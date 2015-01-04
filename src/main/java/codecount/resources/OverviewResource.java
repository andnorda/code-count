package codecount.resources;

import codecount.dtos.Overview;
import codecount.sevices.OverviewService;

public class OverviewResource {
    private final OverviewService service;

    public OverviewResource(OverviewService service) {
        this.service = service;
    }

    public Overview getOverview(String remoteUrl) {
        return service.getOverview(remoteUrl);
    }
}
