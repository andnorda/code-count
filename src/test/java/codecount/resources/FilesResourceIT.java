package codecount.resources;

import codecount.dtos.FileLineCount;
import codecount.repository.GitRepoRepository;
import codecount.services.FilesService;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FilesResourceIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();
    private FilesResource resource;

    @Before
    public void setUp() throws Exception {
        resource = new FilesResource(new FilesService(new GitRepoRepository(folder.getRoot())));
    }

    @Test
    public void returns_commits() throws Exception {
        assertThat(resource.getFileLineCounts("https://github.com/github/testrepo.git"), is(ImmutableSet.of(
                FileLineCount.builder().path("test/alloc.c").lineCount(76).build(),
                FileLineCount.builder().path("test/abspath.2.c").lineCount(109).build(),
                FileLineCount.builder().path("test/archive.c").lineCount(396).build(),
                FileLineCount.builder().path("test/attr.c").lineCount(703).build(),
                FileLineCount.builder().path("test/advice.c").lineCount(49).build(),
                FileLineCount.builder().path("test/archive-tar.c").lineCount(250).build(),
                FileLineCount.builder().path("test/alias.c").lineCount(80).build(),
                FileLineCount.builder().path("test").lineCount(0).build(),
                FileLineCount.builder().path("test/archive-zip.c").lineCount(280).build()
        )));
    }
}
