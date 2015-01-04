package codecount.services;

import codecount.domain.GitFile;
import codecount.domain.Language;
import codecount.dtos.FileInterdependencies;
import codecount.dtos.FileLineCount;
import codecount.repository.GitRepoRepository;

import java.util.Collection;
import java.util.stream.Collectors;

public class FilesService {
    private final GitRepoRepository repo;

    public FilesService(GitRepoRepository repo) {
        this.repo = repo;
    }

    public Collection<FileLineCount> getFileLineCounts(String remoteUrl) {
        return repo.get(remoteUrl).getFiles().stream()
                .map(file -> FileLineCount.builder()
                        .path(file.getPath())
                        .lineCount(file.getLineCount())
                        .build())
                .collect(Collectors.toSet());
    }

    public Collection<FileInterdependencies> getFileInterdependencies(String remoteUrl, Language language) {
        return repo.get(remoteUrl).getFiles().stream()
                .filter(file -> file.getLanguage().isPresent() && file.getLanguage().get().equals(language))
                .map(file -> FileInterdependencies.builder()
                        .path(file.getPath())
                        .interdependencies(file.getInterdependencies())
                        .build())
                .collect(Collectors.toSet());
    }
}
