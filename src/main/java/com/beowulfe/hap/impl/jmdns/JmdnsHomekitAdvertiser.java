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
	
	private final static Logger logger = LoggerFactory.getLogger(JmdnsHomekitAdvertiser.class);
	
	public JmdnsHomekitAdvertiser(InetAddress localAddress) throws UnknownHostException, IOException {
		jmdns = JmDNS.create(localAddress);
	}

	public void advertise(String label, String mac, int port) throws Exception {
		logger.info("Advertising accessory "+label);
	
		logger.info("Registering "+SERVICE_TYPE+" on port "+port);
		Map<String, String> props = new HashMap<>();
		props.put("sf", "1");
		props.put("pv", "1.0");
		props.put("id", mac);
		props.put("md", label);
		props.put("c#", "1");
		props.put("s#", "1");
		props.put("ff", "0");
		jmdns.registerService(ServiceInfo.create(SERVICE_TYPE, label, port, 1, 1, props));
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
    		logger.info("Stopping advertising in response to shutdown.");
    		jmdns.unregisterAllServices();
    	}));
	}
	
	public void stop() {
		jmdns.unregisterAllServices();
	}
	
}
