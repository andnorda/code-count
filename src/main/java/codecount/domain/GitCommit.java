package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;

@EqualsAndHashCode
@ToString
public class GitCommit {
    private String hash;
    private int timestamp;
    private GitContributor author;

    public GitCommit(File root, RevCommit revCommit) {
        hash = revCommit.getName();
        timestamp = revCommit.getCommitTime();

        PersonIdent author = revCommit.getAuthorIdent();
        String email = author.getEmailAddress();
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
}
