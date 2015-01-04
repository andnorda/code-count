package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.jgit.revwalk.RevCommit;

@EqualsAndHashCode
@ToString
public class GitCommit {
    private String hash;
    private int timestamp;

    public GitCommit(RevCommit revCommit) {
        hash = revCommit.getName();
        timestamp = revCommit.getCommitTime();
    }

    public String getHash() {
        return hash;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
