/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceDC.message;

import ch.quantasy.tinkerbus.service.device.message.TinkerforgeDeviceIntent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public interface TinkerforgeDCIntent extends TinkerforgeDeviceIntent {
}
/*
 public TinkerforgeDCIntent(Agent intentSender) {
 super(intentSender);
 }

 public TinkerforgeDCIntent(Agent intentSender, String... intentReceivers) {
 super(intentSender, intentReceivers);
 }

 @Override
 public String toString() {
 return super.toString() + " TinkerforgeDCIntent{" + '}';
 }

 @Override
 protected TinkerforgeDCSetting createDeviceSetting() {
 return new TinkerforgeDCSetting();
 }

 }*/
