package com.beowulfe.hap.impl.jmdns;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmdnsHomekitAdvertiser {
	
	private static final String SERVICE_TYPE = "_hap._tcp.local.";
	
	private final JmDNS jmdns;
	private boolean discoverable = true;
	private final static Logger logger = LoggerFactory.getLogger(JmdnsHomekitAdvertiser.class);
	private boolean isAdvertising = false;
	
	private String label;
	private String mac;
	private int port;
	
	public JmdnsHomekitAdvertiser(InetAddress localAddress) throws UnknownHostException, IOException {
		jmdns = JmDNS.create(localAddress);
	}

	public synchronized void advertise(String label, String mac, int port) throws Exception {
		if (isAdvertising) {
			throw new IllegalStateException("Homekit advertiser is already running");
		}
		this.label = label;
		this.mac = mac;
		this.port = port;
		
		logger.info("Advertising accessory "+label);
	
		registerService();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    		logger.info("Stopping advertising in response to shutdown.");
    		jmdns.unregisterAllServices();
    	}));
		isAdvertising = true;
	}
	
	public void stop() {
		jmdns.unregisterAllServices();
	}
	
	public void setDiscoverable(boolean discoverable) throws IOException {
		if (this.discoverable != discoverable) {
			this.discoverable = discoverable;
			if (isAdvertising) {
				logger.info("Re-creating service due to change in discoverability to "+discoverable);
				jmdns.unregisterAllServices();
				registerService();
			}
		}
	}
	
	private void registerService() throws IOException {
		logger.info("Registering "+SERVICE_TYPE+" on port "+port);
		Map<String, String> props = new HashMap<>();
		props.put("sf", discoverable ? "1" : "0");
		props.put("id", mac);
		props.put("md", label);
		props.put("c#", "1");
		props.put("s#", "1");
		props.put("ff", "0");
		props.put("ci", "1");
		jmdns.registerService(ServiceInfo.create(SERVICE_TYPE, label, port, 1, 1, props));
	}
	
}
