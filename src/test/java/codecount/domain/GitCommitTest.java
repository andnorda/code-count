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
    public void counts_insertions_and_deletions() throws Exception {
        for (int insertions = 0; insertions <= 3; insertions++) {
            for (int deletions = 0; deletions <= 3; deletions++) {
                RevCommit commit = commitWithInsertionsAndDeletions(insertions, deletions);
                assertThat(insertions + ", " + deletions, new GitCommit(folder.getRoot(), commit).getInsertionsCount(), is(insertions));
                assertThat(insertions + ", " + deletions, new GitCommit(folder.getRoot(), commit).getDeletionsCount(), is(deletions));
            }
        }
    }

    private RevCommit commitWithInsertionsAndDeletions(int insertions, int deletions) throws Exception {
        File file1 = folder.newFile();
        File file2 = folder.newFile();
        Files.write(file1.toPath(), "line\nline\nline".getBytes());
        git.add().addFilepattern(".").call();
        git.commit().setMessage("setup").call();

        if (insertions == 1) {
            Files.write(file2.toPath(), "line".getBytes());
        } else if (insertions == 2) {
            Files.write(file2.toPath(), "line\nline".getBytes());
        } else if (insertions == 3) {
            Files.write(file2.toPath(), "line\nline\nline".getBytes());
        }
        if (deletions == 1) {
            Files.write(file1.toPath(), "line\nline".getBytes());
        } else if (deletions == 2) {
            Files.write(file1.toPath(), "line".getBytes());
        } else if (deletions == 3) {
            Files.write(file1.toPath(), "".getBytes());
        }
        git.add().addFilepattern(".").call();
        return git.commit().setMessage("commit").call();
    }
}
