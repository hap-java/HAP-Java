package com.beowulfe.hap.impl.connections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.function.Consumer;

import org.bouncycastle.util.Pack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beowulfe.hap.HomekitAuthInfo;
import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.crypto.ChachaDecoder;
import com.beowulfe.hap.impl.crypto.ChachaEncoder;
import com.beowulfe.hap.impl.http.*;
import com.beowulfe.hap.impl.jmdns.JmdnsHomekitAdvertiser;
import com.beowulfe.hap.impl.pairing.UpgradeResponse;

class ConnectionImpl implements HomekitClientConnection {

	private final HttpSession httpSession;
	private LengthPrefixedByteArrayProcessor binaryProcessor;
	private int inboundBinaryMessageCount = 0;
	private int outboundBinaryMessageCount = 0;
	private byte[] readKey;
	private byte[] writeKey;
	private boolean isUpgraded = false;
	private final Consumer<HttpResponse> outOfBandMessageCallback;
	private final SubscriptionManager subscriptions;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(HomekitClientConnection.class);
	
	public ConnectionImpl(HomekitAuthInfo authInfo, HomekitRegistry registry, 
			Consumer<HttpResponse> outOfBandMessageCallback, SubscriptionManager subscriptions,
			JmdnsHomekitAdvertiser advertiser) {
		httpSession = new HttpSession(authInfo, registry, subscriptions, this, advertiser);
		this.outOfBandMessageCallback = outOfBandMessageCallback;
		this.subscriptions = subscriptions;
	}

	@Override
	public synchronized HttpResponse handleRequest(HttpRequest request) throws IOException {
		return doHandleRequest(request);
	}
	
	private HttpResponse doHandleRequest(HttpRequest request) throws IOException {
		HttpResponse response = isUpgraded ? 
				httpSession.handleAuthenticatedRequest(request) : httpSession.handleRequest(request);
		if (response instanceof UpgradeResponse) {
			isUpgraded = true;
			readKey = ((UpgradeResponse) response).getReadKey().array();
			writeKey = ((UpgradeResponse) response).getWriteKey().array();
		}
		LOGGER.info(response.getStatusCode()+" "+request.getUri());
		return response;
	}
	
	@Override
	public byte[] decryptRequest(byte[] ciphertext) {
		if (!isUpgraded) {
			throw new RuntimeException("Cannot handle binary before connection is upgraded");
		}
		if (binaryProcessor == null) {
			binaryProcessor = new LengthPrefixedByteArrayProcessor();
		}
		Collection<byte[]> res = binaryProcessor.handle(ciphertext);
		if (res.isEmpty()) {
			return new byte[0];
		} else {
			try(ByteArrayOutputStream decrypted = new ByteArrayOutputStream()) {
				res.stream().map(msg -> decrypt(msg))
						.forEach(bytes -> {
							try {
								decrypted.write(bytes);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						});
				return decrypted.toByteArray();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	

	@Override
	public byte[] encryptResponse(byte[] response) throws IOException {
		int offset=0;
		try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			while(offset < response.length) {
				short length = (short) Math.min(response.length - offset, 0x400);
				byte[] lengthBytes = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
						.putShort(length).array();
				baos.write(lengthBytes);
				
				byte[] nonce = Pack.longToLittleEndian(outboundBinaryMessageCount++);
				byte[] plaintext;
				if (response.length == length) {
					plaintext = response;
				} else {
					plaintext = new byte[length];
					System.arraycopy(response, offset, plaintext, 0, length);
				}
				offset += length;
				baos.write(new ChachaEncoder(writeKey, nonce).encodeCiphertext(plaintext, lengthBytes));
			}
			return baos.toByteArray();
		}
	}
	
	private byte[] decrypt(byte[] msg) {
		byte[] mac = new byte[16];
		byte[] ciphertext = new byte[msg.length - 16];
		System.arraycopy(msg, 0, ciphertext, 0, msg.length - 16);
		System.arraycopy(msg, msg.length - 16, mac, 0, 16);
		byte[] additionalData = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN)
				.putShort((short) (msg.length - 16)).array();
		try {
			byte[] nonce = Pack.longToLittleEndian(inboundBinaryMessageCount++);
			return new ChachaDecoder(readKey, nonce)
				.decodeCiphertext(mac, additionalData, ciphertext);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() {
		subscriptions.removeConnection(this);
	}

	@Override
	public void outOfBand(HttpResponse message) {
		outOfBandMessageCallback.accept(message);
	}


}
