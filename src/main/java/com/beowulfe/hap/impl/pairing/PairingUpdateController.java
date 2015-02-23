package com.beowulfe.hap.impl.pairing;

import java.io.IOException;

import com.beowulfe.hap.HomekitAuthInfo;
import com.beowulfe.hap.impl.http.HttpRequest;
import com.beowulfe.hap.impl.http.HttpResponse;
import com.beowulfe.hap.impl.pairing.TypeLengthValueUtils.DecodeResult;

public class PairingUpdateController {

	private final HomekitAuthInfo authInfo;
	
	public PairingUpdateController(HomekitAuthInfo authInfo) {
		this.authInfo = authInfo;
	}

	public HttpResponse handle(HttpRequest request) throws IOException {
		DecodeResult d = TypeLengthValueUtils.decode(request.getBody());
		
		int method = d.getByte(MessageType.METHOD);
		if (method == 3) { //Add pairing
			byte[] username = d.getBytes(MessageType.USERNAME);
			byte[] ltpk = d.getBytes(MessageType.PUBLIC_KEY);
			authInfo.createUser(authInfo.getMac()+new String(username), ltpk);
		} else if (method == 4) { //Remove pairing
			byte[] username = d.getBytes(MessageType.USERNAME);
			authInfo.removeUser(authInfo.getMac()+new String(username));
		} else {
			throw new RuntimeException("Unrecognized method: "+method);
		}
		return new PairingResponse(new byte[] {0x06, 0x01, 0x02});
	}

}
