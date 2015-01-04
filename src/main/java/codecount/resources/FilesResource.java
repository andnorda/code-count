package codecount.resources;

import codecount.dtos.FileLineCount;
import codecount.services.FilesService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("files")
@Produces(MediaType.APPLICATION_JSON)
public class FilesResource {
    private final FilesService service;

    public FilesResource(FilesService service) {
        this.service = service;
    }

    @GET
    @Path("/linecount")
    public Collection<FileLineCount> getFileLineCounts(
            @QueryParam("repo") String remoteUrl) {
        return service.getFileLineCounts(remoteUrl);
    }
}
