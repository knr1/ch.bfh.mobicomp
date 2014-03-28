package ch.quantasy.remoteControl;

import com.tinkerforge.BrickDC;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

public class MotorApplication extends AbstractTinkerforgeApplication{
	private BrickDC dc1;
	private BrickDC dc2;
	
	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if(TinkerforgeDevice.getDevice(device)==TinkerforgeDevice.DC)
			if(dc1==null)
			{
				dc1=(BrickDC)device;
				try {
					dc1.enable();
				} catch (TimeoutException | NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(dc2==null){
				dc2=(BrickDC)device;
				try {
					dc2.enable();
				} catch (TimeoutException | NotConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}
	
	

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if(TinkerforgeDevice.areEqual(dc1, device))
			dc1=null;
		if(TinkerforgeDevice.areEqual(dc2, device))
			dc2=null;
		
	}
	
	public void fullStop(){
		try {
			dc1.fullBrake();
			dc2.fullBrake();
		} catch (TimeoutException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setVelocityDC1(int velocity){
		if(dc1==null)return;
		try {
			dc1.setVelocity((short)velocity);
		} catch (TimeoutException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setVelocityDC2(int velocity){
		if(dc2==null)return;
		try {
			dc2.setVelocity((short)velocity);
		} catch (TimeoutException | NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
