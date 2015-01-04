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
        GitFile gitFile = new GitFile(file);

        // Then
        assertThat(gitFile.getName(), is("README"));
    }
}
