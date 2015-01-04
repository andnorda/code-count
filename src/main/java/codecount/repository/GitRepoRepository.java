package codecount.repository;

import codecount.domain.GitRepo;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class GitRepoRepository {
    private File root;

    public GitRepoRepository(File root) {
        this.root = root;
    }

    public GitRepo get(String remoteUrl) {
        try {
            Git.cloneRepository().setURI(remoteUrl).setDirectory(root).call();
            return new GitRepo(root);
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }
}
