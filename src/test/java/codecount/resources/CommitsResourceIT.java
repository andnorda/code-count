package codecount.resources;

import codecount.dtos.Commit;
import codecount.dtos.CommitDetails;
import codecount.dtos.Contributor;
import codecount.repository.GitRepoRepository;
import codecount.services.CommitsService;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommitsResourceIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private CommitsResource resource;

    @Before
    public void setUp() throws Exception {
        resource = new CommitsResource(new CommitsService(new GitRepoRepository(folder.getRoot())));
    }

    @Test
    public void returns_commits() throws Exception {
        assertThat(resource.getCommits("https://github.com/github/testrepo.git"), is(ImmutableSet.of(
                Commit.builder().hash("10e9ac58c77bc229d8c59a5b4eb7422916453148").timestamp(1290047137).build(),
                Commit.builder().hash("78cf42b3249a69c0602b8bcb074cb6a61156787f").timestamp(1290044873).build(),
                Commit.builder().hash("6ff1be9c3819c93a2f41e0ddc09f252fcf154f34").timestamp(1290043902).build(),
                Commit.builder().hash("444b3c366018526033e849443b252c6f73076cd6").timestamp(1290044076).build(),
                Commit.builder().hash("040b4674e7f9480f61f33441964b66c044dbc47f").timestamp(1290046238).build(),
                Commit.builder().hash("f829883bcbc383e26b3428b268c981b9116370c0").timestamp(1290044297).build()
        )));
    }

    @Test
    public void returns_commit_details() throws Exception {
        assertThat(resource.getCommitDetails("https://github.com/github/testrepo.git", "10e9ac58c77bc229d8c59a5b4eb7422916453148"),
                is(CommitDetails.builder()
                        .committer("Scott Chacon")
                        .insertions(2)
                        .deletions(0)
                        .timestamp(1290047137)
                        .build()));
    }
}
