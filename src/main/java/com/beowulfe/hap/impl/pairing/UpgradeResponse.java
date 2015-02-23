package com.beowulfe.hap.impl.pairing;

public class UpgradeResponse extends PairingResponse {

	private final byte[] readKey;
	private final byte[] writeKey;
	
	UpgradeResponse(byte[] body, byte[] readKey, byte[] writeKey) {
		super(body);
		this.readKey = readKey;
		this.writeKey = writeKey;
	}

	@Override
	public boolean doUpgrade() {
		return true;
	}
	
	public byte[] getReadKey() {
		return readKey;
	}
	
	public byte[] getWriteKey() {
		return writeKey;
	}

}
