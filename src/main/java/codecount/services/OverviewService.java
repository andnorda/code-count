package codecount.services;

import codecount.domain.GitFile;
import codecount.domain.GitRepo;
import codecount.domain.Language;
import codecount.dtos.Overview;
import codecount.repository.GitRepoRepository;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class OverviewService {
    private GitRepoRepository repo;

    public OverviewService(GitRepoRepository repo) {
        this.repo = repo;
    }

    public Overview getOverview(String remoteUrl) {
        GitRepo gitRepo = repo.get(remoteUrl);
        return Overview.builder()
                .name(gitRepo.getName())
                .url(gitRepo.getUrl())
                .branches(gitRepo.getBranches())
                .tags(gitRepo.getTags())
                .languages(getLanguages(gitRepo))
                .build();
    }

    private Map<Language, Double> getLanguages(GitRepo gitRepo) {
        Collection<GitFile> files = gitRepo.getFiles();
        Map<Language, Double> languages = files.stream()
                .filter(file -> file.getLanguage().isPresent())
                .collect(Collectors.groupingBy(file -> file.getLanguage().get(),
                        Collectors.summingDouble(GitFile::getLineCount)));
        double sum = languages.values().stream()
                .reduce(0.0, (a, b) -> a + b);
        languages.keySet().stream()
                .forEach(key -> languages.put(key, languages.get(key) / sum));
        return languages;
    }
}
