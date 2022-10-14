package io.github.hapjava.server.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.server.HomekitAccessoryCategories;
import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.HomekitWebHandler;
import io.github.hapjava.server.impl.http.HomekitClientConnectionFactory;
import io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser;
import java.util.concurrent.CompletableFuture;
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
