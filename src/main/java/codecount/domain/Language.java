package codecount.domain;

import com.google.common.base.Optional;

import java.io.File;

public enum Language {
    JAVA_SCRIPT(".js"), JAVA(".java"), C(".c"), LESS(".less"), CSS(".css");
    private String suffix;

    Language(String suffix) {
        this.suffix = suffix;
    }

    public static Optional<Language> valueOf(File file) {
        for (Language language : values()) {
            if (file.getName().endsWith(language.suffix)) {
                return Optional.of(language);
            }
        }
        return Optional.absent();
    }
}
