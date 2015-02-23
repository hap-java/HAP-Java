package com.beowulfe.hap.impl.pairing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.nimbusds.srp6.ClientEvidenceRoutine;
import com.nimbusds.srp6.SRP6ClientEvidenceContext;
import com.nimbusds.srp6.SRP6CryptoParams;

class ClientEvidenceRoutineImpl implements ClientEvidenceRoutine {

	public ClientEvidenceRoutineImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates M1 according to the following formula:
	 * 
	 * M1 = H(H(N) xor H(g) || H(username) || s || A || B || H(S))
	 */
	@Override
	public BigInteger computeClientEvidence(SRP6CryptoParams cryptoParams,
			SRP6ClientEvidenceContext ctx) {
		
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(cryptoParams.H);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Could not locate requested algorithm", e);
		}
		digest.update(SrpHandler.bigIntegerToUnsignedByteArray(cryptoParams.N));
		byte[] hN = digest.digest();
		
		digest.update(SrpHandler.bigIntegerToUnsignedByteArray(cryptoParams.g));
		byte[] hg = digest.digest();
		
		byte[] hNhg = xor(hN, hg);
		
		digest.update(ctx.userID.getBytes());
		byte[] hu = digest.digest();
		
		digest.update(SrpHandler.bigIntegerToUnsignedByteArray(ctx.S));
		byte[] hS = digest.digest();
		
		digest.update(hNhg);
		digest.update(hu);
		digest.update(SrpHandler.bigIntegerToUnsignedByteArray(ctx.s));
		digest.update(SrpHandler.bigIntegerToUnsignedByteArray(ctx.A));
		digest.update(SrpHandler.bigIntegerToUnsignedByteArray(ctx.B));
		digest.update(hS);
		BigInteger ret = new BigInteger(1, digest.digest());
		return ret;
	}
	
	private static byte[] xor(byte[] b1, byte[] b2) {
		byte[] result = new byte[b1.length];
		for (int i=0; i<b1.length; i++) {
			result[i] = (byte) (b1[i] ^ b2[i]);
		}
		return result;
	}

}
