package test.alexfillis.site;

import alexfillis.monitor.Monitor;
import alexfillis.site.Site;
import alexfillis.site.SiteMonitor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SiteMonitorTest {
    private final Site site = mock(Site.class);
    private final Monitor monitor = new SiteMonitor(site);

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
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.success, status.getResult());
    }

    @Test
    public void failure_status_after_refresh_exception_failure() throws Exception {
        // given
        when(site.ping()).thenThrow(new RuntimeException());
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.failure, status.getResult());
    }
}
