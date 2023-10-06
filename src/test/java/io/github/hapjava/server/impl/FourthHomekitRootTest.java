package io.github.hapjava.server.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.hapjava.accessories.HomekitAccessory;
import io.github.hapjava.server.HomekitAccessoryCategories;
import io.github.hapjava.server.HomekitAuthInfo;
import io.github.hapjava.server.HomekitWebHandler;
import io.github.hapjava.server.impl.jmdns.JmdnsHomekitAdvertiser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FourthHomekitRootTest {

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
}
