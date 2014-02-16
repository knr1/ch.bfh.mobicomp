package ch.bfh.fbi.mobiComp.tinkerforge.test;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRemoteSwitch.SwitchingDoneListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

public class ConcurrentRemoteSwitchApplication extends AbstractTinkerforgeApplication
		implements SwitchingDoneListener {
	private BrickletRemoteSwitch remoteSwitch;

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.RemoteSwitch && device.equals(remoteSwitch)) {
			((BrickletRemoteSwitch) device).removeSwitchingDoneListener(this);
			remoteSwitch=null;

		}

	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.RemoteSwitch) {
			if(remoteSwitch!=null) // It is programmed for exactly one Remote Switch
				return;
			remoteSwitch = (BrickletRemoteSwitch) device;
			remoteSwitch.addSwitchingDoneListener(this);
			remoteSwitch.setResponseExpected(
					BrickletRemoteSwitch.FUNCTION_SWITCH_SOCKET, true);
		}

	}

	public void setRemoteSwitchSocketA(short houseCode, short receiverCode,
			short switchingRequest) {
		try {
			if (remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			remoteSwitch.switchSocketA(houseCode, (short) receiverCode,
					switchingRequest);

		} catch (TimeoutException e) {
			System.out.println("Timed Out");
		} catch (NotConnectedException e) {
			System.out.println("Not connected");
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	public void setRemoteSwitchSocketB(long address, short unit,
			short switchingRequest) {
		try {
			if (remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			remoteSwitch.switchSocketB(address, unit, switchingRequest);

		} catch (TimeoutException e) {
			System.out.println("Timed Out");
		} catch (NotConnectedException e) {
			System.out.println("Not connected");
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	public void setRemoteSwitchSocketC(char systemCode, short deviceCode,
			short switchingRequest) {
		try {
			if (remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			remoteSwitch
					.switchSocketC(systemCode, deviceCode, switchingRequest);

		} catch (TimeoutException e) {
			System.out.println("Timed Out");
		} catch (NotConnectedException e) {
			System.out.println("Not connected");
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	public void setRemoteSwitchSocketBDim(long address, short unit, short dimValue) {
		try {
			if (remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			remoteSwitch.dimSocketB(address, unit, dimValue);
		} catch (TimeoutException e) {
			System.out.println("Timed Out");
		} catch (NotConnectedException e) {
			System.out.println("Not connected");
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	@Override
	public void switchingDone() {
		System.out.println("Switched");
		synchronized (ConcurrentRemoteSwitchApplication.this) {
			this.notifyAll();
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
	

}
