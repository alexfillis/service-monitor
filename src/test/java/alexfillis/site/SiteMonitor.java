package alexfillis.site;

import alexfillis.monitor.Monitor;

import static alexfillis.monitor.Monitor.Status.Result.none;
import static alexfillis.monitor.Monitor.Status.Result.success;

public class SiteMonitor implements Monitor {

    private Status status = new Status(none);

    public SiteMonitor(Site site) {
        super();
    }

    public Status status() {
        return status;
    }

    public void refresh() {
        status = new Status(success);
    }
}
