package codecount.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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

        assertThat(new GitCommit(commit).getHash(), is(commit.getName()));
    }

    @Test
    public void returns_timestamp() throws Exception {
        RevCommit commit = git.commit().setMessage("Initial commit.").call();

        assertThat(new GitCommit(commit).getTimestamp(), is(commit.getCommitTime()));
    }

    @Test
    public void returns_author() throws Exception {
        RevCommit commit = git.commit().setMessage("Initial commit.").setAuthor("Ola Nordmann", "ola@nordmann.no").call();

        assertThat(new GitCommit(commit).getAuthor(), is(GitContributor.builder()
                .name("Ola Nordmann")
                .email("ola@nordmann.no")
                .build()));
    }
}
