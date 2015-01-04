package codecount.services;

import codecount.domain.GitFile;
import codecount.domain.GitRepo;
import codecount.dtos.FileInterdependencies;
import codecount.dtos.FileLineCount;
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

public class FilesServiceTest {
    @Mock
    GitRepoRepository repo;
    private FilesService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new FilesService(repo);

        GitRepo gitRepo = mockGitRepo();
        when(repo.get("https://github.com/github/testrepo.git")).thenReturn(gitRepo);

    }

    private GitRepo mockGitRepo() {
        GitRepo gitRepo = mock(GitRepo.class);
        GitFile file1 = mockGitFile("main.less", 10, ImmutableSet.of(
                "mixins.less"
        ));
        GitFile file2 = mockGitFile("mixins.less", 1, ImmutableSet.of(
                "mixins/color.less"
        ));
        GitFile file3 = mockGitFile("mixins/color.less", 3, ImmutableSet.of());
        when(gitRepo.getFiles()).thenReturn(ImmutableSet.of(
                file1,
                file2,
                file3
        ));
        return gitRepo;
    }

    private GitFile mockGitFile(String path, int lineCount, ImmutableSet<String> interdependencies) {
        GitFile file1 = mock(GitFile.class);
        when(file1.getPath()).thenReturn(path);
        when(file1.getLineCount()).thenReturn(lineCount);
        when(file1.getInterdependencies()).thenReturn(interdependencies);
        return file1;
    }

    @Test
    public void returns_file_interdependencies_collection() throws Exception {
        assertThat(service.getFileInterdependencies("https://github.com/github/testrepo.git"), is(ImmutableSet.of(
                FileInterdependencies.builder().path("main.less").interdependencies(ImmutableSet.of("mixins.less")).build(),
                FileInterdependencies.builder().path("mixins.less").interdependencies(ImmutableSet.of("mixins/color.less")).build(),
                FileInterdependencies.builder().path("mixins/color.less").interdependencies(ImmutableSet.of()).build()
        )));
    }

    @Test
    public void returns_file_line_count_collection() throws Exception {
        assertThat(service.getFileLineCounts("https://github.com/github/testrepo.git"), is(ImmutableSet.of(
                FileLineCount.builder().path("main.less").lineCount(10).build(),
                FileLineCount.builder().path("mixins.less").lineCount(1).build(),
                FileLineCount.builder().path("mixins/color.less").lineCount(3).build()
        )));
    }
}
