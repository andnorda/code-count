package codecount.resources;

import codecount.repository.GitRepoRepository;
import codecount.services.CommitsService;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CommitsResourceIT {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    @Ignore
    public void test() throws Exception {
        new CommitsResource(new CommitsService(new GitRepoRepository(folder.getRoot())));
    }
}
