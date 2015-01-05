package codecount.resources;

import codecount.dtos.Commit;
import codecount.dtos.Contributor;
import codecount.services.CommitsService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    @Path("/contributors")
    public Collection<Contributor> getContributors(
            @QueryParam("repo")String remoteUrl) {
        return service.getContributors(remoteUrl);
    }
}
