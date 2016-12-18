package alexfillis.file;

import alexfillis.monitor.Monitor;

import static alexfillis.monitor.Monitor.Status.Result.failure;
import static alexfillis.monitor.Monitor.Status.Result.none;
import static alexfillis.monitor.Monitor.Status.Result.success;

public class FileMonitor implements Monitor {
    private final FileSystem fileSystem;
    private final String pathMonitoring;
    private Status status = new Status(none);

    public FileMonitor(FileSystem fileSystem, String pathMonitoring) {
        this.fileSystem = fileSystem;
        this.pathMonitoring = pathMonitoring;
    }

    public Status status() {
        return status;
    }

    public void refresh() {
        try {
            boolean exists = fileSystem.exists(pathMonitoring);
            if (exists) {
                status = new Status(success);
            } else {
                status = new Status(failure);
            }
        } catch (Exception e) {
            status = new Status(failure);
        }
    }
}
