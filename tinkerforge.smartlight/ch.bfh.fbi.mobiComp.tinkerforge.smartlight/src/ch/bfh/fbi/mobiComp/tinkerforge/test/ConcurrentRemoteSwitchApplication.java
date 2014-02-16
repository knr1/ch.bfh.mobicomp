package ch.bfh.fbi.mobiComp.tinkerforge.test;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;

import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRemoteSwitch.SwitchingDoneListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;

/**
 * This class allows to use the RemoteSwitch on a concurrent base. The command
 * for switching some remote Switches will block until the command has been sent
 * out. <br/>
 * It will manage the first {@link BrickletRemoteSwitch} that will be presented
 * to this {@link TinkerforgeApplication}. <br/>
 * Be careful to only instantiate one of these
 * {@link ConcurrentRemoteSwitchApplication}s per {@link BrickletRemoteSwitch}!
 * 
 * @author reto
 * 
 */
public class ConcurrentRemoteSwitchApplication extends
		AbstractTinkerforgeApplication implements SwitchingDoneListener {
	private BrickletRemoteSwitch remoteSwitch;

	@Override
	public void deviceDisconnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if ((TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.RemoteSwitch)
				&& device.equals(this.remoteSwitch)) {
			((BrickletRemoteSwitch) device).removeSwitchingDoneListener(this);
			this.remoteSwitch = null;

		}

	}

	@Override
	public void deviceConnected(
			final TinkerforgeStackAgent tinkerforgeStackAgent,
			final Device device) {
		if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.RemoteSwitch) {
			if (this.remoteSwitch != null) {
				return;
			}
			this.remoteSwitch = (BrickletRemoteSwitch) device;
			this.remoteSwitch.addSwitchingDoneListener(this);
			this.remoteSwitch.setResponseExpected(
					BrickletRemoteSwitch.FUNCTION_SWITCH_SOCKET, true);
		}

	}

	public void setRemoteSwitchSocketA(final short houseCode,
			final short receiverCode, final short switchingRequest) {
		try {
			if (this.remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (this.remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			this.remoteSwitch.switchSocketA(houseCode, receiverCode,
					switchingRequest);

		} catch (final TimeoutException e) {
			System.out.println("Timed Out");
		} catch (final NotConnectedException e) {
			System.out.println("Not connected");
		} catch (final InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	public void setRemoteSwitchSocketB(final long address, final short unit,
			final short switchingRequest) {
		try {
			if (this.remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (this.remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			this.remoteSwitch.switchSocketB(address, unit, switchingRequest);

		} catch (final TimeoutException e) {
			System.out.println("Timed Out");
		} catch (final NotConnectedException e) {
			System.out.println("Not connected");
		} catch (final InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	public void setRemoteSwitchSocketC(final char systemCode,
			final short deviceCode, final short switchingRequest) {
		try {
			if (this.remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (this.remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			this.remoteSwitch.switchSocketC(systemCode, deviceCode,
					switchingRequest);

		} catch (final TimeoutException e) {
			System.out.println("Timed Out");
		} catch (final NotConnectedException e) {
			System.out.println("Not connected");
		} catch (final InterruptedException e) {
			System.out.println("Interrupted");
		}

	}

	public void setRemoteSwitchSocketBDim(final long address, final short unit,
			final short dimValue) {
		try {
			if (this.remoteSwitch == null) {
				System.out.println("NO REMOTE SWITCH AVAILABLE");
				return;
			}
			synchronized (this) {
				while (this.remoteSwitch.getSwitchingState() == BrickletRemoteSwitch.SWITCHING_STATE_BUSY) {
					this.wait(1000);
				}
			}
			this.remoteSwitch.dimSocketB(address, unit, dimValue);
		} catch (final TimeoutException e) {
			System.out.println("Timed Out");
		} catch (final NotConnectedException e) {
			System.out.println("Not connected");
		} catch (final InterruptedException e) {
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
	public boolean equals(final Object obj) {
		return this == obj;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
