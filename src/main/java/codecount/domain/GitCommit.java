package codecount.domain;

import lombok.ToString;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;

@ToString
public class GitCommit {
    private String hash;
    private int timestamp;
    private GitContributor author;

    public GitCommit(File root, RevCommit revCommit) {
        hash = revCommit.getName();
        timestamp = revCommit.getCommitTime();

        PersonIdent author = revCommit.getAuthorIdent();
        String name = author.getName();
        this.author = new GitContributor(root, name);
    }

    public String getHash() {
        return hash;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public GitContributor getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitCommit commit = (GitCommit) o;

        if (hash != null ? !hash.equals(commit.hash) : commit.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hash != null ? hash.hashCode() : 0;
    }
}
