package codecount.domain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GitContributor {
    private final File root;
    private final String name;
    private final String email;

    public GitContributor(File root, String name, String email) {
        this.root = root;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getCommitCount() {
        try {
            return (int) StreamSupport.stream(Git.open(root).log().call().spliterator(), false)
                    .filter(commit -> commit.getAuthorIdent().getName().equals(name) && commit.getAuthorIdent().getEmailAddress().equals(email))
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

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
