package codecount.domain;

import java.io.File;

public class GitFile {
    private String name;

    public GitFile(File file) {
        name = file.getName();
    }

    public String getName() {
        return name;
    }
}
