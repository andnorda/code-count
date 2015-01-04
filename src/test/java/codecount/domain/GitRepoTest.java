package codecount.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitRepoTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Git git;

    @Before
    public void setUp() throws Exception {
        git = Git.init().setDirectory(folder.getRoot()).call();
    }

    @Test
    public void returns_files() throws Exception {
        // Given
        folder.newFile("README");

        // When
        GitRepo gitRepo = new GitRepo(folder.getRoot());

        // Then
        assertThat(gitRepo.getFiles().size(), is(1));
    }

    @Test
    public void url_is_origin_url() throws Exception {
        // Given
        StoredConfig config = git.getRepository().getConfig();
        config.setString("remote", "origin", "url", "http://github.com/github/testrepo.git");
        config.save();

        // When
        GitRepo gitRepo = new GitRepo(folder.getRoot());

        // Then
        assertThat(gitRepo.getUrl(), is("http://github.com/github/testrepo.git"));
    }
}
