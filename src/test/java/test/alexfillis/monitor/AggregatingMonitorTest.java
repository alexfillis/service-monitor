package test.alexfillis.monitor;

import alexfillis.monitor.AggregatingMonitor;
import alexfillis.monitor.Monitor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AggregatingMonitorTest {

    private final Monitor monitor1 = mock(Monitor.class);
    private final Monitor monitor2 = mock(Monitor.class);
    private final Monitor monitor3 = mock(Monitor.class);
    private final Monitor monitor = new AggregatingMonitor(monitor1, monitor2, monitor3);

    @Test
    public void failure_when_a_monitor_is_failing() throws Exception {
        // given
        when(monitor1.status()).thenReturn(new Monitor.Status(Monitor.Status.Result.success));
        when(monitor2.status()).thenReturn(new Monitor.Status(Monitor.Status.Result.success));
        when(monitor3.status()).thenReturn(new Monitor.Status(Monitor.Status.Result.failure));

        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.failure, status.getResult());
    }

    @Test
    public void success_when_all_monitors_are_successful() throws Exception {
        // given
        when(monitor1.status()).thenReturn(new Monitor.Status(Monitor.Status.Result.success));
        when(monitor2.status()).thenReturn(new Monitor.Status(Monitor.Status.Result.success));
        when(monitor3.status()).thenReturn(new Monitor.Status(Monitor.Status.Result.success));

        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.success, status.getResult());
    }

    @Test
    public void refreshing_should_refresh_all_monitors() throws Exception {
        // given

        // when
        monitor.refresh();

        // then
        verify(monitor1, times(1)).refresh();
        verify(monitor2, times(1)).refresh();
        verify(monitor3, times(1)).refresh();
    }
}
