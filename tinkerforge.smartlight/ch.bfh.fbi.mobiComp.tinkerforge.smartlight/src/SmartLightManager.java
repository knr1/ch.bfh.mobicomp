import ch.bfh.fbi.mobiComp.tinkerforge.test.LightApplication;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;

import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletRemoteSwitch;

/**
 * This class demonstrates a hardened 'Smart'-Light Application It expects to
 * see the following components:<br/>
 * <ul>
 * <li>{@link BrickletMotionDetector} 1</li>
 * <li>{@link BrickletAmbientLight} 1</li>
 * <li>{@link BrickletDualButton} 1</li>
 * <li>{@link BrickletRemoteSwitch} 1</li>
 * </ul>
 * The application itself does not care on how many stacks the bricklets are
 * spread. However, the manager accepts the following Stacks:
 * <ul>
 * <li>USB-Stack</li>
 * <li>WLAN-Stack called MasterBrick01</li>
 * </ul>
 * 
 * It demonstrates the fusion and fission of sensors and actors. <br/>
 * It does the following: It allows to switch two remote switches (Light is
 * expected) controllable by the dual buttons. Furthermore the motion detector
 * switches the two remote switches if the ambient light is to low. When
 * switched by the motion detector, the remote switches will be switched on for
 * a specific amount of time using a timer logic. If a remote switch has been
 * activated via dualButton, this switch will not be touched by the timer logic.
 * 
 * Try it...
 * 
 * 
 * 
 * @author reto
 * 
 */
public class SmartLightManager {
	// Assumes to be connected via USB
	public static final String STACK_USB_HOST_NAME = "localhost";

	// Assumes to be connected via WLAN
	public static final String STACK_WLAN_HOST_NAME = "MasterBrick01";

	// Assumes to switch the (lights) for two minutes if activated by motion
	// detection.
	public static final long TIMING_DELAY_IN_MILLISECONDS = 1000 * 60 * 2;

	private final TinkerforgeStackAgentIdentifier identifier1;
	private final TinkerforgeStackAgentIdentifier identifier2;

	private final LightApplication lightApplication;

	public SmartLightManager() {
		this.identifier1 = new TinkerforgeStackAgentIdentifier(
				SmartLightManager.STACK_USB_HOST_NAME);
		this.identifier2 = new TinkerforgeStackAgentIdentifier(
				SmartLightManager.STACK_WLAN_HOST_NAME);

		this.lightApplication = new LightApplication();
		this.lightApplication
				.setDelayInMilliseconds(SmartLightManager.TIMING_DELAY_IN_MILLISECONDS);

		this.lightApplication.addTinkerforgeStackAgent(this.identifier1,
				this.identifier2);
	}

	/**
	 * This method allows to gracefully disconnect the {@link LightApplication}
	 * form the {@link TinkerforgeStackAgent}
	 */
	public void stop() {
		this.lightApplication.removeTinkerforgeStackAgent(this.identifier1,
				this.identifier2);
	}

	/**
	 * A simple boot-strap. The program will shut-down gracefully if one hits
	 * 'any' key on the console
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		SmartLightManager smartLightManager = null;
		try {
			smartLightManager = new SmartLightManager();
			System.in.read();
			smartLightManager.stop();
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
	}
}
