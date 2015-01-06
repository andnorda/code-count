package codecount.services;

import codecount.domain.GitCommit;
import codecount.dtos.Commit;
import codecount.dtos.CommitDetails;
import codecount.repository.GitRepoRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class CommitsService {
    private final GitRepoRepository repo;

    public CommitsService(GitRepoRepository repo) {
        this.repo = repo;
    }

    public Collection<Commit> getCommits(String remoteUrl) {
        return repo.get(remoteUrl).getCommits().stream()
                .map(commit -> Commit.builder()
                        .hash(commit.getHash())
                        .timestamp(commit.getTimestamp())
                        .build())
                .collect(Collectors.toSet());
    }

    public CommitDetails getCommitDetails(String remoteUrl, String commitHash) {
        GitCommit commit = repo.get(remoteUrl).getCommit(commitHash);
        return CommitDetails.builder()
                .committer(commit.getAuthor().getName())
                .insertions(commit.getInsertionsCount())
                .deletions(commit.getDeletionsCount())
                .timestamp(commit.getTimestamp())
                .build();
    }
}
