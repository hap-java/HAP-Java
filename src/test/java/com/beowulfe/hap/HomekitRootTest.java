package com.beowulfe.hap;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.concurrent.CompletableFuture;

import org.junit.*;

import com.beowulfe.hap.impl.HomekitWebHandler;
import com.beowulfe.hap.impl.http.HomekitClientConnectionFactory;
import com.beowulfe.hap.impl.jmdns.JmdnsHomekitAdvertiser;

public class HomekitRootTest {
	
	private HomekitAccessory accessory;
	private HomekitRoot root;
	private HomekitWebHandler webHandler;
	private JmdnsHomekitAdvertiser advertiser;
	private HomekitAuthInfo authInfo;
	
	private final static int PORT = 12345;
	private final static String LABEL = "Test Label";
	
	@Before
	public void setup() throws Exception {
		accessory = mock(HomekitAccessory.class);
		when(accessory.getId()).thenReturn(2);
		webHandler = mock(HomekitWebHandler.class);
		when(webHandler.start(any())).thenReturn(CompletableFuture.completedFuture(PORT));
		advertiser = mock(JmdnsHomekitAdvertiser.class);
		authInfo = mock(HomekitAuthInfo.class);
		root = new HomekitRoot(LABEL, webHandler, authInfo, advertiser);
	}
	
	@Test
	public void verifyRegistryAdded() throws Exception {
		root.addAccessory(accessory);
		Assert.assertTrue("Registry does not contain accessory", root.getRegistry().getAccessories().contains(accessory));
	}
	
	@Test
	public void verifyRegistryRemoved() throws Exception {
		root.addAccessory(accessory);
		root.removeAccessory(accessory);
		Assert.assertFalse("Registry still contains accessory", root.getRegistry().getAccessories().contains(accessory));
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
		String mac = "00:00:00:00:00:00";
		when(authInfo.getMac()).thenReturn(mac);
		root.start();
		verify(advertiser).advertise(eq(LABEL), eq(mac), eq(PORT));
	}
	
	@Test
	public void testAdvertiserStops() throws Exception {
		root.start();
		root.stop();
		verify(advertiser).stop();
	}
	
	@Test
	public void testAddAccessoryResetsWeb() {
		root.start();
		verify(webHandler, never()).resetConnections();
		root.addAccessory(accessory);
		verify(webHandler).resetConnections();
	}
	
	@Test
	public void testRemoveAccessoryResetsWeb() {
		root.addAccessory(accessory);
		root.start();
		verify(webHandler, never()).resetConnections();
		root.removeAccessory(accessory);
		verify(webHandler).resetConnections();
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void testAddIndexOneAccessory() throws Exception {
		when(accessory.getId()).thenReturn(1);
		root.addAccessory(accessory);
	}
}
