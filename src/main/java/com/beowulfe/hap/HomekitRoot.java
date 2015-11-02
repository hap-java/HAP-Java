package com.beowulfe.hap;

import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.HomekitWebHandler;
import com.beowulfe.hap.impl.accessories.Bridge;
import com.beowulfe.hap.impl.connections.HomekitClientConnectionFactoryImpl;
import com.beowulfe.hap.impl.connections.SubscriptionManager;
import com.beowulfe.hap.impl.jmdns.JmdnsHomekitAdvertiser;

/**
 * Provides advertising and handling for Homekit accessories. This class handles the advertising of Homekit accessories and 
 * contains one or more accessories. When implementing a bridge accessory, you will interact with this class directly. Instantiate
 * it via {@link HomekitServer#createBridge(HomekitAuthInfo, String, String, String, String)}. For single accessories, this is composed
 * by {@link HomekitStandaloneAccessoryServer}.
 *
 * @author Andy Lintner
 */
public class HomekitRoot {
	
	private final static Logger logger = LoggerFactory.getLogger(HomekitRoot.class);
	
	private final JmdnsHomekitAdvertiser advertiser;
	private final HomekitWebHandler webHandler;
	private final HomekitAuthInfo authInfo;
	private final String label;
	private final HomekitRegistry registry;
	private final SubscriptionManager subscriptions = new SubscriptionManager();
	private boolean started = false;

	HomekitRoot(String label, HomekitWebHandler webHandler, InetAddress localhost, 
			HomekitAuthInfo authInfo) throws IOException {
		this(label, webHandler, authInfo, new JmdnsHomekitAdvertiser(localhost));
	}
	
	HomekitRoot(String label, HomekitWebHandler webHandler, HomekitAuthInfo authInfo, 
			JmdnsHomekitAdvertiser advertiser) throws IOException {
		this.advertiser = advertiser;
		this.webHandler = webHandler;
		this.authInfo = authInfo;
		this.label = label;
		this.registry = new HomekitRegistry(label);
	}
	
	/**
	 * Add an accessory to be handled and advertised by this root. Any existing Homekit connections will be terminated to allow
	 * the clients to reconnect and see the updated accessory list. When using this for a bridge, the ID of the accessory must be
	 * greater than 1, as that ID is reserved for the Bridge itself.
	 * 
	 * @param accessory to advertise and handle.
	 */
	public void addAccessory(HomekitAccessory accessory) {
		if (accessory.getId() <= 1 && !(accessory instanceof Bridge)) {
			throw new IndexOutOfBoundsException("The ID of an accessory used in a bridge must be greater than 1");
		}
		addAccessorySkipRangeCheck(accessory);
	}
	
	/**
	 * Skips the range check. Used by {@link HomekitStandaloneAccessoryServer} as well as {@link #addAccessory(HomekitAccessory)};
	 * 
	 * @param accessory to advertise and handle.
	 */
	void addAccessorySkipRangeCheck(HomekitAccessory accessory) {
		this.registry.add(accessory);
		logger.info("Added accessory "+accessory.getLabel());
		if (started) {
			registry.reset();
			webHandler.resetConnections();
		}
	}
	
	/**
	 * Removes an accessory from being handled or advertised by this root. Any existing Homekit connections will be terminated to allow
	 * the clients to reconnect and see the updated accessory list.
	 * 
	 * @param accessory accessory to cease advertising and handling
	 */
	public void removeAccessory(HomekitAccessory accessory) {
		this.registry.remove(accessory);
		if (started) {
			registry.reset();
			webHandler.resetConnections();
		}
	}
	
	/**
	 * Starts advertising and handling the previously added Homekit accessories. You should try to call this after you have used the 
	 * {@link #addAccessory(HomekitAccessory)} method to add all the initial accessories you plan on advertising, as any later additions
	 * will cause the Homekit clients to reconnect.
	 */
	public void start() {
		started = true;
		registry.reset();
		webHandler.start(new HomekitClientConnectionFactoryImpl(authInfo,
				registry, subscriptions, advertiser)).thenAccept(port -> {
					try {
						advertiser.setDiscoverable(authInfo.hasUser());
						advertiser.advertise(label, authInfo.getMac(), port);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
		});
	}
	
	/**
	 * Stops advertising and handling the Homekit accessories.
	 */
	public void stop() {
		advertiser.stop();
		webHandler.stop();
		started = false;
	}
	
	HomekitRegistry getRegistry() {
		return registry;
	}

}
