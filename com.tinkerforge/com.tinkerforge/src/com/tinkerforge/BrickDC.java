/* ***********************************************************
 * This file was automatically generated on 2013-12-19.      *
 *                                                           *
 * Bindings Version 2.0.14                                    *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generator git on tinkerforge.com                   *
 *************************************************************/

package com.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Device for controlling DC motors
 */
public class BrickDC extends Device {
	public final static int DEVICE_IDENTIFIER = 11;

	public final static byte FUNCTION_SET_VELOCITY = (byte)1;
	public final static byte FUNCTION_GET_VELOCITY = (byte)2;
	public final static byte FUNCTION_GET_CURRENT_VELOCITY = (byte)3;
	public final static byte FUNCTION_SET_ACCELERATION = (byte)4;
	public final static byte FUNCTION_GET_ACCELERATION = (byte)5;
	public final static byte FUNCTION_SET_PWM_FREQUENCY = (byte)6;
	public final static byte FUNCTION_GET_PWM_FREQUENCY = (byte)7;
	public final static byte FUNCTION_FULL_BRAKE = (byte)8;
	public final static byte FUNCTION_GET_STACK_INPUT_VOLTAGE = (byte)9;
	public final static byte FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE = (byte)10;
	public final static byte FUNCTION_GET_CURRENT_CONSUMPTION = (byte)11;
	public final static byte FUNCTION_ENABLE = (byte)12;
	public final static byte FUNCTION_DISABLE = (byte)13;
	public final static byte FUNCTION_IS_ENABLED = (byte)14;
	public final static byte FUNCTION_SET_MINIMUM_VOLTAGE = (byte)15;
	public final static byte FUNCTION_GET_MINIMUM_VOLTAGE = (byte)16;
	public final static byte FUNCTION_SET_DRIVE_MODE = (byte)17;
	public final static byte FUNCTION_GET_DRIVE_MODE = (byte)18;
	public final static byte FUNCTION_SET_CURRENT_VELOCITY_PERIOD = (byte)19;
	public final static byte FUNCTION_GET_CURRENT_VELOCITY_PERIOD = (byte)20;
	public final static byte CALLBACK_UNDER_VOLTAGE = (byte)21;
	public final static byte CALLBACK_EMERGENCY_SHUTDOWN = (byte)22;
	public final static byte CALLBACK_VELOCITY_REACHED = (byte)23;
	public final static byte CALLBACK_CURRENT_VELOCITY = (byte)24;
	public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
	public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
	public final static byte FUNCTION_RESET = (byte)243;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short DRIVE_MODE_DRIVE_BRAKE = (short)0;
	public final static short DRIVE_MODE_DRIVE_COAST = (short)1;

	private List<UnderVoltageListener> listenerUnderVoltage = new CopyOnWriteArrayList<UnderVoltageListener>();
	private List<EmergencyShutdownListener> listenerEmergencyShutdown = new CopyOnWriteArrayList<EmergencyShutdownListener>();
	private List<VelocityReachedListener> listenerVelocityReached = new CopyOnWriteArrayList<VelocityReachedListener>();
	private List<CurrentVelocityListener> listenerCurrentVelocity = new CopyOnWriteArrayList<CurrentVelocityListener>();

	public class Protocol1BrickletName {
		public char port;
		public short protocolVersion;
		public short[] firmwareVersion = new short[3];
		public String name;

		public String toString() {
			return "[" + "port = " + port + ", " + "protocolVersion = " + protocolVersion + ", " + "firmwareVersion = " + Arrays.toString(firmwareVersion) + ", " + "name = " + name + "]";
		}
	}

	/**
	 * This listener is triggered when the input voltage drops below the value set by
	 * {@link BrickDC#setMinimumVoltage(int)}. The parameter is the current voltage given
	 * in mV.
	 */
	public interface UnderVoltageListener {
		public void underVoltage(int voltage);
	}

	/**
	 * This listener is triggered if either the current consumption
	 * is too high (above 5A) or the temperature of the driver chip is too high
	 * (above 175°C). These two possibilities are essentially the same, since the
	 * temperature will reach this threshold immediately if the motor consumes too
	 * much current. In case of a voltage below 3.3V (external or stack) this
	 * listener is triggered as well.
	 * 
	 * If this listener is triggered, the driver chip gets disabled at the same time.
	 * That means, {@link BrickDC#enable()} has to be called to drive the motor again.
	 * 
	 * \note
	 *  This listener only works in Drive/Brake mode (see {@link BrickDC#setDriveMode(short)}). In
	 *  Drive/Coast mode it is unfortunately impossible to reliably read the
	 *  overcurrent/overtemperature signal from the driver chip.
	 */
	public interface EmergencyShutdownListener {
		public void emergencyShutdown();
	}

	/**
	 * This listener is triggered whenever a set velocity is reached. For example:
	 * If a velocity of 0 is present, acceleration is set to 5000 and velocity
	 * to 10000, {@link BrickDC.VelocityReachedListener} will be triggered after about 2 seconds, when
	 * the set velocity is actually reached.
	 * 
	 * \note
	 *  Since we can't get any feedback from the DC motor, this only works if the
	 *  acceleration (see {@link BrickDC#setAcceleration(int)}) is set smaller or equal to the
	 *  maximum acceleration of the motor. Otherwise the motor will lag behind the
	 *  control value and the listener will be triggered too early.
	 */
	public interface VelocityReachedListener {
		public void velocityReached(short velocity);
	}

	/**
	 * This listener is triggered with the period that is set by
	 * {@link BrickDC#setCurrentVelocityPeriod(int)}. The parameter is the *current* velocity
	 * used by the motor.
	 * 
	 * {@link BrickDC.CurrentVelocityListener} is only triggered after the set period if there is
	 * a change in the velocity.
	 */
	public interface CurrentVelocityListener {
		public void currentVelocity(short velocity);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickDC(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_VELOCITY)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VELOCITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_VELOCITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ACCELERATION)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ACCELERATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_PWM_FREQUENCY)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_PWM_FREQUENCY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_FULL_BRAKE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_STACK_INPUT_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_CONSUMPTION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_ENABLE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_DISABLE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_ENABLED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MINIMUM_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MINIMUM_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DRIVE_MODE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DRIVE_MODE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CURRENT_VELOCITY_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_VELOCITY_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_PROTOCOL1_BRICKLET_NAME)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CHIP_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_RESET)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_UNDER_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_EMERGENCY_SHUTDOWN)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_VELOCITY_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_CURRENT_VELOCITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_UNDER_VOLTAGE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int voltage = IPConnection.unsignedShort(bb.getShort());

				for(UnderVoltageListener listener: listenerUnderVoltage) {
					listener.underVoltage(voltage);
				}
			}
		};

		callbacks[CALLBACK_EMERGENCY_SHUTDOWN] = new CallbackListener() {
			public void callback(byte[] data) {
				for(EmergencyShutdownListener listener: listenerEmergencyShutdown) {
					listener.emergencyShutdown();
				}
			}
		};

		callbacks[CALLBACK_VELOCITY_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short velocity = (bb.getShort());

				for(VelocityReachedListener listener: listenerVelocityReached) {
					listener.velocityReached(velocity);
				}
			}
		};

		callbacks[CALLBACK_CURRENT_VELOCITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short velocity = (bb.getShort());

				for(CurrentVelocityListener listener: listenerCurrentVelocity) {
					listener.currentVelocity(velocity);
				}
			}
		};
	}

	/**
	 * Sets the velocity of the motor. Whereas -32767 is full speed backward,
	 * 0 is stop and 32767 is full speed forward. Depending on the
	 * acceleration (see {@link BrickDC#setAcceleration(int)}), the motor is not immediately
	 * brought to the velocity but smoothly accelerated.
	 * 
	 * The velocity describes the duty cycle of the PWM with which the motor is
	 * controlled, e.g. a velocity of 3277 sets a PWM with a 10% duty cycle.
	 * You can not only control the duty cycle of the PWM but also the frequency,
	 * see {@link BrickDC#setPWMFrequency(int)}.
	 * 
	 * The default velocity is 0.
	 */
	public void setVelocity(short velocity) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_VELOCITY, this);
		bb.putShort(velocity);

		sendRequest(bb.array());
	}

	/**
	 * Returns the velocity as set by {@link BrickDC#setVelocity(short)}.
	 */
	public short getVelocity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VELOCITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short velocity = (bb.getShort());

		return velocity;
	}

	/**
	 * Returns the *current* velocity of the motor. This value is different
	 * from {@link BrickDC#getVelocity()} whenever the motor is currently accelerating
	 * to a goal set by {@link BrickDC#setVelocity(short)}.
	 */
	public short getCurrentVelocity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CURRENT_VELOCITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short velocity = (bb.getShort());

		return velocity;
	}

	/**
	 * Sets the acceleration of the motor. It is given in *velocity/s*. An
	 * acceleration of 10000 means, that every second the velocity is increased
	 * by 10000 (or about 30% duty cycle).
	 * 
	 * For example: If the current velocity is 0 and you want to accelerate to a
	 * velocity of 16000 (about 50% duty cycle) in 10 seconds, you should set
	 * an acceleration of 1600.
	 * 
	 * If acceleration is set to 0, there is no speed ramping, i.e. a new velocity
	 * is immediately given to the motor.
	 * 
	 * The default acceleration is 10000.
	 */
	public void setAcceleration(int acceleration) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_ACCELERATION, this);
		bb.putShort((short)acceleration);

		sendRequest(bb.array());
	}

	/**
	 * Returns the acceleration as set by {@link BrickDC#setAcceleration(int)}.
	 */
	public int getAcceleration() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ACCELERATION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int acceleration = IPConnection.unsignedShort(bb.getShort());

		return acceleration;
	}

	/**
	 * Sets the frequency (in Hz) of the PWM with which the motor is driven.
	 * The possible range of the frequency is 1-20000Hz. Often a high frequency
	 * is less noisy and the motor runs smoother. However, with a low frequency
	 * there are less switches and therefore fewer switching losses. Also with
	 * most motors lower frequencies enable higher torque.
	 * 
	 * If you have no idea what all this means, just ignore this function and use
	 * the default frequency, it will very likely work fine.
	 * 
	 * The default frequency is 15 kHz.
	 */
	public void setPWMFrequency(int frequency) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_PWM_FREQUENCY, this);
		bb.putShort((short)frequency);

		sendRequest(bb.array());
	}

	/**
	 * Returns the PWM frequency (in Hz) as set by {@link BrickDC#setPWMFrequency(int)}.
	 */
	public int getPWMFrequency() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_PWM_FREQUENCY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int frequency = IPConnection.unsignedShort(bb.getShort());

		return frequency;
	}

	/**
	 * Executes an active full brake.
	 * 
	 * \warning
	 *  This function is for emergency purposes,
	 *  where an immediate brake is necessary. Depending on the current velocity and
	 *  the strength of the motor, a full brake can be quite violent.
	 * 
	 * Call {@link BrickDC#setVelocity(short)} with 0 if you just want to stop the motor.
	 */
	public void fullBrake() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_FULL_BRAKE, this);

		sendRequest(bb.array());
	}

	/**
	 * Returns the stack input voltage in mV. The stack input voltage is the
	 * voltage that is supplied via the stack, i.e. it is given by a
	 * Step-Down or Step-Up Power Supply.
	 */
	public int getStackInputVoltage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_STACK_INPUT_VOLTAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		return voltage;
	}

	/**
	 * Returns the external input voltage in mV. The external input voltage is
	 * given via the black power input connector on the DC Brick.
	 * 
	 * If there is an external input voltage and a stack input voltage, the motor
	 * will be driven by the external input voltage. If there is only a stack
	 * voltage present, the motor will be driven by this voltage.
	 * 
	 * \warning
	 *  This means, if you have a high stack voltage and a low external voltage,
	 *  the motor will be driven with the low external voltage. If you then remove
	 *  the external connection, it will immediately be driven by the high
	 *  stack voltage.
	 */
	public int getExternalInputVoltage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_EXTERNAL_INPUT_VOLTAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		return voltage;
	}

	/**
	 * Returns the current consumption of the motor in mA.
	 */
	public int getCurrentConsumption() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CURRENT_CONSUMPTION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		return voltage;
	}

	/**
	 * Enables the driver chip. The driver parameters can be configured (velocity,
	 * acceleration, etc) before it is enabled.
	 */
	public void enable() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_ENABLE, this);

		sendRequest(bb.array());
	}

	/**
	 * Disables the driver chip. The configurations are kept (velocity,
	 * acceleration, etc) but the motor is not driven until it is enabled again.
	 */
	public void disable() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_DISABLE, this);

		sendRequest(bb.array());
	}

	/**
	 * Returns *true* if the driver chip is enabled, *false* otherwise.
	 */
	public boolean isEnabled() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_IS_ENABLED, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean enabled = (bb.get()) != 0;

		return enabled;
	}

	/**
	 * Sets the minimum voltage in mV, below which the {@link BrickDC.UnderVoltageListener} listener
	 * is triggered. The minimum possible value that works with the DC Brick is 5V.
	 * You can use this function to detect the discharge of a battery that is used
	 * to drive the motor. If you have a fixed power supply, you likely do not need
	 * this functionality.
	 * 
	 * The default value is 5V.
	 */
	public void setMinimumVoltage(int voltage) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_MINIMUM_VOLTAGE, this);
		bb.putShort((short)voltage);

		sendRequest(bb.array());
	}

	/**
	 * Returns the minimum voltage as set by {@link BrickDC#setMinimumVoltage(int)}
	 */
	public int getMinimumVoltage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MINIMUM_VOLTAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = IPConnection.unsignedShort(bb.getShort());

		return voltage;
	}

	/**
	 * Sets the drive mode. Possible modes are:
	 * 
	 * * 0 = Drive/Brake
	 * * 1 = Drive/Coast
	 * 
	 * These modes are different kinds of motor controls.
	 * 
	 * In Drive/Brake mode, the motor is always either driving or braking. There
	 * is no freewheeling. Advantages are: A more linear correlation between
	 * PWM and velocity, more exact accelerations and the possibility to drive
	 * with slower velocities.
	 * 
	 * In Drive/Coast mode, the motor is always either driving or freewheeling.
	 * Advantages are: Less current consumption and less demands on the motor and
	 * driver chip.
	 * 
	 * The default value is 0 = Drive/Brake.
	 */
	public void setDriveMode(short mode) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_DRIVE_MODE, this);
		bb.put((byte)mode);

		sendRequest(bb.array());
	}

	/**
	 * Returns the drive mode, as set by {@link BrickDC#setDriveMode(short)}.
	 */
	public short getDriveMode() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DRIVE_MODE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short mode = IPConnection.unsignedByte(bb.get());

		return mode;
	}

	/**
	 * Sets a period in ms with which the {@link BrickDC.CurrentVelocityListener} listener is triggered.
	 * A period of 0 turns the listener off.
	 * 
	 * The default value is 0.
	 */
	public void setCurrentVelocityPeriod(int period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_CURRENT_VELOCITY_PERIOD, this);
		bb.putShort((short)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickDC#setCurrentVelocityPeriod(int)}.
	 */
	public int getCurrentVelocityPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CURRENT_VELOCITY_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int period = IPConnection.unsignedShort(bb.getShort());

		return period;
	}

	/**
	 * Returns the firmware and protocol version and the name of the Bricklet for a
	 * given port.
	 * 
	 * This functions sole purpose is to allow automatic flashing of v1.x.y Bricklet
	 * plugins.
	 * 
	 * .. versionadded:: 2.0.0~(Firmware)
	 */
	public Protocol1BrickletName getProtocol1BrickletName(char port) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_PROTOCOL1_BRICKLET_NAME, this);
		bb.put((byte)port);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Protocol1BrickletName obj = new Protocol1BrickletName();
		obj.protocolVersion = IPConnection.unsignedByte(bb.get());
		for(int i = 0; i < 3; i++) {
			obj.firmwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		obj.name = IPConnection.string(bb, 40);

		return obj;
	}

	/**
	 * Returns the temperature in °C/10 as measured inside the microcontroller. The
	 * value returned is not the ambient temperature!
	 * 
	 * The temperature is only proportional to the real temperature and it has an
	 * accuracy of +-15%. Practically it is only useful as an indicator for
	 * temperature changes.
	 * 
	 * .. versionadded:: 1.1.3~(Firmware)
	 */
	public short getChipTemperature() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CHIP_TEMPERATURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short temperature = (bb.getShort());

		return temperature;
	}

	/**
	 * Calling this function will reset the Brick. Calling this function
	 * on a Brick inside of a stack will reset the whole stack.
	 * 
	 * After a reset you have to create new device objects,
	 * calling functions on the existing ones will result in
	 * undefined behavior!
	 * 
	 * .. versionadded:: 1.1.3~(Firmware)
	 */
	public void reset() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_RESET, this);

		sendRequest(bb.array());
	}

	/**
	 * Returns the UID, the UID where the Brick is connected to, 
	 * the position, the hardware and firmware version as well as the
	 * device identifier.
	 * 
	 * The position can be '0'-'8' (stack position).
	 * 
	 * The device identifiers can be found :ref:`here <device_identifier>`.
	 * 
	 * .. versionadded:: 2.0.0~(Firmware)
	 */
	public Identity getIdentity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_IDENTITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Identity obj = new Identity();
		obj.uid = IPConnection.string(bb, 8);
		obj.connectedUid = IPConnection.string(bb, 8);
		obj.position = (char)(bb.get());
		for(int i = 0; i < 3; i++) {
			obj.hardwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		for(int i = 0; i < 3; i++) {
			obj.firmwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		obj.deviceIdentifier = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Adds a UnderVoltage listener.
	 */
	public void addUnderVoltageListener(UnderVoltageListener listener) {
		listenerUnderVoltage.add(listener);
	}

	/**
	 * Removes a UnderVoltage listener.
	 */
	public void removeUnderVoltageListener(UnderVoltageListener listener) {
		listenerUnderVoltage.remove(listener);
	}

	/**
	 * Adds a EmergencyShutdown listener.
	 */
	public void addEmergencyShutdownListener(EmergencyShutdownListener listener) {
		listenerEmergencyShutdown.add(listener);
	}

	/**
	 * Removes a EmergencyShutdown listener.
	 */
	public void removeEmergencyShutdownListener(EmergencyShutdownListener listener) {
		listenerEmergencyShutdown.remove(listener);
	}

	/**
	 * Adds a VelocityReached listener.
	 */
	public void addVelocityReachedListener(VelocityReachedListener listener) {
		listenerVelocityReached.add(listener);
	}

	/**
	 * Removes a VelocityReached listener.
	 */
	public void removeVelocityReachedListener(VelocityReachedListener listener) {
		listenerVelocityReached.remove(listener);
	}

	/**
	 * Adds a CurrentVelocity listener.
	 */
	public void addCurrentVelocityListener(CurrentVelocityListener listener) {
		listenerCurrentVelocity.add(listener);
	}

	/**
	 * Removes a CurrentVelocity listener.
	 */
	public void removeCurrentVelocityListener(CurrentVelocityListener listener) {
		listenerCurrentVelocity.remove(listener);
	}
}