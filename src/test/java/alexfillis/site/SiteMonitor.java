package alexfillis.site;

import alexfillis.monitor.Monitor;

import static alexfillis.monitor.Monitor.Status.Result.failure;
import static alexfillis.monitor.Monitor.Status.Result.none;
import static alexfillis.monitor.Monitor.Status.Result.success;

public class SiteMonitor implements Monitor {

    private final Site site;
    private Status status = new Status(none);

    public SiteMonitor(Site site) {
        this.site = site;
    }

    public Status status() {
        return status;
    }

    public void refresh() {
        try {
            site.ping();
            status = new Status(success);
        } catch (Exception e) {
            status = new Status(failure);
        }
    }
}
