package codecount.repository;

import codecount.domain.GitRepo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitRepoRepositoryIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void clones_repo() throws Exception {
        GitRepoRepository repo = new GitRepoRepository(folder.getRoot());
        GitRepo gitRepo = repo.get("https://github.com/github/testrepo.git");
        assertThat(gitRepo.getName(), is("testrepo"));
    }
}
