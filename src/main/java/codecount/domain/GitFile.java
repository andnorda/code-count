package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.*;

@EqualsAndHashCode
@ToString
public class GitFile {
    private String name;
    private String path;
    private int lineCount;

    public GitFile(File root, File file) {
        name = file.getName();
        path = file.getPath().replace(root.getPath() + "/", "");
        if (file.isDirectory()) {
            lineCount = 0;
        } else {
            lineCount = countLines(file);
        }
    }

    private int countLines(File file) {
        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public int getLineCount() {
        return lineCount;
    }
}
