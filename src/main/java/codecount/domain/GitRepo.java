package codecount.domain;

import com.google.common.collect.ImmutableSet;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GitRepo {
    private Collection<GitFile> files;
    private String url;
    private String name;
    private Collection<String> branches;
    private Collection<GitCommit> commits;

    public GitRepo(File root) throws IOException, GitAPIException {
        files = listDeep(root).stream().map(GitFile::new).collect(Collectors.toSet());
        Git git = Git.open(root);
        url = git.getRepository().getConfig().getString("remote", "origin", "url");
        name = url.substring(url.lastIndexOf("/") + 1, url.length() - 4);
        branches = git.branchList().call().stream()
                .map(Ref::getName)
                .map(branch -> branch.replace("refs/heads/", ""))
                .collect(Collectors.toSet());
        commits = StreamSupport.stream(git.log().all().call().spliterator(), false)
                .map(GitCommit::new)
                .collect(Collectors.toSet());
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

    public Collection<String> getBranches() {
        return branches;
    }

    public Collection<GitCommit> getCommits() {
        return commits;
    }
}
