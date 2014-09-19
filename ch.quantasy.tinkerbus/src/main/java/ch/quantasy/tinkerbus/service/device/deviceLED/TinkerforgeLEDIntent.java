/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceLED;

import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceIntent;
import java.util.Arrays;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeLEDIntent extends TinkerforgeDeviceIntent<TinkerforgeLEDSetting> {

    private Boolean requestFrame;
    private short[][] frame;

    public TinkerforgeLEDIntent(Agent intentSender) {
	super(intentSender);
    }

    public TinkerforgeLEDIntent(Agent intentSender, String... intentReceivers) {
	super(intentSender, intentReceivers);
    }

    public Boolean isRequestFrame() {
	return requestFrame != null && requestFrame;
    }

    public void setRequestFrame(Boolean requestFrame) {
	this.requestFrame = requestFrame;
    }

    @Override
    protected TinkerforgeLEDSetting createDeviceSetting() {
	return new TinkerforgeLEDSetting();
    }

    public void setFrame(short[][] frame) {
	this.frame = frame;
    }

    public short[][] getFrame() {
	return frame;
    }

    @Override
    public String toString() {
	return super.toString() + " TinkerforgeLEDIntent{" + "requestFrame=" + requestFrame + ", frame=" + Arrays.toString(frame) + '}';
    }

}
