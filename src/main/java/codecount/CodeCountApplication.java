package codecount;

import codecount.repository.GitRepoRepository;
import codecount.resources.CommitsResource;
import codecount.resources.FilesResource;
import codecount.resources.OverviewResource;
import codecount.services.CommitsService;
import codecount.services.FilesService;
import codecount.services.OverviewService;
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
        GitRepoRepository repo = new GitRepoRepository(root);
        environment.jersey().register(new OverviewResource(new OverviewService(repo)));
        environment.jersey().register(new CommitsResource(new CommitsService(repo)));
        environment.jersey().register(new FilesResource(new FilesService(repo)));
    }
}
