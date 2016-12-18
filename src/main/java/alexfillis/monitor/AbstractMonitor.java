package alexfillis.monitor;

import java.io.IOException;

import static alexfillis.monitor.Monitor.Status.Result.failure;
import static alexfillis.monitor.Monitor.Status.Result.none;
import static alexfillis.monitor.Monitor.Status.Result.success;

public abstract class AbstractMonitor implements Monitor {
    private volatile Status status = new Status(none);

    public final Status status() {
        return status;
    }

    protected final void failure() {
        status = new Status(failure);
    }

    protected final void success() {
        status = new Status(success);
    }

    public final void refresh() {
        try {
            refreshInternal();
        } catch (Exception e) {
            failure();
        }
    }

    protected abstract void refreshInternal() throws IOException;
}
