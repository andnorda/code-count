package codecount.services;

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

    public Collection<FileInterdependencies> getFileInterdependencies(String remoteUrl) {
        return repo.get(remoteUrl).getFiles().stream()
                .map(file -> FileInterdependencies.builder()
                        .path(file.getPath())
                        .interdependencies(file.getInterdependencies())
                        .build())
                .collect(Collectors.toSet());
    }
}
