package codecount.domain;

import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class GitRepo {
    private Collection<GitFile> files;
    private String url;
    private String name;

    public GitRepo(File root) throws IOException {
        files = listDeep(root).stream().map(GitFile::new).collect(Collectors.toSet());
        url = Git.open(root).getRepository().getConfig().getString("remote", "origin", "url");
        name = url.substring(url.lastIndexOf("/") + 1, url.length() - 4);
    }

    private Collection<File> listDeep(File dir) {
        Collection<File> files = new HashSet<>();
        files.addAll(Arrays.asList(dir.listFiles(file -> !file.getName().equals(".git"))));
        Arrays.asList(dir.listFiles(file -> file.isDirectory() && !file.getName().equals(".git"))).stream()
                .forEach(file -> files.addAll(listDeep(file)));
        return files;
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
