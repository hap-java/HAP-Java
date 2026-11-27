package io.github.hapjava.server.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.server.HomekitAccessoryCategories;
import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.HomekitWebHandler;
import io.github.hapjava.server.impl.http.HomekitClientConnectionFactory;
import io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser;
import java.io.IOException;
import java.net.BindException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HomekitRootTest {

  private HomekitAccessory accessory;
  private HomekitRoot root;
  private HomekitWebHandler webHandler;
  private JmdnsHomekitAdvertiser advertiser;
  private HomekitAuthInfo authInfo;

  private static final int PORT = 12345;
  private static final String SETUPID = "Gx12";

  private static final String LABEL = "Test Label";

  @Before
  public void setup() throws Exception {
    accessory = mock(HomekitAccessory.class);
    when(accessory.getId()).thenReturn(2);
    webHandler = mock(HomekitWebHandler.class);
    when(webHandler.start(any())).thenReturn(CompletableFuture.completedFuture(PORT));
    advertiser = mock(JmdnsHomekitAdvertiser.class);
    authInfo = mock(HomekitAuthInfo.class);
    root =
        new HomekitRoot(LABEL, HomekitAccessoryCategories.OTHER, webHandler, authInfo, advertiser);
  }

  @Test
  public void verifyRegistryAdded() throws Exception {
    root.addAccessory(accessory);
    Assert.assertTrue(
        "Registry does not contain accessory",
        root.getRegistry().getAccessories().contains(accessory));
  }

  @Test
  public void verifyRegistryRemoved() throws Exception {
    root.addAccessory(accessory);
    root.removeAccessory(accessory);
    Assert.assertFalse(
        "Registry still contains accessory",
        root.getRegistry().getAccessories().contains(accessory));
  }

  @Test
  public void testWebHandlerStarts() throws Exception {
    root.start();
    verify(webHandler).start(any(HomekitClientConnectionFactory.class));
  }

  @Test
  public void testFailedWebHandlerStartIsCleanedUp() throws Exception {
    CompletableFuture<Integer> bind = new CompletableFuture<>();
    when(webHandler.start(any())).thenReturn(bind);

    root.start();
    bind.completeExceptionally(new BindException("Address not available"));

    verify(webHandler).stop();
    verify(advertiser).stop();
    root.setConfigurationIndex(2);
    verify(advertiser, never()).setConfigurationIndex(2);
  }

  @Test
  public void testStartAsyncWaitsForWebHandler() throws Exception {
    CompletableFuture<Integer> bind = new CompletableFuture<>();
    when(webHandler.start(any())).thenReturn(bind);

    CompletableFuture<Void> start = root.startAsync();

    Assert.assertFalse(start.isDone());
    verify(advertiser, never()).advertise(any(), anyInt(), any(), anyInt(), anyInt(), any());

    bind.complete(PORT);

    start.join();
    verify(advertiser).advertise(eq(LABEL), eq(1), any(), eq(PORT), eq(1), any());
    verify(webHandler, never()).stop();
  }

  @Test
  public void testStartAsyncPropagatesBindFailure() throws Exception {
    CompletableFuture<Integer> bind = new CompletableFuture<>();
    when(webHandler.start(any())).thenReturn(bind);
    BindException failure = new BindException("Address not available");

    CompletableFuture<Void> start = root.startAsync();
    bind.completeExceptionally(failure);

    Assert.assertSame(
        failure, Assert.assertThrows(CompletionException.class, start::join).getCause());
    verify(webHandler).stop();
    verify(advertiser).stop();
    verify(advertiser, never()).advertise(any(), anyInt(), any(), anyInt(), anyInt(), any());
    root.setConfigurationIndex(2);
    verify(advertiser, never()).setConfigurationIndex(2);
  }

  @Test
  public void testStartAsyncPropagatesSynchronousFailure() throws Exception {
    RuntimeException failure = new IllegalStateException("HTTP startup failed");
    when(webHandler.start(any())).thenThrow(failure);

    CompletableFuture<Void> start = root.startAsync();

    Assert.assertTrue(start.isCompletedExceptionally());
    Assert.assertSame(
        failure, Assert.assertThrows(CompletionException.class, start::join).getCause());
    verify(webHandler).stop();
    verify(advertiser).stop();
    verify(advertiser, never()).advertise(any(), anyInt(), any(), anyInt(), anyInt(), any());
    root.setConfigurationIndex(2);
    verify(advertiser, never()).setConfigurationIndex(2);
  }

  @Test
  public void testStartCleansUpSynchronousFailure() throws Exception {
    when(webHandler.start(any())).thenThrow(new IllegalStateException("HTTP startup failed"));

    root.start();

    verify(webHandler).stop();
    verify(advertiser).stop();
    root.setConfigurationIndex(2);
    verify(advertiser, never()).setConfigurationIndex(2);
  }

  @Test
  public void testDuplicateStartDoesNotStopPendingStartup() throws Exception {
    CompletableFuture<Integer> bind = new CompletableFuture<>();
    when(webHandler.start(any())).thenReturn(bind);

    CompletableFuture<Void> first = root.startAsync();
    CompletableFuture<Void> duplicate = root.startAsync();

    Assert.assertTrue(duplicate.isCompletedExceptionally());
    Assert.assertTrue(
        Assert.assertThrows(CompletionException.class, duplicate::join).getCause()
            instanceof IllegalStateException);
    Assert.assertFalse(first.isDone());
    verify(webHandler, times(1)).start(any());
    verify(webHandler, never()).stop();
    verify(advertiser, never()).stop();

    bind.complete(PORT);

    Assert.assertTrue(first.isDone());
    first.join();
    verify(advertiser).advertise(eq(LABEL), eq(1), any(), eq(PORT), eq(1), any());
  }

  @Test
  public void testDuplicateStartDoesNotStopRunningRoot() throws Exception {
    root.startAsync().join();

    CompletableFuture<Void> duplicate = root.startAsync();

    Assert.assertTrue(duplicate.isCompletedExceptionally());
    Assert.assertTrue(
        Assert.assertThrows(CompletionException.class, duplicate::join).getCause()
            instanceof IllegalStateException);
    verify(webHandler, times(1)).start(any());
    verify(webHandler, never()).stop();
    verify(advertiser, never()).stop();
    root.setConfigurationIndex(2);
    verify(advertiser).setConfigurationIndex(2);
  }

  @Test
  public void testStartAsyncPropagatesAdvertisingFailure() throws Exception {
    IOException failure = new IOException("mDNS registration failed");
    doThrow(failure).when(advertiser).advertise(any(), anyInt(), any(), anyInt(), anyInt(), any());

    CompletableFuture<Void> start = root.startAsync();

    Assert.assertSame(
        failure, Assert.assertThrows(CompletionException.class, start::join).getCause());
    verify(webHandler).stop();
    verify(advertiser).stop();
    root.setConfigurationIndex(2);
    verify(advertiser, never()).setConfigurationIndex(2);
  }

  @Test
  public void testWebHandlerStops() throws Exception {
    root.start();
    root.stop();
    verify(webHandler).stop();
  }

  @Test
  public void testAdvertiserStarts() throws Exception {
    final String mac = "00:00:00:00:00:00";
    when(authInfo.getMac()).thenReturn(mac);
    when(authInfo.getSetupId()).thenReturn(SETUPID);

    root.start();
    verify(advertiser).advertise(eq(LABEL), eq(1), eq(mac), eq(PORT), eq(1), eq(SETUPID));
  }

  @Test
  public void testAdvertiserStops() throws Exception {
    root.start();
    root.stop();
    verify(advertiser).stop();
  }

  @Test
  public void testAddAccessoryDoesntResetWeb() {
    root.start();
    root.addAccessory(accessory);
    verify(webHandler, never()).resetConnections();
  }

  @Test
  public void testRemoveAccessoryDoesntResetWeb() {
    root.addAccessory(accessory);
    root.start();
    root.removeAccessory(accessory);
    verify(webHandler, never()).resetConnections();
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testAddIndexOneAccessory() throws Exception {
    when(accessory.getId()).thenReturn(1);
    root.addAccessory(accessory);
  }
}
