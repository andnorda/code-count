package codecount.domain;

import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class GitRepo {
    private Collection<GitFile> files;
    private String url;
    private String name;

    public GitRepo(File root) throws IOException {
        files = Arrays.asList(root.listFiles(file -> !file.getName().equals(".git"))).stream()
                .map(file -> new GitFile(file))
                .collect(Collectors.toList());
        url = Git.open(root).getRepository().getConfig().getString("remote", "origin", "url");
        name = url.substring(url.lastIndexOf("/") + 1, url.length() - 4);
    }

    public Collection<GitFile> getFiles() {
        return files;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
