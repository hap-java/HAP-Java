package com.beowulfe.hap.impl.pairing;

import com.beowulfe.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;


abstract class PairVerificationRequest {
	
	private final static short VALUE_STAGE_1 = 1;
	private final static short VALUE_STAGE_2 = 3;

	static PairVerificationRequest of(byte[] content) throws Exception {
		DecodeResult d = TypeLengthValueUtils.decode(content);
		short stage = d.getByte(MessageType.STATE);
		switch(stage) {
		case VALUE_STAGE_1:
			return new Stage1Request(d);
			
		case VALUE_STAGE_2:
			return new Stage2Request(d);
			
		default:
			throw new Exception("Unknown pair process stage: "+stage);
		}
	}
	
	abstract Stage getStage();
	
	static class Stage1Request extends PairVerificationRequest {

		private final byte[] clientPublicKey;
		
		public Stage1Request(DecodeResult d) {
			clientPublicKey = d.getBytes(MessageType.PUBLIC_KEY);
		}

		public byte[] getClientPublicKey() {
			return clientPublicKey;
		}
		
		@Override
		Stage getStage() {
			return Stage.ONE;
		}
		
	}
	
	static class Stage2Request extends PairVerificationRequest {
		
		private final byte[] messageData;
		private final byte[] authTagData;
	
		public Stage2Request(DecodeResult d) {
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
			return Stage.TWO;
		}
		
	}

}
