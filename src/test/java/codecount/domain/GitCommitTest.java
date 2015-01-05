package codecount.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitCommitTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Git git;

    @Before
    public void setUp() throws Exception {
        git = Git.init().setDirectory(folder.getRoot()).call();
    }

    @Test
    public void returns_hash() throws Exception {
        RevCommit commit = git.commit().setMessage("Initial commit.").call();

        assertThat(new GitCommit(folder.getRoot(), commit).getHash(), is(commit.getName()));
    }

    @Test
    public void returns_timestamp() throws Exception {
        RevCommit commit = git.commit().setMessage("Initial commit.").call();

        assertThat(new GitCommit(folder.getRoot(), commit).getTimestamp(), is(commit.getCommitTime()));
    }

    @Test
    public void returns_author() throws Exception {
        RevCommit commit = git.commit().setMessage("Initial commit.").setAuthor("Ola Nordmann", "ola@nordmann.no").call();

        assertThat(new GitCommit(folder.getRoot(), commit).getAuthor().getName(), is("Ola Nordmann"));
    }

    @Test
    public void returns_additions_count() throws Exception {
        git.commit().setMessage("Initial commit.").call();
        Files.write(folder.newFile().toPath(), "line1".getBytes());
        File file = folder.newFile();
        Files.write(file.toPath(), "line1\nline2".getBytes());
        git.add().addFilepattern(".").call();
        RevCommit secondCommit = git.commit().setMessage("Second commit.").call();
        file.delete();
        RevCommit thirdCommit = git.commit().setAll(true).setMessage("Third commit.").call();

        assertThat(new GitCommit(folder.getRoot(), secondCommit).getAdditionsCount(), is(3));
        assertThat(new GitCommit(folder.getRoot(), thirdCommit).getAdditionsCount(), is(0));
    }
}
