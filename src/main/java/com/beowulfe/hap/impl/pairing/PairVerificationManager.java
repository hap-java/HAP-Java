package com.beowulfe.hap.impl.pairing;

import java.security.SecureRandom;

import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beowulfe.hap.HomekitAuthInfo;
import com.beowulfe.hap.impl.HomekitRegistry;
import com.beowulfe.hap.impl.crypto.ChachaDecoder;
import com.beowulfe.hap.impl.crypto.ChachaEncoder;
import com.beowulfe.hap.impl.crypto.EdsaSigner;
import com.beowulfe.hap.impl.crypto.EdsaVerifier;
import com.beowulfe.hap.impl.http.HttpRequest;
import com.beowulfe.hap.impl.http.HttpResponse;
import com.beowulfe.hap.impl.pairing.PairVerificationRequest.Stage1Request;
import com.beowulfe.hap.impl.pairing.PairVerificationRequest.Stage2Request;
import com.beowulfe.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;
import com.beowulfe.hap.impl.pairing.TypeLengthValueUtils.Encoder;
import com.beowulfe.hap.impl.responses.NotFoundResponse;
import com.beowulfe.hap.impl.responses.OkResponse;

import djb.Curve25519;

public class PairVerificationManager {
	
	private final static Logger logger = LoggerFactory.getLogger(PairVerificationManager.class);
	private static SecureRandom secureRandom;

	private final HomekitAuthInfo authInfo;
	private final HomekitRegistry registry;
	
	private byte[] hkdfKey;
	private byte[] clientPublicKey;
	private byte[] publicKey;
	private byte[] sharedSecret;
	
	public PairVerificationManager(HomekitAuthInfo authInfo, HomekitRegistry registry) {
		this.authInfo = authInfo;
		this.registry = registry;
	}

	public HttpResponse handle(HttpRequest rawRequest) throws Exception {
		PairVerificationRequest request = PairVerificationRequest.of(rawRequest.getBody());
		switch(request.getStage()) {
		case ONE:
			return stage1((Stage1Request) request);
			
		case TWO:
			return stage2((Stage2Request) request);
			
		default:
			return new NotFoundResponse();
		}
	}

	private HttpResponse stage1(Stage1Request request) throws Exception {
		logger.debug("Starting pair verification for "+registry.getLabel());
		clientPublicKey = request.getClientPublicKey();
		publicKey = new byte[32];
		byte[] privateKey = new byte[32];
		getSecureRandom().nextBytes(privateKey);
		Curve25519.keygen(publicKey, null, privateKey);
		
		sharedSecret = new byte[32];
		Curve25519.curve(sharedSecret, privateKey, clientPublicKey);
		
		byte[] material = ByteUtils.joinBytes(publicKey, authInfo.getMac().getBytes(),
				clientPublicKey);
		
		byte[] proof = new EdsaSigner(authInfo.getPrivateKey()).sign(material);
		
		HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
		hkdf.init(new HKDFParameters(sharedSecret, "Pair-Verify-Encrypt-Salt".getBytes(),
				"Pair-Verify-Encrypt-Info".getBytes()));
		hkdfKey = new byte[32];
		hkdf.generateBytes(hkdfKey, 0, 32);
		
		Encoder encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.USERNAME, authInfo.getMac().getBytes());
		encoder.add(MessageType.SIGNATURE, proof);
		byte[] plaintext = encoder.toByteArray();
		
		ChachaEncoder chacha = new ChachaEncoder(hkdfKey, "PV-Msg02".getBytes());
		byte[] ciphertext = chacha.encodeCiphertext(plaintext);
		
		encoder = TypeLengthValueUtils.getEncoder();
		encoder.add(MessageType.STATE, (short) 2);
		encoder.add(MessageType.ENCRYPTED_DATA, ciphertext);
		encoder.add(MessageType.PUBLIC_KEY, publicKey);
		return new PairingResponse(encoder.toByteArray());
	}
	
	private HttpResponse stage2(Stage2Request request) throws Exception {
		ChachaDecoder chacha = new ChachaDecoder(hkdfKey, "PV-Msg03".getBytes());
		byte[] plaintext = chacha.decodeCiphertext(request.getAuthTagData(), request.getMessageData());
		
		DecodeResult d = TypeLengthValueUtils.decode(plaintext);
		byte[] clientUsername = d.getBytes(MessageType.USERNAME);
		byte[] clientSignature = d.getBytes(MessageType.SIGNATURE);
		
		byte[] material = ByteUtils.joinBytes(clientPublicKey, clientUsername, publicKey);
		
		byte[] clientLtpk = authInfo.getUserPublicKey(authInfo.getMac()+new String(clientUsername));
		if (clientLtpk == null) {
			throw new Exception("Unknown user: "+new String(clientUsername));
		}
		
		Encoder encoder = TypeLengthValueUtils.getEncoder();
		if (new EdsaVerifier(clientLtpk).verify(material, clientSignature)) {
			encoder.add(MessageType.STATE, (short) 4);
			logger.debug("Completed pair verification for "+registry.getLabel());
			return new UpgradeResponse(encoder.toByteArray(), createKey("Control-Write-Encryption-Key"),
					createKey("Control-Read-Encryption-Key"));
		} else {
			encoder.add(MessageType.ERROR, (short) 4);
			logger.warn("Invalid signature. Could not pair "+registry.getLabel());
			return new OkResponse(encoder.toByteArray());
		}
	}
	
	private byte[] createKey(String info) {
		HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new SHA512Digest());
		hkdf.init(new HKDFParameters(sharedSecret, "Control-Salt".getBytes(),
				info.getBytes()));
		byte[] key = new byte[32];
		hkdf.generateBytes(key, 0, 32);
		return key;
	}
	
	private static SecureRandom getSecureRandom() {
		if (secureRandom == null) {
			synchronized(PairVerificationManager.class) {
				if (secureRandom == null) {
					secureRandom = new SecureRandom();
				}
			}
		}
		return secureRandom;
	}

}
