package codecount.domain;

import lombok.ToString;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@ToString
public class GitCommit {
    private final File root;
    private String hash;
    private int timestamp;
    private GitContributor author;
    private Integer insertions;
    private Integer deletions;

    public GitCommit(File root, RevCommit revCommit) {
        this.root = root;
        hash = revCommit.getName();
        timestamp = revCommit.getCommitTime();

        PersonIdent author = revCommit.getAuthorIdent();
        String name = author.getName();
        this.author = new GitContributor(root, name);
    }

    public String getHash() {
        return hash;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public GitContributor getAuthor() {
        return author;
    }

    public int getInsertionsCount() {
        if (insertions == null) {
            setChanges();
        }
        return insertions;
    }

    public int getDeletionsCount() {
        if (deletions == null) {
            setChanges();
        }
        return deletions;
    }

    private void setChanges() {
        try {
            String command = "git --git-dir=" + root.getAbsolutePath() + "/.git diff-tree --shortstat " + getHash();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CommandLine commandline = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            String output = outputStream.toString();

            if (output.contains("(-)")) {
                deletions = Integer.valueOf(output.substring(output.lastIndexOf(", ")+2, output.indexOf(" deletion")));
            } else {
                deletions = 0;
            }
            if (output.contains("(+)")) {
                insertions = Integer.valueOf(output.substring(output.indexOf(", ")+2, output.indexOf(" insertion")));
            } else {
                insertions = 0;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitCommit commit = (GitCommit) o;

        if (hash != null ? !hash.equals(commit.hash) : commit.hash != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return hash != null ? hash.hashCode() : 0;
    }
}
