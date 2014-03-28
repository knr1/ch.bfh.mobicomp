package ch.quantasy.tinkerforge.tinker.core.implementation;

import com.tinkerforge.BrickDC;
import com.tinkerforge.BrickIMU;
import com.tinkerforge.BrickMaster;
import com.tinkerforge.BrickServo;
import com.tinkerforge.BrickStepper;
import com.tinkerforge.BrickletAmbientLight;
import com.tinkerforge.BrickletAnalogIn;
import com.tinkerforge.BrickletAnalogOut;
import com.tinkerforge.BrickletBarometer;
import com.tinkerforge.BrickletCurrent12;
import com.tinkerforge.BrickletCurrent25;
import com.tinkerforge.BrickletDistanceIR;
import com.tinkerforge.BrickletDistanceUS;
import com.tinkerforge.BrickletDualButton;
import com.tinkerforge.BrickletDualRelay;
import com.tinkerforge.BrickletGPS;
import com.tinkerforge.BrickletHallEffect;
import com.tinkerforge.BrickletHumidity;
import com.tinkerforge.BrickletIO16;
import com.tinkerforge.BrickletIO4;
import com.tinkerforge.BrickletIndustrialDigitalIn4;
import com.tinkerforge.BrickletIndustrialDigitalOut4;
import com.tinkerforge.BrickletIndustrialDual020mA;
import com.tinkerforge.BrickletIndustrialQuadRelay;
import com.tinkerforge.BrickletJoystick;
import com.tinkerforge.BrickletLCD16x2;
import com.tinkerforge.BrickletLCD20x4;
import com.tinkerforge.BrickletLEDStrip;
import com.tinkerforge.BrickletLine;
import com.tinkerforge.BrickletLinearPoti;
import com.tinkerforge.BrickletMoisture;
import com.tinkerforge.BrickletMotionDetector;
import com.tinkerforge.BrickletMultiTouch;
import com.tinkerforge.BrickletPTC;
import com.tinkerforge.BrickletPiezoBuzzer;
import com.tinkerforge.BrickletPiezoSpeaker;
import com.tinkerforge.BrickletRemoteSwitch;
import com.tinkerforge.BrickletRotaryEncoder;
import com.tinkerforge.BrickletRotaryPoti;
import com.tinkerforge.BrickletSegmentDisplay4x7;
import com.tinkerforge.BrickletSoundIntensity;
import com.tinkerforge.BrickletTemperature;
import com.tinkerforge.BrickletTemperatureIR;
import com.tinkerforge.BrickletTilt;
import com.tinkerforge.BrickletVoltage;
import com.tinkerforge.BrickletVoltageCurrent;
import com.tinkerforge.Device;
import com.tinkerforge.Device.Identity;

/**
 * This enum is used as a convenience to map the Tinker-Bricks and
 * Tinker-Bricklets It tries to hide the 'strange' stuff from the Java-Developer
 * ;-)
 * 
 * @author reto
 * 
 */
public enum TinkerforgeDevice {
	DC (BrickDC.class) {
		public BrickDC getTypedInstance(Device device) {
			if(device instanceof BrickDC)
				return BrickDC.class.cast(device);
			return null;
		}
	} ,
	IMU (BrickIMU.class) , Master (BrickMaster.class) , Servo (BrickServo.class) , Stepper (BrickStepper.class) ,

	AmbientLight (BrickletAmbientLight.class) , AnalogIn (BrickletAnalogIn.class) , AnalogOut (BrickletAnalogOut.class) , Barometer (
			BrickletBarometer.class) , Current12 (BrickletCurrent12.class) , Current25 (BrickletCurrent25.class) , DistanceIR (
			BrickletDistanceIR.class) , DistanceUS (BrickletDistanceUS.class) , DualButton (BrickletDualButton.class) , DualRelay (
			BrickletDualRelay.class) , GPS (BrickletGPS.class) , HallEffect (BrickletHallEffect.class) , Humidity (
			BrickletHumidity.class) , IndustrialDigitalIn4 (BrickletIndustrialDigitalIn4.class) , IndustrialDigitalOut4 (
			BrickletIndustrialDigitalOut4.class) , IndustrialDual020mA (BrickletIndustrialDual020mA.class) , IndustrialQuadRelay (
			BrickletIndustrialQuadRelay.class) , IO16 (BrickletIO16.class) , IO4 (BrickletIO4.class) , Joystick (
			BrickletJoystick.class) , LCD16x2 (BrickletLCD16x2.class) , LCD20x4 (BrickletLCD20x4.class) , LEDStrip (
			BrickletLEDStrip.class) , Line (BrickletLine.class) , LinearPoti (BrickletLinearPoti.class) , Moisture (
			BrickletMoisture.class) , MotionDetector (BrickletMotionDetector.class) , MultiTouch (BrickletMultiTouch.class) , PiezoBuzzer (
			BrickletPiezoBuzzer.class) , PiezoSpeaker (BrickletPiezoSpeaker.class) , PTC (BrickletPTC.class) , RemoteSwitch (
			BrickletRemoteSwitch.class) , RotaryEncoder (BrickletRotaryEncoder.class) , RotaryPoti (BrickletRotaryPoti.class) , SegmentDisplay4x7 (
			BrickletSegmentDisplay4x7.class) , SoundIntensity (BrickletSoundIntensity.class) , Temperature (
			BrickletTemperature.class) , TemperatureIR (BrickletTemperatureIR.class) , Tilt (BrickletTilt.class) , Voltage (
			BrickletVoltage.class) , VoltageCurrent (BrickletVoltageCurrent.class);

	public final int identifier;
	public final Class<?> device;

	public Device getTypedInstance(Device device) {
		return null;
	}

	private TinkerforgeDevice(final Class<?> device) {
		if (device == null) {
			throw new IllegalArgumentException();
		}
		this.device = device;
		int internalIdentifier = -1;
		try {
			internalIdentifier = device.getField("DEVICE_IDENTIFIER").getInt(null);
		} catch (final Exception e) {
			// No identifier
		}
		this.identifier = internalIdentifier;
	}

	public static String toString(final Device device) {
		try {
			return device.getIdentity().toString();
		} catch (final Exception e) {
			return device.toString();
		}
	}

	public static boolean areEqual(final Device device1, final Device device2) {
		Identity id1 = null;
		Identity id2 = null;
		try {
			id1 = device1.getIdentity();
		} catch (final Exception ex) {

		}
		try {
			id2 = device2.getIdentity();
		} catch (final Exception ex) {

		}
		if ((id1 == null) && (id2 == null)) {
			return TinkerforgeDevice.getDevice(device1) == TinkerforgeDevice.getDevice(device2);
		}
		if ((id1 == null) || (id2 == null)) {
			return false;
		}
		if (id1.deviceIdentifier != id2.deviceIdentifier) {
			return false;
		}
		if (!id1.uid.equals(id2.uid)) {
			return false;
		}
		if (!id1.connectedUid.equals(id2.connectedUid)) {
			return false;
		}
		if (id1.position != id2.position) {
			return false;
		}
		return true;
	}

	public static TinkerforgeDevice getDevice(final Device device) {
		if (device == null) {
			return null;
		}
		final Class<? extends Device> deviceClass = device.getClass();
		for (final TinkerforgeDevice tinkerforgeDevice : TinkerforgeDevice.values()) {
			if (deviceClass == tinkerforgeDevice.device) {
				return tinkerforgeDevice;
			}
		}
		return null;
	}

	public static TinkerforgeDevice getDevice(final int deviceIdentifier) {
		for (final TinkerforgeDevice tinkerforgeDevice : TinkerforgeDevice.values()) {
			if (deviceIdentifier == tinkerforgeDevice.identifier) {
				return tinkerforgeDevice;
			}
		}
		return null;
	}

}
