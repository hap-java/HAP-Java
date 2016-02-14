package com.beowulfe.hap.impl.connections;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.LinkedList;

class LengthPrefixedByteArrayProcessor {

	private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	private Byte firstLengthByteBuffer; //Only used if we've received a single byte at the start of a message
	private int targetLength = 0;

	public synchronized Collection<byte[]> handle(byte[] data) {
		Collection<byte[]> results = new LinkedList<>();
		int pos = 0;
		if (buffer.size() == 0) {
			while(data.length - pos > 18) {
				int targetLength = (data[0] & 0xFF) + (data[1] & 0xFF) * 256 + 16 + 2;
				if (data.length >= pos + targetLength) {
					byte[] b = new byte[targetLength - 2];
					System.arraycopy(data, pos+2, b, 0, targetLength-2);
					results.add(b);
					pos = pos + targetLength;
				} else {
					break;
				}
			}
		}
		if (data.length > pos) {
			step(data, pos, results);
		}
		return results;
	}
	
	private void step(byte[] data, int pos, Collection<byte[]> results) {
		if (buffer.size() == 0 && data.length == 1 + pos) {
			firstLengthByteBuffer = data[pos];
			return;
		}
		if (buffer.size() == 0) {
			if (firstLengthByteBuffer != null) {
				targetLength = firstLengthByteBuffer + data[pos] * 256 + 16;
				pos += 1;
			} else {
				targetLength = data[pos] + data[pos+1] * 256 + 16;
				pos += 2;
			}
		}
		int toWrite = targetLength - buffer.size();
		if (toWrite > data.length - pos) {
			//We have a complete message
			buffer.write(data, pos, toWrite);
			results.add(buffer.toByteArray());
			buffer.reset();
			step(data, pos + toWrite, results);
		} else {
			buffer.write(data, pos, data.length - pos);
		}
	}

}
