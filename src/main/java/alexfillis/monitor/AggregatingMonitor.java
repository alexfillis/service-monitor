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

        if (failureCount(statuses) > 0) {
            failure();
        } else {
            success();
        }
    }

    private int failureCount(List<Status> statuses) {
        int failureCount = 0;
        for (Status status : statuses) {
            if (status.getResult().equals(Status.Result.failure)) {
                failureCount++;
            }
        }
        return failureCount;
    }
}
