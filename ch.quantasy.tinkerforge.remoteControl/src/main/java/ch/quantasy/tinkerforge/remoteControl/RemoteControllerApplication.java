package ch.quantasy.tinkerforge.remoteControl;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import com.tinkerforge.Device;

public class RemoteControllerApplication extends AbstractTinkerforgeApplication {

	private MotorApplication motorApplication;
	private JoystickApplication joystickApplication;

	public RemoteControllerApplication() {
		this.motorApplication = new MotorApplication();
		this.joystickApplication = new JoystickApplication(this);
		super.addTinkerforgeApplication(motorApplication);
		super.addTinkerforgeApplication(joystickApplication);
	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
	}

	private int latestx;
	private int latesty;

	public void setVelocity(int x, int y) {
		if (this.latestx != x) {
			this.latestx = x;
			this.motorApplication.setVelocityDC1(x * 250);
		}
		if (this.latesty != y) {
			this.latesty = y;
			this.motorApplication.setVelocityDC2(y * 250);
		}
	}
	
	public void fullStop(){
		this.latestx=0;
		this.latesty=0;
		this.motorApplication.fullStop();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

}
