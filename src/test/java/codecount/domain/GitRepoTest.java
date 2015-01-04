package codecount.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GitRepoTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private Git git;

    @Before
    public void setUp() throws Exception {
        git = Git.init().setDirectory(folder.getRoot()).call();
        StoredConfig config = git.getRepository().getConfig();
        config.setString("remote", "origin", "url", "http://github.com/github/testrepo.git");
        config.save();
    }

    @Test
    public void returns_files() throws Exception {
        // Given
        Collection<GitFile> expected = ImmutableSet.of(
                new GitFile(folder.newFile("README")),
                new GitFile(folder.newFolder("src")),
                new GitFile(folder.newFile("src/index.js")));

        // Then
        assertThat(new GitRepo(folder.getRoot()).getFiles(), is(expected));
    }

    @Test
    public void url_is_origin_url() throws Exception {
        assertThat(new GitRepo(folder.getRoot()).getUrl(), is("http://github.com/github/testrepo.git"));
    }

    @Test
    public void name_is_end_of_url_resource_name() throws Exception {
        assertThat(new GitRepo(folder.getRoot()).getName(), is("testrepo"));
    }

    @Test
    public void lists_branches() throws Exception {
        // Given
        git.commit().setMessage("Initial commit.").call();
        git.branchCreate().setName("dev").call();

        // Then
        assertThat(new GitRepo(folder.getRoot()).getBranches(), is(ImmutableSet.of(
                "master",
                "dev"
        )));
    }
}
