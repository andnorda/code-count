package codecount.resources;

import codecount.domain.Language;
import codecount.dtos.Overview;
import codecount.repository.GitRepoRepository;
import codecount.sevices.OverviewService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OverviewResourceIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test() throws Exception {
        OverviewResource resource = new OverviewResource(new OverviewService(new GitRepoRepository(folder.getRoot())));
        Overview overview = resource.getOverview("https://github.com/github/testrepo.git");
        System.out.println(overview);

        Overview expected = Overview.builder()
                .name("testrepo")
                .url("https://github.com/github/testrepo.git")
                .branches(ImmutableSet.of(
                        "blue",
                        "email",
                        "master",
                        "topic/green",
                        "topic/pink"
                ))
                .tags(ImmutableSet.of())
                .languages(ImmutableMap.of(
                        Language.C, 1.0
                ))
                .build();
        assertThat(overview, is(expected));
    }
}
