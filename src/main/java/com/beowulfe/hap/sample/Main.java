package com.beowulfe.hap.sample;

import com.beowulfe.hap.HomekitRoot;
import com.beowulfe.hap.HomekitServer;

public class Main {
	
	private static final int PORT = 9123;
	
	public static void main(String[] args) {
		try {
			HomekitServer homekit = new HomekitServer(PORT);
			HomekitRoot bridge = homekit.createBridge(new MockAuthInfo(), "Test Bridge", "TestBridge, Inc.", "G6", "111abe234");
			bridge.addAccessory(new MockSwitch());
			bridge.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}