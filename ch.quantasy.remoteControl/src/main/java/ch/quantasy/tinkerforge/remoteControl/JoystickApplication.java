package ch.quantasy.tinkerforge.remoteControl;

import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.BrickletJoystick.PositionListener;
import com.tinkerforge.BrickletJoystick.PressedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

public class JoystickApplication extends AbstractTinkerforgeApplication implements PositionListener,PressedListener{

	BrickletJoystick joystick;
	RemoteControllerApplication remoteControllerApplication;
	public JoystickApplication(RemoteControllerApplication remoteControllerApplication){
		this.remoteControllerApplication=remoteControllerApplication;
	}
	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if(TinkerforgeDevice.getDevice(device)==TinkerforgeDevice.Joystick)
		{
			if(joystick==null)
			{
				joystick=(BrickletJoystick)device;
				joystick.addPositionListener(this);
				joystick.addPressedListener(this);
				try {
					joystick.setPositionCallbackPeriod(50);
					joystick.setDebouncePeriod(20);
				} catch (TimeoutException | NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if(TinkerforgeDevice.areEqual(joystick, device))
		{
			joystick.removePositionListener(this);
			joystick=null;
		}
		
	}

	@Override
	public boolean equals(Object obj) {
		return this==obj;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public void position(short x, short y) {
		remoteControllerApplication.setVelocity(x,y);
		
	}
	@Override
	public void pressed() {
		remoteControllerApplication.fullStop();
		System.out.println("STOP");
		
	}
	
}
