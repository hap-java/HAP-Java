package com.beowulfe.hap.impl.pairing;

import java.math.BigInteger;

import com.beowulfe.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;

abstract class PairSetupRequest {
	
	private final static short VALUE_STAGE_1 = 1;
	private final static short VALUE_STAGE_2 = 3;
	private final static short VALUE_STAGE_3 = 5;
	
	public static PairSetupRequest of(byte[] content) throws Exception {
		DecodeResult d = TypeLengthValueUtils.decode(content);
		short stage = d.getByte(MessageType.STATE);
		switch(stage) {
		case VALUE_STAGE_1:
			return new Stage1Request();
			
		case VALUE_STAGE_2:
			return new Stage2Request(d);
			
		case VALUE_STAGE_3:
			return new Stage3Request(d);
			
		default:
			throw new Exception("Unknown pair process stage: "+stage);
		}
	}
	
	public abstract Stage getStage();
	
	public static class Stage1Request extends PairSetupRequest {
		@Override
		public Stage getStage() {
			return Stage.ONE;
		}
	}
	
	public static class Stage2Request extends PairSetupRequest {
		
		private final BigInteger a;
		private final BigInteger m1;
		
		public Stage2Request(DecodeResult d) {
			a = d.getBigInt(MessageType.PUBLIC_KEY);
			m1 = d.getBigInt(MessageType.PROOF);
		}

		public BigInteger getA() {
			return a;
		}

		public BigInteger getM1() {
			return m1;
		}

		@Override
		public Stage getStage() {
			return Stage.TWO;
		}
		
	}
	
	public static class Stage3Request extends PairSetupRequest {
	
		private final byte[] messageData;
		private final byte[] authTagData;
	
		public Stage3Request(DecodeResult d) {
			messageData = new byte[d.getLength(MessageType.ENCRYPTED_DATA) - 16];
			authTagData = new byte[16];
			d.getBytes(MessageType.ENCRYPTED_DATA, messageData, 0);
			d.getBytes(MessageType.ENCRYPTED_DATA, authTagData, messageData.length);
		}

		public byte[] getMessageData() {
			return messageData;
		}

		public byte[] getAuthTagData() {
			return authTagData;
		}

		@Override
		public Stage getStage() {
			return Stage.THREE;
		}
		
	}

}
