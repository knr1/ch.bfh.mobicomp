package ch.quantasy.tinkerforge.led;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.BrickletRotaryEncoder.CountListener;
import com.tinkerforge.BrickletRotaryEncoder.PressedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class RotaryApplication extends AbstractTinkerforgeApplication implements CountListener, PressedListener{
	private BrickletRotaryEncoder rotaryEncoder;
	private BlinkingLEDs blinkingLEDs;
	public RotaryApplication(BlinkingLEDs blinkingLEDs){
		this.blinkingLEDs=blinkingLEDs;
	}
	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		
		if(TinkerforgeDevice.getDevice(device)==TinkerforgeDevice.RotaryEncoder){
			if(rotaryEncoder==null)
			{
				rotaryEncoder=(BrickletRotaryEncoder)device;
				rotaryEncoder.addCountListener(this);
				rotaryEncoder.addPressedListener(this);
				try {
					rotaryEncoder.setCountCallbackPeriod(100);
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if(TinkerforgeDevice.areEqual(rotaryEncoder, device)){
				rotaryEncoder.removeCountListener(this);
				rotaryEncoder.removePressedListener(this);
				rotaryEncoder=null;
			}
		
	}

	@Override
	public boolean equals(Object obj) {
		return this==obj;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void pressed() {
		blinkingLEDs.flash();
		
	}

	private int previousCount;
	@Override
	public void count(int count) {
		blinkingLEDs.changeSpeed(previousCount-count);
		this.previousCount=count;
	}

}
