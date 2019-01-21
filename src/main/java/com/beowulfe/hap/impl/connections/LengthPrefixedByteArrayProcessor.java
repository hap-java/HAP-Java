package com.beowulfe.hap.impl.connections;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class LengthPrefixedByteArrayProcessor {

  private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  private Byte
      firstLengthByteBuffer; // Only used if we've received a single byte at the start of a message
  private int targetLength = 0;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(LengthPrefixedByteArrayProcessor.class);

  public synchronized Collection<byte[]> handle(byte[] data) {
    Collection<byte[]> results = new LinkedList<>();
    int pos = 0;
    LOGGER.trace(
        "Received message of length {}. Existing buffer is {}", data.length, buffer.size());
    if (buffer.size() == 0) {
      while (data.length - pos > 18) {
        int targetLength = (data[0] & 0xFF) + (data[1] & 0xFF) * 256 + 16 + 2;
        LOGGER.trace("Attempting to read message of length {}", targetLength);
        if (data.length >= pos + targetLength) {
          byte[] b = new byte[targetLength - 2];
          System.arraycopy(data, pos + 2, b, 0, targetLength - 2);
          results.add(b);
          LOGGER.trace("Read complete message");
          pos = pos + targetLength;
        } else {
          LOGGER.trace("Not enough data available");
          break;
        }
      }
    }
    if (data.length > pos) {
      LOGGER.trace("Remaining data available");
      step(data, pos, results);
    }
    LOGGER.trace("Returning {} results", results.size());
    return results;
  }

  private void step(byte[] data, int pos, Collection<byte[]> results) {
    LOGGER.trace("Performing step operation on buffer of length {} with pos {}", data.length, pos);
    if (targetLength == 0 && data.length == 1 + pos) {
      firstLengthByteBuffer = data[pos];
      LOGGER.trace(
          "Received a single byte message, storing byte {} for later", firstLengthByteBuffer);
      return;
    }
    if (targetLength == 0) {
      if (firstLengthByteBuffer != null) {
        targetLength = (firstLengthByteBuffer & 0xFF) + (data[pos] & 0xFF) * 256 + 16;
        pos += 1;
        LOGGER.trace(
            "Received the second byte after storing the first byte. New length is {}",
            targetLength);
      } else {
        targetLength = (data[pos] & 0xFF) + (data[pos + 1] & 0xFF) * 256 + 16;
        pos += 2;
        LOGGER.trace("targetLength is {}", targetLength);
      }
    }
    int toWrite = targetLength - buffer.size();
    if (toWrite <= data.length - pos) {
      // We have a complete message
      LOGGER.trace("Received a complete message");
      buffer.write(data, pos, toWrite);
      results.add(buffer.toByteArray());
      buffer.reset();
      targetLength = 0;
      if (pos + toWrite > data.length) {
        step(data, pos + toWrite, results);
      }
    } else {
      LOGGER.trace(
          "Storing {} bytes in buffer until we receive the complete {}",
          data.length - pos,
          targetLength);
      buffer.write(data, pos, data.length - pos);
    }
  }
}
