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
        GitRepo gitRepo = mockGitRepo();
        when(repo.get("http://github.com/github/testrepo.git")).thenReturn(gitRepo);

        //Then
        assertThat(service.getContributors("http://github.com/github/testrepo.git"), is(ImmutableSet.of(
                Contributor.builder().name("Ola Nordmann").commitCount(2).build(),
                Contributor.builder().name("Kari Nordmann").commitCount(3).build()
        )));
    }

    private GitRepo mockGitRepo() {
        GitRepo gitRepo = mock(GitRepo.class);
        GitContributor contributor1 = mockGitContributor("Ola Nordmann", 2);
        GitCommit commit1 = mockGitCommit(contributor1);
        GitContributor contributor2 = mockGitContributor("Kari Nordmann", 3);
        GitCommit commit2 = mockGitCommit(contributor2);
        when(gitRepo.getCommits()).thenReturn(ImmutableSet.of(
                commit1,
                commit2
        ));
        return gitRepo;
    }

    private GitCommit mockGitCommit(GitContributor contributor1) {
        GitCommit commit1 = mock(GitCommit.class);
        when(commit1.getAuthor()).thenReturn(contributor1);
        return commit1;
    }

    private GitContributor mockGitContributor(String name, int commitCount) {
        GitContributor contributor = mock(GitContributor.class);
        when(contributor.getName()).thenReturn(name);
        when(contributor.getCommitCount()).thenReturn(commitCount);
        return contributor;
    }
}
