package codecount.resources;

import codecount.domain.Language;
import codecount.dtos.Overview;
import codecount.repository.GitRepoRepository;
import codecount.services.OverviewService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OverviewResourceIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private OverviewResource resource;

    @Before
    public void setUp() throws Exception {
        resource = new OverviewResource(new OverviewService(new GitRepoRepository(folder.getRoot())));
    }

    @Test
    public void test() throws Exception {
        assertThat(resource.getOverview("https://github.com/github/testrepo.git"), is(Overview.builder()
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
                .build()));
    }
}
