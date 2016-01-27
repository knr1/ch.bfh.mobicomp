package ch.bfh.smoje.tinkerforge.imagecapture;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeDevice;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.Device;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaySoundApplication extends AbstractTinkerforgeApplication {

    private BrickletPiezoSpeaker speaker;
    private ImageCaptureManager manager;

    public PlaySoundApplication(ImageCaptureManager manager) {
	this.manager = manager;
    }

    @Override
    public void deviceConnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.getDevice(device) == TinkerforgeDevice.PiezoSpeaker) {
	    if (this.speaker == null) {
		this.speaker = (BrickletPiezoSpeaker) device;
	    }
	}

    }

    @Override
    public void deviceDisconnected(
	    final TinkerforgeStackAgent tinkerforgeStackAgent,
	    final Device device) {
	if (TinkerforgeDevice.areEqual(this.speaker, device)) {
	    this.speaker = null;

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

    public void alert() {
	if (this.speaker == null) {
	    return;
	}
	try {
	    for (int i = 585; i < 7000; i += 5) {
		this.speaker.beep(30, i);
		Thread.sleep(2);
	    }
	    this.speaker.beep(300, 1750);
	} catch (TimeoutException ex) {
	    Logger.getLogger(PlaySoundApplication.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NotConnectedException ex) {
	    Logger.getLogger(PlaySoundApplication.class.getName()).log(Level.SEVERE, null, ex);
	} catch (InterruptedException ex) {
	    Logger.getLogger(PlaySoundApplication.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
