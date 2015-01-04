package codecount.sevices;

import codecount.domain.GitFile;
import codecount.domain.GitRepo;
import codecount.domain.Language;
import codecount.dtos.Overview;
import codecount.repository.GitRepoRepository;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OverviewServiceTest {
    @Mock
    GitRepoRepository repo;
    private OverviewService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new OverviewService(repo);
    }

    @Test
    public void builds_overview() throws Exception {
        // Given
        GitRepo gitRepo = mockGitRepo();
        when(repo.get("http://github.com/github/testrepo.git")).thenReturn(gitRepo);

        Overview expected = Overview.builder()
                .name("testrepo")
                .url("http://github.com/github/testrepo.git")
                .branches(ImmutableSet.of(
                        "master",
                        "dev"
                ))
                .tags(ImmutableSet.of(
                        "1.0",
                        "1.1"
                ))
                .languages(ImmutableMap.of(
                        Language.JAVA, 0.6,
                        Language.JAVA_SCRIPT, 0.3,
                        Language.LESS, 0.1
                ))
                .build();

        // Then
        assertThat(service.getOverview("http://github.com/github/testrepo.git"), is(expected));
    }

    private GitRepo mockGitRepo() {
        GitRepo gitRepo = mock(GitRepo.class);
        when(gitRepo.getName()).thenReturn("testrepo");
        when(gitRepo.getUrl()).thenReturn("http://github.com/github/testrepo.git");
        when(gitRepo.getBranches()).thenReturn(ImmutableSet.of(
                "master",
                "dev"
        ));
        when(gitRepo.getTags()).thenReturn(ImmutableSet.of(
                "1.0",
                "1.1"
        ));
        GitFile gitFile1 = mockGitFile(Optional.of(Language.JAVA), 6);
        GitFile gitFile2 = mockGitFile(Optional.of(Language.JAVA_SCRIPT), 3);
        GitFile gitFile3 = mockGitFile(Optional.of(Language.LESS), 1);
        GitFile gitFile4 = mockGitFile(Optional.absent(), 10);
        when(gitRepo.getFiles()).thenReturn(ImmutableSet.of(
                gitFile1,
                gitFile2,
                gitFile3,
                gitFile4
        ));
        return gitRepo;
    }

    private GitFile mockGitFile(Optional<Language> language, int lineCount) {
        GitFile gitFile = mock(GitFile.class);
        when(gitFile.getLanguage()).thenReturn(language);
        when(gitFile.getLineCount()).thenReturn(lineCount);
        return gitFile;
    }
}
