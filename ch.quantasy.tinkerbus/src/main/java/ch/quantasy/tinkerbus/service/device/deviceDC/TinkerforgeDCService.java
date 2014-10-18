/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceDC;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.worker.definition.Agent;
import ch.quantasy.messagebus.worker.definition.Service;
import ch.quantasy.tinkerbus.service.device.content.TinkerforgeDeviceContent;
import ch.quantasy.tinkerbus.service.device.core.ATinkerforgeDeviceService;
import ch.quantasy.tinkerbus.service.device.deviceDC.content.DCEnabledContent;
import ch.quantasy.tinkerbus.service.device.deviceDC.message.TinkerforgeDCEvent;
import ch.quantasy.tinkerbus.service.device.deviceDC.message.TinkerforgeDCIntent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceEvent;
import ch.quantasy.tinkerbus.service.device.message.ATinkerforgeDeviceIntent;
import com.tinkerforge.BrickDC;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDCService extends ATinkerforgeDeviceService<BrickDC, TinkerforgeDCIntent, TinkerforgeDCEvent> implements BrickDC.CurrentVelocityListener, BrickDC.EmergencyShutdownListener, BrickDC.UnderVoltageListener, BrickDC.VelocityReachedListener {

    public TinkerforgeDCService(BrickDC device, String deviceID) {
	super(device, deviceID);
    }

    @Override
    protected void updateListeners(BrickDC device) {
	device.addCurrentVelocityListener(this);
	device.addEmergencyShutdownListener(this);
	device.addUnderVoltageListener(this);
	device.addVelocityReachedListener(this);
    }

    /* @Override
     protected TinkerforgeDCSetting updateCurrentSetting(TinkerforgeDCSetting newSetting) {
     if (newSetting == null) {
     return null;
     }
     TinkerforgeDCSetting delta = new TinkerforgeDCSetting();
     if (newSetting.getAcceleration() != null && !newSetting.getAcceleration().equals(this.currentSetting.getAcceleration())) {
     this.currentSetting.setAcceleration(newSetting.getAcceleration());
     delta.setAcceleration(newSetting.getAcceleration());
     }
     if (newSetting.getCurrentVelocityPeriod() != null && !newSetting.getCurrentVelocityPeriod().equals(this.currentSetting.getCurrentVelocityPeriod())) {
     this.currentSetting.setCurrentVelocityPeriod(newSetting.getCurrentVelocityPeriod());
     delta.setCurrentVelocityPeriod(newSetting.getCurrentVelocityPeriod());
     }
     if (newSetting.getDriveMode() != null && !newSetting.getDriveMode().equals(this.currentSetting.getDriveMode())) {
     this.currentSetting.setDriveMode(newSetting.getDriveMode());
     delta.setDriveMode(newSetting.getDriveMode());
     }
     if (newSetting.getMinimumVoltage() != null && !newSetting.getMinimumVoltage().equals(this.currentSetting.getMinimumVoltage())) {
     this.currentSetting.setMinimumVoltage(newSetting.getMinimumVoltage());
     delta.setMinimumVoltage(newSetting.getMinimumVoltage());
     }
     if (newSetting.getPWMFrequency() != null && !newSetting.getPWMFrequency().equals(this.currentSetting.getPWMFrequency())) {
     this.currentSetting.setPWMFrequency(newSetting.getPWMFrequency());
     delta.setPWMFrequency(newSetting.getPWMFrequency());
     }
     if (newSetting.getVelocity() != null && !newSetting.getVelocity().equals(this.currentSetting.getVelocity())) {
     this.currentSetting.setVelocity(newSetting.getVelocity());
     delta.setVelocity(newSetting.getVelocity());
     }
     if (newSetting.isFullBrake() != null && !newSetting.isFullBrake().equals(this.currentSetting.isFullBrake())) {
     this.currentSetting.setFullBrake(newSetting.isFullBrake());
     delta.setFullBrake(newSetting.isFullBrake());
     }
     if (newSetting.isEnabled() != null && !newSetting.isEnabled().equals(this.currentSetting.isEnabled())) {
     this.currentSetting.setEnabled(newSetting.isEnabled());
     delta.setEnabled(newSetting.isEnabled());
     }
     return delta;
     }

     @Override
     protected void updateDeviceSetting(TinkerforgeDCSetting setting) {
     if (device == null) {
     return;
     }
     if (setting == null) {
     return;
     }

     try {

     if (setting.getAcceleration() != null) {
     device.setAcceleration(setting.getAcceleration());
     }
     if (setting.getCurrentVelocityPeriod() != null) {
     device.setCurrentVelocityPeriod(setting.getCurrentVelocityPeriod());
     }
     if (setting.getDriveMode() != null) {
     device.setDriveMode(setting.getDriveMode());
     }
     if (setting.getMinimumVoltage() != null) {
     device.setMinimumVoltage(setting.getMinimumVoltage());
     }
     if (setting.getPWMFrequency() != null) {
     device.setPWMFrequency(setting.getMinimumVoltage());
     }
     if (setting.getVelocity() != null) {
     device.setVelocity(setting.getVelocity());
     }
     if (setting.isEnabled() != null) {
     if (setting.isEnabled()) {
     device.enable();
     } else {
     device.disable();
     }
     }
     if (setting.isFullBrake() != null) {
     if (setting.isFullBrake()) {
     device.fullBrake();
     }
     }
     } catch (Exception ex) {
     Logger.getLogger(TinkerforgeAmbientLightService.class.getName()).log(Level.SEVERE, null, ex);
     }
     }

     @Override
     protected void handleTinkerMessage(TinkerforgeDCIntent message) {
     TinkerforgeDCSetting delta = updateCurrentSetting(message.getDeviceSetting());
     updateDeviceSetting(delta);
     if (message.isRequestCurrentSetting()) {
     TinkerforgeDCEvent event = createEvent();
     event.setDeviceSetting(currentSetting.clone());
     }
     }

     @Override
     public void currentVelocity(short velocity) {
     TinkerforgeDCEvent event = createEvent();
     event.setVelocity(velocity);
     }

     @Override
     public void emergencyShutdown() {
     TinkerforgeDCEvent event = createEvent();
     event.setEmergencyShutdown(true);
     publish(event);
     }

     @Override
     public void underVoltage(int voltage) {
     TinkerforgeDCEvent event = createEvent();
     event.setUnderVoltage(true);
     event.setVoltage(voltage);
     publish(event);
     }

     @Override
     public void velocityReached(short velocity) {
     TinkerforgeDCEvent event = createEvent();
     event.setVelocityReached(true);
     event.setVelocity(velocity);
     publish(event);
     }

     @Override
     public void publish(TinkerforgeDCEvent message) {
     try {
     message.setDeviceSetting(currentSettings.clone());
     message.setAcceleration(device.getAcceleration());
     message.setChipTemperature(device.getChipTemperature());
     message.setCurrentConsumption(device.getCurrentConsumption());
     message.setPWMFrequency(device.getPWMFrequency());
     message.setMinimumVoltage(device.getMinimumVoltage());
     message.setExternalInputVoltage(device.getExternalInputVoltage());
     } catch (Throwable t) {
     //
     }
     super.publish(message); //To change body of generated methods, choose Tools | Templates.
     }

     public static void addIntentForEnable(DefaultIntent intent, boolean enable) {

     }

     public static void addIntentForFullBrake(DefaultIntent intent) {

     }
     */
    @Override
    public TinkerforgeDCEvent createEvent() {
	return new Event(getDeviceContent(), this);
    }

    public static TinkerforgeDCIntent createIntent(TinkerforgeDeviceContent content, Agent agent) {
	return new Intent(content, agent);
    }

    @Override
    protected void updateSettingsOnDevice(BrickDC device, Map<Class, Content> deviceContent) {
	try {
	    {
		DCEnabledContent content = ((DCEnabledContent) (deviceContent.get(DCEnabledContent.class)));
		if (content != null) {
		    Boolean value = content.getValue();
		    if (value != null) {
			if (value) {
			    device.enable();
			} else {
			    device.disable();
			}
		    }
		}
	    }
	} catch (TimeoutException ex) {
	    Logger.getLogger(TinkerforgeDCService.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NotConnectedException ex) {
	    Logger.getLogger(TinkerforgeDCService.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void handleTinkerforgeIntent(TinkerforgeDCIntent message) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void currentVelocity(short velocity) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void emergencyShutdown() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void underVoltage(int voltage) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void velocityReached(short velocity) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

class Intent extends ATinkerforgeDeviceIntent implements TinkerforgeDCIntent {

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender) {
	super(deviceContent, intentSender);
    }

    public Intent(TinkerforgeDeviceContent deviceContent, Agent intentSender, String... intentReceivers) {
	super(deviceContent, intentSender, intentReceivers);
    }

}

class Event extends ATinkerforgeDeviceEvent implements TinkerforgeDCEvent {

    public Event(TinkerforgeDeviceContent deviceContent, Service eventSender) {
	super(deviceContent, eventSender);
    }

}
