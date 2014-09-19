/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceAmbientLight;

import ch.quantasy.messagebus.message.DefaultEvent;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceService;
import com.tinkerforge.BrickletAmbientLight;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeAmbientLightService extends TinkerforgeDeviceService<BrickletAmbientLight, TinkerforgeAmbientLightSetting, TinkerforgeAmbientLightIntent, TinkerforgeAmbientLightEvent> implements BrickletAmbientLight.AnalogValueListener, BrickletAmbientLight.AnalogValueReachedListener, BrickletAmbientLight.IlluminanceListener, BrickletAmbientLight.IlluminanceReachedListener {

    public TinkerforgeAmbientLightService(BrickletAmbientLight device, String deviceID) {
	super(device, deviceID);
    }

    @Override
    protected void updateListeners() {
	device.addAnalogValueListener(this);
	device.addAnalogValueReachedListener(this);
	device.addIlluminanceListener(this);
	device.addIlluminanceReachedListener(this);
    }

    @Override
    protected TinkerforgeAmbientLightSetting updateCurrentSetting(TinkerforgeAmbientLightSetting newSetting) {
	if (newSetting == null) {
	    return null;
	}
	if (currentSetting == null) {
	    currentSetting = newSetting.clone();
	    return newSetting;
	}
	TinkerforgeAmbientLightSetting delta = new TinkerforgeAmbientLightSetting();

	if (newSetting.getAnalogThreshold() != null && !newSetting.getAnalogThreshold().equals(this.currentSetting.getAnalogThreshold())) {
	    this.currentSetting.setAnalogThreshold(newSetting.getAnalogThreshold());
	    delta.setAnalogThreshold(newSetting.getAnalogThreshold());
	}
	if (newSetting.getAnalogValueCallbackPeriod() != null && !newSetting.getAnalogValueCallbackPeriod().equals(currentSetting.getAnalogValueCallbackPeriod())) {
	    this.currentSetting.setAnalogValueCallbackPeriod(newSetting.getAnalogValueCallbackPeriod());
	    delta.setAnalogValueCallbackPeriod(newSetting.getAnalogValueCallbackPeriod());
	}
	if (newSetting.getDebouncePeriod() != null && !newSetting.getDebouncePeriod().equals(currentSetting.getDebouncePeriod())) {
	    this.currentSetting.setDebouncePeriod(newSetting.getDebouncePeriod());
	    delta.setDebouncePeriod(this.currentSetting.getDebouncePeriod());
	}
	if (newSetting.getIlluminanceThreshold() != null && !newSetting.getIlluminanceThreshold().equals(currentSetting.getIlluminanceThreshold())) {
	    this.currentSetting.setIlluminanceThreshold(newSetting.getIlluminanceThreshold());
	    delta.setIlluminanceThreshold(this.currentSetting.getIlluminanceThreshold());
	}
	if (newSetting.getIlluminanceValueCallbackPeriod() != null && !newSetting.getIlluminanceValueCallbackPeriod().equals(currentSetting.getIlluminanceValueCallbackPeriod())) {
	    this.currentSetting.setIlluminanceValueCallbackPeriod(newSetting.getIlluminanceValueCallbackPeriod());
	    delta.setIlluminanceValueCallbackPeriod(newSetting.getIlluminanceValueCallbackPeriod());
	}
	return delta;

    }

    @Override
    protected void updateDeviceSetting(TinkerforgeAmbientLightSetting setting) {
	if (device == null) {
	    return;
	}
	if (setting == null) {
	    return;
	}

	try {

	    if (setting.getAnalogValueCallbackPeriod() != null) {
		device.setAnalogValueCallbackPeriod(setting.getAnalogValueCallbackPeriod());
	    }

	    if (setting.getIlluminanceValueCallbackPeriod() != null) {

		device.setIlluminanceCallbackPeriod(setting.getIlluminanceValueCallbackPeriod());
	    }

	    if (setting.getIlluminanceThreshold() != null) {
		device.setIlluminanceCallbackThreshold(setting.getIlluminanceThreshold().option, setting.getIlluminanceThreshold().min, setting.getIlluminanceThreshold().max);

	    }
	    if (setting.getAnalogThreshold() != null) {
		device.setAnalogValueCallbackThreshold(setting.getAnalogThreshold().option, setting.getAnalogThreshold().min, setting.getAnalogThreshold().max);

	    }

	    if (setting.getDebouncePeriod() != null) {
		device.setDebouncePeriod(setting.getDebouncePeriod());
	    }
	} catch (Exception ex) {
	    Logger.getLogger(TinkerforgeAmbientLightService.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    protected void handleTinkerMessage(TinkerforgeAmbientLightIntent message) {
	if (message == null) {
	    return;
	}
	TinkerforgeAmbientLightSetting delta = updateCurrentSetting(message.getDeviceSetting());
	updateDeviceSetting(delta);
	if (message.isRequestCurrentSetting() != null && message.isRequestCurrentSetting()) {
	    TinkerforgeAmbientLightEvent event = createEvent();
	    event.setDeviceSetting(currentSetting.clone());
	}
    }

    @Override
    public void analogValue(int value) {
	TinkerforgeAmbientLightEvent event = createEvent();
	event.setAnalogValue(value);
	publish(event);
    }

    @Override
    public void analogValueReached(int value) {
	TinkerforgeAmbientLightEvent event = createEvent();
	event.setAnalogValueReached(true);
	event.setAnalogValue(value);
	publish(event);
    }

    @Override
    public void illuminance(int illuminance) {
	TinkerforgeAmbientLightEvent event = createEvent();
	event.setIlluminanceValue(illuminance);
	publish(event);
    }

    @Override
    public void illuminanceReached(int illuminance) {
	TinkerforgeAmbientLightEvent event = createEvent();
	event.setIlluminanceReached(true);
	event.setIlluminanceValue(illuminance);
	publish(event);
    }

    @Override
    public TinkerforgeAmbientLightEvent createEvent() {
	return new TinkerforgeAmbientLightEvent(this);
    }

    public static TinkerforgeAmbientLightIntent createIntent(Agent agent) {
	return new TinkerforgeAmbientLightIntent(agent);
    }

    public static TinkerforgeAmbientLightEvent getTinkerforgeAmbientLightEvent(DefaultEvent event) {
	if (event instanceof TinkerforgeAmbientLightEvent) {
	    return (TinkerforgeAmbientLightEvent) event;
	}
	return null;
    }

}
