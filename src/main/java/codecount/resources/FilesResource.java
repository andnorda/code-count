package codecount.resources;

import codecount.domain.Language;
import codecount.dtos.FileInterdependencies;
import codecount.dtos.FileLineCount;
import codecount.services.FilesService;

import javax.ws.rs.*;
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

    @GET
    @Path("/interdependencies/{language}")
    public Collection<FileInterdependencies> getFileInterdependencies(
            @QueryParam("repo") String remoteUrl,
            @PathParam("language") String language) {
        return service.getFileInterdependencies(remoteUrl, Language.valueOf(language));
    }
}
