package codecount.services;

import codecount.domain.GitCommit;
import codecount.dtos.Commit;
import codecount.dtos.Contributor;
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

    public Collection<Contributor> getContributors(String remoteUrl) {
        return repo.get(remoteUrl).getCommits().stream()
                .map(GitCommit::getAuthor)
                .distinct()
                .map(author -> Contributor.builder()
                        .name(author.getName())
                        .email(author.getEmail())
                        .build())
                .collect(Collectors.toSet());
    }
}
