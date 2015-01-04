package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.jgit.revwalk.RevCommit;

@EqualsAndHashCode
@ToString
public class GitCommit {
    private String hash;

    public GitCommit(RevCommit revCommit) {
        hash = revCommit.getName();
    }

    public String getHash() {
        return hash;
    }
}
