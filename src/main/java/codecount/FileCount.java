package codecount;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class FileCount {
    private int count;

    public FileCount(String remoteUrl) throws IOException, GitAPIException {
        // prepare a new folder for the cloned repository
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // then clone
        System.out.println("Cloning from " + remoteUrl + " to " + localPath);
        Git result = Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(localPath)
                .call();

        try {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            System.out.println("Having repository: " + result.getRepository().getDirectory());
            count = Arrays.asList(localPath.listFiles(file -> !file.getName().equals(".git"))).stream()
                    .map(file -> countDeep(file))
                    .reduce(0, (a, b) -> a + b);
        } finally {
            result.close();
        }
    }

    private int countDeep(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return 1;
        }
        return Arrays.asList(files).stream()
                .map(dir -> countDeep(dir))
                .reduce(1, (a, b) -> a + b);
    }

    public int count() {
        return count;
    }
}
