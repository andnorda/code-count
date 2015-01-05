package codecount.services;

import codecount.domain.GitCommit;
import codecount.domain.GitContributor;
import codecount.domain.GitRepo;
import codecount.dtos.Contributor;
import codecount.repository.GitRepoRepository;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContributorsServiceTest {
    @Mock
    GitRepoRepository repo;
    private ContributorService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new ContributorService(repo);
    }

    @Test
    public void builds_contributors() throws Exception {
        // Given
        GitRepo gitRepo = mock(GitRepo.class);
        GitCommit commit1 = mock(GitCommit.class);
        when(commit1.getAuthor()).thenReturn(GitContributor.builder()
                .name("Ola Nordmann")
                .email("ola@nordmann.no")
                .build());
        GitCommit commit2 = mock(GitCommit.class);
        when(commit2.getAuthor()).thenReturn(GitContributor.builder()
                .name("Kari Nordmann")
                .email("kari@nordmann.no")
                .build());
        when(gitRepo.getCommits()).thenReturn(ImmutableSet.of(
                commit1,
                commit2
        ));
        when(repo.get("http://github.com/github/testrepo.git")).thenReturn(gitRepo);

        //Then
        assertThat(service.getContributors("http://github.com/github/testrepo.git"), is(ImmutableSet.of(
                Contributor.builder().name("Ola Nordmann").email("ola@nordmann.no").build(),
                Contributor.builder().name("Kari Nordmann").email("kari@nordmann.no").build()
        )));
    }
}
