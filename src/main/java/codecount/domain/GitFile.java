package codecount.domain;

import com.google.common.base.Optional;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class GitFile {
    private String name;
    private String path;
    private int lineCount;
    private Optional<Language> language;
    private Collection<String> interdependencies;

    public GitFile(File root, File file) {
        name = file.getName();
        path = file.getPath().replace(root.getPath() + "/", "");
        if (file.isDirectory()) {
            lineCount = 0;
        } else {
            lineCount = countLines(file);
        }
        language = Language.valueOf(file);
        if (language.isPresent()) {
            interdependencies = getInterdependencies(language.get(), file);
        } else {
            interdependencies = new HashSet<>();
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

    public Optional<Language> getLanguage() {
        return language;
    }

    public Collection<String> getInterdependencies() {
        return interdependencies;
    }

    private Collection<String> getInterdependencies(Language language, File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            return reader.lines()
                    .filter(line -> line.startsWith("@import"))
                    .map(line -> line.replace("@import ", "").replace("\"", "").replace("'", "").replace(";", ""))
                    .map(line -> {
                        if (!line.endsWith(".css") && !line.endsWith(".less")) {
                            return line + ".less";
                        }
                        return line;
                    })
                    .collect(Collectors.toSet());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
