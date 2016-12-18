package test.alexfillis.file;

import alexfillis.file.FileMonitor;
import alexfillis.file.FileSystem;
import alexfillis.monitor.Monitor;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FileMonitorTest {
    private static final String PATH_MONITORING = "/tmp";

    private final FileSystem fileSystem = mock(FileSystem.class);
    private final Monitor monitor = new FileMonitor(fileSystem, PATH_MONITORING);

    @Test
    public void none_status_before_monitor_refresh() throws Exception {
        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.none, status.getResult());
    }

    @Test
    public void success_status_after_refresh_success() throws Exception {
        // given
        when(fileSystem.exists(PATH_MONITORING)).thenReturn(true);
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.success, status.getResult());
    }

    @Test
    public void failure_status_after_refresh_exception_failure() throws Exception {
        // given
        when(fileSystem.exists(PATH_MONITORING)).thenThrow(new IOException());
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.failure, status.getResult());
    }

    @Test
    public void failure_status_after_unsuccessful_refresh_response() throws Exception {
        // given
        when(fileSystem.exists(PATH_MONITORING)).thenReturn(false);
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.failure, status.getResult());
    }

    @Test
    public void disabled_monitor_should_not_call_what_it_is_monitoring() throws Exception {
        // given
        monitor.disable();
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        verify(fileSystem, never()).exists(PATH_MONITORING);
        assertEquals(Monitor.Status.Result.disabled, status.getResult());
    }}
