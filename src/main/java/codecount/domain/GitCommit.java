package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.jgit.revwalk.RevCommit;

@EqualsAndHashCode
@ToString
public class GitCommit {
    private String hash;
    private int timestamp;
    private GitContributor author;

    public GitCommit(RevCommit revCommit) {
        hash = revCommit.getName();
        timestamp = revCommit.getCommitTime();
        author = GitContributor.builder()
                .name(revCommit.getAuthorIdent().getName())
                .email(revCommit.getAuthorIdent().getEmailAddress())
                .build();
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
