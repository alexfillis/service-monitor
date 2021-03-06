package alexfillis.monitor;

import static alexfillis.monitor.Monitor.Status.Result.*;

public abstract class AbstractMonitor implements Monitor {
    private volatile Status status = new Status(none);
    private volatile boolean muted;

    public final Status status() {
        if (!muted) {
            return status;
        } else {
            return new Status(Status.Result.muted);
        }
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

    public void enable() {
        status = new Status(none);
    }

    public void disable() {
        status = new Status(disabled);
    }

    public void mute() {
        muted = true;
    }

    public void unmute() {
        muted = false;
    }

    protected abstract void refreshInternal() throws Exception;
}
