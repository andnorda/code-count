package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;

@EqualsAndHashCode
@ToString
public class GitFile {
    private String name;
    private String path;

    public GitFile(File root, File file) {
        name = file.getName();
        path = file.getPath().replace(root.getPath() + "/", "");
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}
