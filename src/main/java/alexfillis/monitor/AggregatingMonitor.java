package alexfillis.monitor;

import java.util.ArrayList;
import java.util.List;

public class AggregatingMonitor extends AbstractMonitor {
    private final Monitor[] monitors;

    public AggregatingMonitor(Monitor... monitors) {
        super();
        this.monitors = monitors;
    }

    @Override
    protected void refreshInternal() throws Exception {
        List<Status> statuses = new ArrayList<>();
        for (Monitor monitor : monitors) {
            monitor.refresh();
            statuses.add(monitor.status());
        }

        int failureCount = 0;
        for (Status status : statuses) {
            if (status.getResult().equals(Status.Result.failure)) {
                failureCount++;
            }
        }

        if (failureCount > 0) {
            failure();
        } else {
            success();
        }
    }
}
