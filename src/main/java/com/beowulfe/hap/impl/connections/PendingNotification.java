package com.beowulfe.hap.impl.connections;

import com.beowulfe.hap.characteristics.EventableCharacteristic;

public class PendingNotification {
  public int aid;
  public int iid;
  public EventableCharacteristic characteristic;

  public PendingNotification(int aid, int iid, EventableCharacteristic characteristic) {
    this.aid = aid;
    this.iid = iid;
    this.characteristic = characteristic;
  }
}
