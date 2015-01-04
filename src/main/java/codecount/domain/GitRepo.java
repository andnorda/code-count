package codecount.domain;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class GitRepo {
    private Collection<GitFile> files;

    public GitRepo(File root) {
        files = Arrays.asList(root.listFiles()).stream()
                .map(file -> new GitFile(file))
                .collect(Collectors.toList());
    }

    public Collection<GitFile> getFiles() {
        return files;
    }
}
