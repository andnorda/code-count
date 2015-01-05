package codecount.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitContributorTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Git git;

    @Before
    public void setUp() throws Exception {
        git = Git.init().setDirectory(folder.getRoot()).call();
    }

    @Test
    public void is_equal_based_on_name_and_email() throws Exception {
        assertThat(new GitContributor(folder.newFile(), "Ola Nordmann"),
                is(new GitContributor(folder.newFile(), "Ola Nordmann")));
    }

    @Test
    public void returns_email_and_name() throws Exception {
        GitContributor contributor = new GitContributor(folder.newFile(), "Ola Nordmann");
        assertThat(contributor.getName(), is("Ola Nordmann"));
    }

    @Test
    public void returns_number_of_commits() throws Exception {
        git.commit().setMessage("Initial commit.").setAuthor("Ola Nordmann", "ola@nordmann.no").call();
        git.commit().setMessage("Second commit.").setAuthor("Ola Nordmann", "sauron14@hotmail.com").call();
        git.commit().setMessage("Third commit.").setAuthor("Kari Nordmann", "kari@nordmann.no").call();

        assertThat(new GitContributor(folder.getRoot(), "Ola Nordmann").getCommitCount(), is(2));
    }
}
