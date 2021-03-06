package test.alexfillis.site;

import alexfillis.monitor.Monitor;
import alexfillis.site.Site;
import alexfillis.site.SiteException;
import alexfillis.site.SiteMonitor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
        when(site.ping()).thenReturn("SUCCESS");
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.success, status.getResult());
    }

    @Test
    public void failure_status_after_refresh_exception_failure() throws Exception {
        // given
        when(site.ping()).thenThrow(new SiteException());
        monitor.refresh();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.failure, status.getResult());
    }

    @Test
    public void failure_status_after_unsuccessful_refresh_response() throws Exception {
        // given
        when(site.ping()).thenReturn("FAIL");
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
        verify(site, never()).ping();
        assertEquals(Monitor.Status.Result.disabled, status.getResult());
    }

    @Test
    public void none_status_for_disabled_monitor_that_is_enabled_but_not_refreshed() throws Exception {
        // given
        monitor.disable();
        monitor.enable();

        // when
        Monitor.Status status = monitor.status();

        // then
        verify(site, never()).ping();
        assertEquals(Monitor.Status.Result.none, status.getResult());
    }

    @Test
    public void disabled_monitor_that_is_enabled_and_refreshed_calls_thing_monitoring() throws Exception {
        // given
        monitor.disable();
        monitor.enable();
        monitor.refresh();

        // when
        monitor.status();

        // then
        verify(site).ping();
    }

    @Test
    public void muted_status_after_muting_monitor() throws Exception {
        // given
        monitor.refresh();
        monitor.mute();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(Monitor.Status.Result.muted, status.getResult());
    }

    @Test
    public void muted_monitor_that_is_unmuted_will_return_last_status() throws Exception {
        // given
        monitor.refresh();
        Monitor.Status statusBeforeMute = monitor.status();

        monitor.mute();
        monitor.unmute();

        // when
        Monitor.Status status = monitor.status();

        // then
        assertEquals(statusBeforeMute, status);
    }
}
