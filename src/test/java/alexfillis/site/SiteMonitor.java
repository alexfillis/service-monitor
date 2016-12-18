package alexfillis.site;

import alexfillis.monitor.AbstractMonitor;

public class SiteMonitor extends AbstractMonitor {

    private final Site site;

    public SiteMonitor(Site site) {
        this.site = site;
    }

    protected void refreshInternal() throws SiteException {
        String response = site.ping();
        if ("SUCCESS".equals(response)) {
            success();
        } else {
            failure();
        }
    }
}
