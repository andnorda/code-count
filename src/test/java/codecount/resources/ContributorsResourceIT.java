package codecount.resources;

import codecount.dtos.Contributor;
import codecount.repository.GitRepoRepository;
import codecount.services.ContributorService;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContributorsResourceIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private ContributorResource resource;

    @Before
    public void setUp() throws Exception {
        resource = new ContributorResource(new ContributorService(new GitRepoRepository(folder.getRoot())));
    }

    @Test
    public void returns_contributors() throws Exception {
        assertThat(resource.getContributors("https://github.com/github/testrepo.git"), is(ImmutableSet.of(
                Contributor.builder().name("Scott Chacon").email("schacon@gmail.com").build()
        )));
    }
}
