package codecount.resources;

import codecount.dtos.Commit;
import codecount.dtos.CommitDetails;
import codecount.services.CommitsService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("commits")
@Produces(MediaType.APPLICATION_JSON)
public class CommitsResource {
    private final CommitsService service;

    public CommitsResource(CommitsService service) {
        this.service = service;
    }

    @GET
    public Collection<Commit> getCommits(
            @QueryParam("repo") String remoteUrl) {
        return service.getCommits(remoteUrl);
    }

    @GET
    @Path("/{hash}")
    public CommitDetails getCommitDetails(
            @QueryParam("repo") String remoteUrl,
            @PathParam("hash") String hash) {
        return service.getCommitDetails(remoteUrl, hash);
    }
}
