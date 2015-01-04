package codecount.services;

import codecount.domain.GitCommit;
import codecount.domain.GitRepo;
import codecount.dtos.Commit;
import codecount.repository.GitRepoRepository;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
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
    public void builds_overview() throws Exception {
        // Given
        GitRepo gitRepo = mock(GitRepo.class);
        GitCommit commit = mock(GitCommit.class);
        when(commit.getHash()).thenReturn("hash");
        when(commit.getTimestamp()).thenReturn(1l);
        when(gitRepo.getCommits()).thenReturn(ImmutableSet.of(
                commit
        ));
        when(repo.get("http://github.com/github/testrepo.git")).thenReturn(gitRepo);

        Collection<Commit> expected = ImmutableSet.of(
                Commit.builder()
                        .hash("hash")
                        .timestamp(1l)
                        .build()
        );

        // Then
        assertThat(service.getCommits("http://github.com/github/testrepo.git"), is(expected));
    }
}
