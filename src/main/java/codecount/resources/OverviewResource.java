package codecount.resources;

import codecount.dtos.Overview;
import codecount.services.OverviewService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("overview")
@Produces(MediaType.APPLICATION_JSON)
public class OverviewResource {
    private final OverviewService service;

    public OverviewResource(OverviewService service) {
        this.service = service;
    }

    @GET
    public Overview getOverview(
            @QueryParam("repo") String remoteUrl) {
        return service.getOverview(remoteUrl);
    }
}
