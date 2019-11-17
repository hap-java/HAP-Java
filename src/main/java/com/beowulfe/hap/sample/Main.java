package com.beowulfe.hap.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import io.github.hapjava.HomekitRoot;
import io.github.hapjava.HomekitServer;

public class Main {

    private static final int PORT = 9123;

    public static void main(String[] args) {
        try {
            File authFile = new File("auth-state.bin");
            MockAuthInfo mockAuth;
            if (authFile.exists()) {
                FileInputStream fileInputStream = new FileInputStream(authFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    System.out.println("Using persisted auth");
                    AuthState authState = (AuthState) objectInputStream.readObject();
                    mockAuth = new MockAuthInfo(authState);
                } finally {
                    objectInputStream.close();
                }
            } else {
                mockAuth = new MockAuthInfo();
            }

            HomekitServer homekit = new HomekitServer(PORT);
            HomekitRoot bridge = homekit.createBridge(mockAuth, "TestBridge", "TestBridge, Inc.", "G6", "111abe234");

            mockAuth.onChange(state -> {
                try {
                    System.out.println("State has changed! Writing");
                    FileOutputStream fileOutputStream = new FileOutputStream(authFile);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(state);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bridge.addAccessory(new MockSwitch());
            bridge.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
