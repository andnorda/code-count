package codecount.domain;

import com.google.common.collect.ImmutableSet;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
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
    private RevCommit initialCommit;

    @Before
    public void setUp() throws Exception {
        git = Git.init().setDirectory(folder.getRoot()).call();
        StoredConfig config = git.getRepository().getConfig();
        config.setString("remote", "origin", "url", "http://github.com/github/testrepo.git");
        config.save();
        initialCommit = git.commit().setMessage("Initial commit.").call();
    }

    @Test
    public void returns_files() throws Exception {
        // Given
        Collection<GitFile> expected = ImmutableSet.of(
                new GitFile(folder.getRoot(), folder.newFile("README")),
                new GitFile(folder.getRoot(), folder.newFolder("src")),
                new GitFile(folder.getRoot(), folder.newFile("src/index.js")));

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

    @Test
    public void lists_commits() throws Exception {
        // Given
        RevCommit secondCommit = git.commit().setMessage("Second commit.").call();

        // Then
        assertThat(new GitRepo(folder.getRoot()).getCommits(), is(ImmutableSet.of(
                new GitCommit(initialCommit),
                new GitCommit(secondCommit)
        )));
    }

    @Test
    public void lists_commits_of_master_branch() throws Exception {
        // Given
        git.checkout().setCreateBranch(true).setName("dev").call();
        git.commit().setMessage("Second commit.").call();

        // Then
        assertThat(new GitRepo(folder.getRoot()).getCommits(), is(ImmutableSet.of(
                new GitCommit(initialCommit)
        )));
    }

    @Test
    public void lists_commits_of_dev_branch() throws Exception {
        // Given
        git.checkout().setCreateBranch(true).setName("dev").call();
        RevCommit secondCommit = git.commit().setMessage("Second commit.").call();

        // Then
        GitRepo gitRepo = new GitRepo(folder.getRoot());
        gitRepo.checkout("dev");
        assertThat(gitRepo.getCommits(), is(ImmutableSet.of(
                new GitCommit(initialCommit),
                new GitCommit(secondCommit)
        )));
    }

    @Test
    public void lists_commits_of_1_0_tag() throws Exception {
        // Given
        git.tag().setName("1.0").call();
        git.commit().setMessage("Second commit.").call();

        // Then
        GitRepo gitRepo = new GitRepo(folder.getRoot());
        gitRepo.checkout("1.0");
        assertThat(gitRepo.getCommits(), is(ImmutableSet.of(
                new GitCommit(initialCommit)
        )));
    }

    @Test
    public void lists_tags() throws Exception {
        // Given
        git.tag().setName("1.0").call();
        git.commit().setMessage("Second commit.").call();
        git.tag().setName("1.1").call();

        // Then
        assertThat(new GitRepo(folder.getRoot()).getTags(), is(ImmutableSet.of(
                "1.0",
                "1.1"
        )));
    }
}
