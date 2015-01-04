package codecount.repository;

import codecount.domain.GitRepo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitRepoRepositoryIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private GitRepoRepository repo;

    @Before
    public void setUp() throws Exception {
        repo = new GitRepoRepository(folder.getRoot());
    }

    @Test
    public void clones_repo() throws Exception {
        GitRepo gitRepo = repo.get("https://github.com/github/testrepo.git");
        assertThat(gitRepo.getName(), is("testrepo"));
    }

    @Test
    public void pulls_repo() throws Exception {
        repo.get("https://github.com/github/testrepo.git");
        GitRepo gitRepo = repo.get("https://github.com/github/testrepo.git");
        assertThat(gitRepo.getName(), is("testrepo"));
    }
}
