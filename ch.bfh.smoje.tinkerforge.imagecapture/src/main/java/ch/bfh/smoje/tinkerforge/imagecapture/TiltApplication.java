package ch.bfh.smoje.tinkerforge.imagecapture;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.BrickletTilt.TiltStateListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiltApplication extends AbstractTinkerforgeApplication implements TiltStateListener {

    private BrickletTilt tilt;
    private ImageCaptureManager manager;

    public TiltApplication(ImageCaptureManager manager) {
	this.manager = manager;
    }

    @Override
    public void deviceConnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.Tilt) {
	    if (this.tilt == null) {
		this.tilt = (BrickletTilt) device;
		this.tilt.addTiltStateListener(this);
		try {
		    this.tilt.enableTiltStateCallback();
		} catch (TimeoutException ex) {
		    Logger.getLogger(TiltApplication.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotConnectedException ex) {
		    Logger.getLogger(TiltApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}

    }

    @Override
    public void deviceDisconnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.areEqual(this.tilt, device)) {
	    this.tilt.removeTiltStateListener(this);
	    this.tilt = null;

	}
    }

    @Override
    public boolean equals(final Object obj
    ) {
	return this == obj;
    }

    @Override
    public int hashCode() {
	// TODO Auto-generated method stub
	return 0;
    }

    int tiltStateCount;
    long tiltStateCountTime;

    @Override
    public void tiltState(short state) {
	long delta = System.currentTimeMillis() - tiltStateCountTime;
	if (delta < 1000) {
	    if (tiltStateCount < 10) {
		tiltStateCount++;
	    } else {
		try {
		    tilt.disableTiltStateCallback();
		    manager.captureImage();
		    Thread.sleep(20 * 1000);

		    tilt.enableTiltStateCallback();
		} catch (TimeoutException ex) {
		    Logger.getLogger(TiltApplication.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NotConnectedException ex) {
		    Logger.getLogger(TiltApplication.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
		    Logger.getLogger(TiltApplication.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }

	} else {
	    tiltStateCount = 0;
	}
	this.tiltStateCountTime = System.currentTimeMillis();

    }

}
