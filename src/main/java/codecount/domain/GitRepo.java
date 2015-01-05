package codecount.domain;

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

import static org.eclipse.jgit.api.ListBranchCommand.ListMode.ALL;

public class GitRepo {
    private final File root;
    private Collection<GitFile> files;
    private String url;
    private String name;
    private Collection<String> branches;
    private final Git git;
    private Collection<String> tags;

    public GitRepo(File root) {
        this.root = root;
        try {
            git = Git.open(root);
            git.checkout().setName("master").call();
            files = listDeep(root).stream().map(file -> new GitFile(root, file)).collect(Collectors.toSet());
            url = git.getRepository().getConfig().getString("remote", "origin", "url");
            name = url.substring(url.lastIndexOf("/") + 1, url.length() - 4);
            branches = git.branchList().setListMode(ALL).call().stream()
                    .map(Ref::getName)
                    .map(branch -> branch.replace("refs/heads/", ""))
                    .map(branch -> branch.replace("refs/remotes/origin/", ""))
                    .collect(Collectors.toSet());
            tags = git.tagList().call().stream()
                    .map(Ref::getName)
                    .map(branch -> branch.replace("refs/tags/", ""))
                    .collect(Collectors.toSet());

        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
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
        try {
            return StreamSupport.stream(git.log().call().spliterator(), false)
                    .map(commit -> new GitCommit(root, commit))
                    .collect(Collectors.toSet());
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkout(String ref) throws GitAPIException {
        git.checkout().setName(ref).call();
    }

    public Collection<String> getTags() {
        return tags;
    }
}
