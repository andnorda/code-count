package codecount.resources;

import codecount.dtos.Contributor;
import codecount.services.ContributorService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("contributors")
@Produces(MediaType.APPLICATION_JSON)
public class ContributorResource {
    private final ContributorService service;

    public ContributorResource(ContributorService service) {
        this.service = service;
    }

    @GET
    public Collection<Contributor> getContributors(
            @QueryParam("repo")String remoteUrl) {
        return service.getContributors(remoteUrl);
    }
}
