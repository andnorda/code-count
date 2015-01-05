package codecount.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.stream.StreamSupport;

public class GitContributor {
    private final File root;
    private final String name;

    public GitContributor(File root, String name) {
        this.root = root;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCommitCount() {
        try {
            return (int) StreamSupport.stream(Git.open(root).log().call().spliterator(), false)
                    .filter(commit -> commit.getAuthorIdent().getName().equals(name))
                    .count();
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitContributor that = (GitContributor) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
