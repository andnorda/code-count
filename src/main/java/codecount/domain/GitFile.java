package codecount.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;

@EqualsAndHashCode
@ToString
public class GitFile {
    private String name;

    public GitFile(File file) {
        name = file.getName();
    }

    public String getName() {
        return name;
    }
}
