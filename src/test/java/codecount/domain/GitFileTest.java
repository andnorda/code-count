package codecount.domain;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitFileTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void returns_file_name() throws Exception {
        // Given
        File file = folder.newFile("README");

        // When
        GitFile gitFile = new GitFile(folder.getRoot(), file);

        // Then
        assertThat(gitFile.getName(), is("README"));
    }

    @Test
    public void returns_relative_file_path() throws Exception {
        // Given
        folder.newFolder("dir");
        File file = folder.newFile("dir/README");

        // When
        GitFile gitFile = new GitFile(folder.getRoot(), file);

        // Then
        assertThat(gitFile.getPath(), is("dir/README"));
    }
}
