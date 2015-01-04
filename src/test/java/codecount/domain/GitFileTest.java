package codecount.domain;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

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

    @Test
    public void counts_number_of_lines() throws Exception {
        // Given
        File file = folder.newFile("README");
        Files.write(file.toPath(), "line1\nline2".getBytes());

        // Then
        assertThat(new GitFile(folder.getRoot(), file).getLineCount(), is(1));
    }

    @Test
    public void returns_line_count_of_0_for_dir() throws Exception {
        // Given
        File file = folder.newFolder();

        // Then
        assertThat(new GitFile(folder.getRoot(), file).getLineCount(), is(0));
    }

    @Test
    public void returns_absent_language() throws Exception {
        // Given
        File file = folder.newFile("README");

        // Then
        assertThat(new GitFile(folder.getRoot(), file).getLanguage(), is(Optional.absent()));
    }

    @Test
    public void returns_language() throws Exception {
        // Given
        File file = folder.newFile("index.js");

        // Then
        assertThat(new GitFile(folder.getRoot(), file).getLanguage(), is(Optional.of(Language.JAVA_SCRIPT)));
    }

    @Test
    public void returns_interdependencies() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File root = new File(classLoader.getResource("assets").getFile());
        File file = new File(classLoader.getResource("assets/main.less").getFile());
        assertThat(new GitFile(root, file).getInterdependencies(), is(ImmutableSet.of(
                "mixins.less",
                "typography.less",
                "variables.less",
                "normalize.css",
                "vendor/grid.less"
        )));
    }
}
