package codecount;

import codecount.repository.GitRepoRepository;
import codecount.resources.OverviewResource;
import codecount.sevices.OverviewService;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import java.io.File;

public class CodeCountApplication extends Application<CodeCountConfiguration> {
    public static void main(String[] args) throws Exception {
        new CodeCountApplication().run(args);
    }

    @Override
    public void run(CodeCountConfiguration codeCountConfiguration, Environment environment) throws Exception {
        File root = new File("repos");
        if (!root.exists()) {
            root.mkdir();
        }
        environment.jersey().register(new OverviewResource(new OverviewService(new GitRepoRepository(root))));
    }
}
