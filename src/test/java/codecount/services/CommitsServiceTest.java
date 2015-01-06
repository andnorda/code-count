package codecount.services;

import codecount.domain.GitCommit;
import codecount.domain.GitContributor;
import codecount.domain.GitRepo;
import codecount.dtos.Commit;
import codecount.dtos.CommitDetails;
import codecount.repository.GitRepoRepository;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommitsServiceTest {
    @Mock
    GitRepoRepository repo;
    private CommitsService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new CommitsService(repo);
    }

    @Test
    public void builds_commit_list() throws Exception {
        // Given
        GitRepo gitRepo = mock(GitRepo.class);
        GitCommit commit = mock(GitCommit.class);
        when(commit.getHash()).thenReturn("hash");
        when(commit.getTimestamp()).thenReturn(1);
        when(gitRepo.getCommits()).thenReturn(ImmutableSet.of(
                commit
        ));
        when(repo.get("http://github.com/github/testrepo.git")).thenReturn(gitRepo);

        // Then
        assertThat(service.getCommits("http://github.com/github/testrepo.git"), is(ImmutableSet.of(
                Commit.builder()
                        .hash("hash")
                        .timestamp(1)
                        .build()
        )));
    }

    @Test
    public void builds_commit_details() throws Exception {
        // Given
        String hash = "10e9ac58c77bc229d8c59a5b4eb7422916453148";
        GitRepo gitRepo = mock(GitRepo.class);
        GitCommit commit = mock(GitCommit.class);
        when(commit.getHash()).thenReturn(hash);
        GitContributor gitContributor = mock(GitContributor.class);
        when(gitContributor.getName()).thenReturn("Ola Nordmann");
        when(commit.getAuthor()).thenReturn(gitContributor);
        when(commit.getDeletionsCount()).thenReturn(100);
        when(commit.getInsertionsCount()).thenReturn(30);
        when(commit.getTimestamp()).thenReturn(1);
        when(gitRepo.getCommit(hash)).thenReturn(commit);
        when(repo.get("http://github.com/github/testrepo.git")).thenReturn(gitRepo);

        // Then
        assertThat(service.getCommitDetails("http://github.com/github/testrepo.git", hash), is(CommitDetails.builder()
                .committer("Ola Nordmann")
                .insertions(30)
                .deletions(100)
                .timestamp(1)
                .build()));
    }
}
