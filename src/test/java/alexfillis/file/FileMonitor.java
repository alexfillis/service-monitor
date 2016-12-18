package alexfillis.file;

import alexfillis.monitor.AbstractMonitor;

import java.io.IOException;

public class FileMonitor extends AbstractMonitor {
    private final FileSystem fileSystem;
    private final String pathMonitoring;

    public FileMonitor(FileSystem fileSystem, String pathMonitoring) {
        this.fileSystem = fileSystem;
        this.pathMonitoring = pathMonitoring;
    }

    protected void refreshInternal() throws IOException {
        boolean exists = fileSystem.exists(pathMonitoring);
        if (exists) {
            success();
        } else {
            failure();
        }
    }
}
