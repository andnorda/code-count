package codecount.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitRepoTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void returns_files() throws Exception {
        // Given
        folder.newFile("README");

        // When
        GitRepo gitRepo = new GitRepo(folder.getRoot());

        // Then
        assertThat(gitRepo.getFiles().size(), is(1));
    }
}
