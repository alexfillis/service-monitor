package alexfillis.site;

import alexfillis.monitor.Monitor;

public class SiteMonitor implements Monitor {
    public SiteMonitor(Site site) {
        super();
    }

    public Status status() {
        return new Status(Status.Result.none);
    }
}
