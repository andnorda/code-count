package codecount.resources;

import codecount.dtos.Commit;
import codecount.services.CommitsService;

import java.util.Collection;

public class CommitsResource {
    private final CommitsService service;

    public CommitsResource(CommitsService service) {
        this.service = service;
    }

    public Collection<Commit> getCommits(String remoteUrl) {
        return service.getCommits(remoteUrl);
    }
}
