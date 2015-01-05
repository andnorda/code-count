package codecount.services;

import codecount.domain.GitCommit;
import codecount.dtos.Contributor;
import codecount.repository.GitRepoRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class ContributorService {
    private final GitRepoRepository repo;

    public ContributorService(GitRepoRepository repo) {
        this.repo = repo;
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
