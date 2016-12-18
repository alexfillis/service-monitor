package alexfillis.monitor;

import java.io.IOException;

import static alexfillis.monitor.Monitor.Status.Result.*;

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
            if (!disabled()) {
                refreshInternal();
            }
        } catch (Exception e) {
            failure();
        }
    }

    private boolean disabled() {
        return status.getResult().equals(disabled);
    }

    public void disable() {
        status = new Status(disabled);
    }

    protected abstract void refreshInternal() throws IOException;
}
