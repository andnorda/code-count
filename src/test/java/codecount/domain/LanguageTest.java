package codecount.domain;

import com.google.common.base.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class LanguageTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void returns_correct_language() throws Exception {
        isAbsent(folder.newFolder());
        isAbsent(new File("README"));

        hasLanguage(new File("index.js"), Language.JAVA_SCRIPT);
        hasLanguage(new File("Date.java"), Language.JAVA);
        hasLanguage(new File("git.c"), Language.C);
        hasLanguage(new File("mixins.less"), Language.LESS);
        hasLanguage(new File("normalize.css"), Language.CSS);
    }

    private void isAbsent(File readme) {
        assertThat(Language.valueOf(readme), is(Optional.absent()));
    }

    private void hasLanguage(File file, Language java) {
        assertThat(Language.valueOf(file), is(Optional.of(java)));
    }
}
