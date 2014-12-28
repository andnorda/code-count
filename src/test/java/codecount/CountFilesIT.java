package codecount;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CountFilesIT {
    @Test
    public void test() throws Exception {
        assertThat(new FileCount("https://github.com/github/testrepo.git").count(), is(9));
    }
}
