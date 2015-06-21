package ch.bfh.smoje.tinkerforge.imagecapture;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletSoundIntensity.IntensityReachedListener;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoundIntensityApplication extends AbstractTinkerforgeApplication implements IntensityReachedListener {

    private BrickletSoundIntensity soundIntensity;
    private ImageCaptureManager manager;

    public SoundIntensityApplication(ImageCaptureManager manager) {
	this.manager = manager;
    }

    @Override
    public void deviceConnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.SoundIntensity) {
	    if (this.soundIntensity == null) {
		this.soundIntensity = (BrickletSoundIntensity) device;
		this.soundIntensity
			.addIntensityReachedListener(this);
		try {
		    try {
			this.soundIntensity.setDebouncePeriod(50 * 1000);
			this.soundIntensity.setIntensityCallbackThreshold('>', 500, 500);
		    } catch (com.tinkerforge.TimeoutException ex) {
			Logger.getLogger(SoundIntensityApplication.class.getName()).log(Level.SEVERE, null, ex);
		    }
		} catch (final NotConnectedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    }
	}

    }

    @Override
    public void deviceDisconnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.areEqual(this.soundIntensity, device)) {
	    this.soundIntensity.removeIntensityReachedListener(this);
	    this.soundIntensity = null;

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

    @Override
    public void intensityReached(int intensity) {
	manager.captureImage();
    }

}
