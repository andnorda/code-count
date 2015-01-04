package codecount.repository;

import codecount.domain.GitRepo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitRepoRepository {
    private File root;

    public GitRepoRepository(File root) {
        this.root = root;
    }

    public GitRepo get(String remoteUrl) {
        try {
            File directory = new File(root, remoteUrl);
            if (directory.exists()) {
                Git.open(directory).pull().call();
            } else {
                Git.cloneRepository().setURI(remoteUrl).setDirectory(directory).call();
            }
            return new GitRepo(directory);
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
