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
 * Device for high precision sensing of voltage and current
 */
public class BrickletVoltageCurrent extends Device {
	public final static int DEVICE_IDENTIFIER = 227;

	public final static byte FUNCTION_GET_CURRENT = (byte)1;
	public final static byte FUNCTION_GET_VOLTAGE = (byte)2;
	public final static byte FUNCTION_GET_POWER = (byte)3;
	public final static byte FUNCTION_SET_CONFIGURATION = (byte)4;
	public final static byte FUNCTION_GET_CONFIGURATION = (byte)5;
	public final static byte FUNCTION_SET_CALIBRATION = (byte)6;
	public final static byte FUNCTION_GET_CALIBRATION = (byte)7;
	public final static byte FUNCTION_SET_CURRENT_CALLBACK_PERIOD = (byte)8;
	public final static byte FUNCTION_GET_CURRENT_CALLBACK_PERIOD = (byte)9;
	public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD = (byte)10;
	public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD = (byte)11;
	public final static byte FUNCTION_SET_POWER_CALLBACK_PERIOD = (byte)12;
	public final static byte FUNCTION_GET_POWER_CALLBACK_PERIOD = (byte)13;
	public final static byte FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD = (byte)14;
	public final static byte FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD = (byte)15;
	public final static byte FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD = (byte)16;
	public final static byte FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD = (byte)17;
	public final static byte FUNCTION_SET_POWER_CALLBACK_THRESHOLD = (byte)18;
	public final static byte FUNCTION_GET_POWER_CALLBACK_THRESHOLD = (byte)19;
	public final static byte FUNCTION_SET_DEBOUNCE_PERIOD = (byte)20;
	public final static byte FUNCTION_GET_DEBOUNCE_PERIOD = (byte)21;
	public final static byte CALLBACK_CURRENT = (byte)22;
	public final static byte CALLBACK_VOLTAGE = (byte)23;
	public final static byte CALLBACK_POWER = (byte)24;
	public final static byte CALLBACK_CURRENT_REACHED = (byte)25;
	public final static byte CALLBACK_VOLTAGE_REACHED = (byte)26;
	public final static byte CALLBACK_POWER_REACHED = (byte)27;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short AVERAGING_1 = (short)0;
	public final static short AVERAGING_4 = (short)1;
	public final static short AVERAGING_16 = (short)2;
	public final static short AVERAGING_64 = (short)3;
	public final static short AVERAGING_128 = (short)4;
	public final static short AVERAGING_256 = (short)5;
	public final static short AVERAGING_512 = (short)6;
	public final static short AVERAGING_1024 = (short)7;
	public final static char THRESHOLD_OPTION_OFF = 'x';
	public final static char THRESHOLD_OPTION_OUTSIDE = 'o';
	public final static char THRESHOLD_OPTION_INSIDE = 'i';
	public final static char THRESHOLD_OPTION_SMALLER = '<';
	public final static char THRESHOLD_OPTION_GREATER = '>';

	private List<CurrentListener> listenerCurrent = new CopyOnWriteArrayList<CurrentListener>();
	private List<VoltageListener> listenerVoltage = new CopyOnWriteArrayList<VoltageListener>();
	private List<PowerListener> listenerPower = new CopyOnWriteArrayList<PowerListener>();
	private List<CurrentReachedListener> listenerCurrentReached = new CopyOnWriteArrayList<CurrentReachedListener>();
	private List<VoltageReachedListener> listenerVoltageReached = new CopyOnWriteArrayList<VoltageReachedListener>();
	private List<PowerReachedListener> listenerPowerReached = new CopyOnWriteArrayList<PowerReachedListener>();

	public class Configuration {
		public short averaging;
		public short voltageConversionTime;
		public short currentConversionTime;

		public String toString() {
			return "[" + "averaging = " + averaging + ", " + "voltageConversionTime = " + voltageConversionTime + ", " + "currentConversionTime = " + currentConversionTime + "]";
		}
	}

	public class Calibration {
		public int gainMultiplier;
		public int gainDivisor;

		public String toString() {
			return "[" + "gainMultiplier = " + gainMultiplier + ", " + "gainDivisor = " + gainDivisor + "]";
		}
	}

	public class CurrentCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class VoltageCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	public class PowerCallbackThreshold {
		public char option;
		public int min;
		public int max;

		public String toString() {
			return "[" + "option = " + option + ", " + "min = " + min + ", " + "max = " + max + "]";
		}
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletVoltageCurrent#setCurrentCallbackPeriod(long)}. The parameter is the current of the
	 * sensor.
	 * 
	 * {@link BrickletVoltageCurrent.CurrentListener} is only triggered if the current has changed since the
	 * last triggering.
	 */
	public interface CurrentListener {
		public void current(int current);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletVoltageCurrent#setVoltageCallbackPeriod(long)}. The parameter is the voltage of the
	 * sensor.
	 * 
	 * {@link BrickletVoltageCurrent.VoltageListener} is only triggered if the voltage has changed since the
	 * last triggering.
	 */
	public interface VoltageListener {
		public void voltage(int voltage);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickletVoltageCurrent#setPowerCallbackPeriod(long)}. The parameter is the power of the
	 * sensor.
	 * 
	 * {@link BrickletVoltageCurrent.PowerListener} is only triggered if the power has changed since the
	 * last triggering.
	 */
	public interface PowerListener {
		public void power(int power);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletVoltageCurrent#setCurrentCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the current of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletVoltageCurrent#setDebouncePeriod(long)}.
	 */
	public interface CurrentReachedListener {
		public void currentReached(int current);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletVoltageCurrent#setVoltageCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the voltage of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletVoltageCurrent#setDebouncePeriod(long)}.
	 */
	public interface VoltageReachedListener {
		public void voltageReached(int voltage);
	}

	/**
	 * This listener is triggered when the threshold as set by
	 * {@link BrickletVoltageCurrent#setPowerCallbackThreshold(char, int, int)} is reached.
	 * The parameter is the power of the sensor.
	 * 
	 * If the threshold keeps being reached, the listener is triggered periodically
	 * with the period as set by {@link BrickletVoltageCurrent#setDebouncePeriod(long)}.
	 */
	public interface PowerReachedListener {
		public void powerReached(int power);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletVoltageCurrent(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_POWER)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CONFIGURATION)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CONFIGURATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CALIBRATION)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CALIBRATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CURRENT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_POWER_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_POWER_CALLBACK_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_POWER_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_POWER_CALLBACK_THRESHOLD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_DEBOUNCE_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_CURRENT)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_VOLTAGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_POWER)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_CURRENT_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_VOLTAGE_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_POWER_REACHED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_CURRENT] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int current = (bb.getInt());

				for(CurrentListener listener: listenerCurrent) {
					listener.current(current);
				}
			}
		};

		callbacks[CALLBACK_VOLTAGE] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int voltage = (bb.getInt());

				for(VoltageListener listener: listenerVoltage) {
					listener.voltage(voltage);
				}
			}
		};

		callbacks[CALLBACK_POWER] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int power = (bb.getInt());

				for(PowerListener listener: listenerPower) {
					listener.power(power);
				}
			}
		};

		callbacks[CALLBACK_CURRENT_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int current = (bb.getInt());

				for(CurrentReachedListener listener: listenerCurrentReached) {
					listener.currentReached(current);
				}
			}
		};

		callbacks[CALLBACK_VOLTAGE_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int voltage = (bb.getInt());

				for(VoltageReachedListener listener: listenerVoltageReached) {
					listener.voltageReached(voltage);
				}
			}
		};

		callbacks[CALLBACK_POWER_REACHED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				int power = (bb.getInt());

				for(PowerReachedListener listener: listenerPowerReached) {
					listener.powerReached(power);
				}
			}
		};
	}

	/**
	 * Returns the current. The value is in mA
	 * and between -20000mA and 20000mA.
	 * 
	 * If you want to get the current periodically, it is recommended to use the
	 * listener {@link BrickletVoltageCurrent.CurrentListener} and set the period with 
	 * {@link BrickletVoltageCurrent#setCurrentCallbackPeriod(long)}.
	 */
	public int getCurrent() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CURRENT, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int current = (bb.getInt());

		return current;
	}

	/**
	 * Returns the voltage. The value is in mV
	 * and between 0mV and 36000mV.
	 * 
	 * If you want to get the voltage periodically, it is recommended to use the
	 * listener {@link BrickletVoltageCurrent.VoltageListener} and set the period with 
	 * {@link BrickletVoltageCurrent#setVoltageCallbackPeriod(long)}.
	 */
	public int getVoltage() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VOLTAGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int voltage = (bb.getInt());

		return voltage;
	}

	/**
	 * Returns the power. The value is in mW
	 * and between 0mV and 720000mW.
	 * 
	 * If you want to get the power periodically, it is recommended to use the
	 * listener {@link BrickletVoltageCurrent.PowerListener} and set the period with 
	 * {@link BrickletVoltageCurrent#setPowerCallbackPeriod(long)}.
	 */
	public int getPower() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_POWER, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int power = (bb.getInt());

		return power;
	}

	/**
	 * Sets the configuration of the Voltage/Current Bricklet. It is
	 * possible to configure number of averages as well as
	 * voltage and current conversion time.
	 * 
	 * Averaging:
	 * 
	 * \verbatim
	 *  "Value", "Number of Averages"
	 * 
	 *  "0",    "1"
	 *  "1",    "4"
	 *  "2",    "16"
	 *  "3",    "64"
	 *  "4",    "128"
	 *  "5",    "256"
	 *  "6",    "512"
	 *  ">=7",  "1024"
	 * \endverbatim
	 * 
	 * Voltage/Current conversion:
	 * 
	 * \verbatim
	 *  "Value", "Conversion time"
	 * 
	 *  "0",    "140µs"
	 *  "1",    "204µs"
	 *  "2",    "332µs"
	 *  "3",    "588µs"
	 *  "4",    "1.1ms"
	 *  "5",    "2.116ms"
	 *  "6",    "4.156ms"
	 *  ">=7",  "8.244ms"
	 * \endverbatim
	 * 
	 * The default values are 3, 4 and 4 (64, 1.1ms, 1.1ms) for averaging, voltage 
	 * conversion and current conversion.
	 */
	public void setConfiguration(short averaging, short voltageConversionTime, short currentConversionTime) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)11, FUNCTION_SET_CONFIGURATION, this);
		bb.put((byte)averaging);
		bb.put((byte)voltageConversionTime);
		bb.put((byte)currentConversionTime);

		sendRequest(bb.array());
	}

	/**
	 * Returns the configuration as set by {@link BrickletVoltageCurrent#setConfiguration(short, short, short)}.
	 */
	public Configuration getConfiguration() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CONFIGURATION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Configuration obj = new Configuration();
		obj.averaging = IPConnection.unsignedByte(bb.get());
		obj.voltageConversionTime = IPConnection.unsignedByte(bb.get());
		obj.currentConversionTime = IPConnection.unsignedByte(bb.get());

		return obj;
	}

	/**
	 * Since the shunt resistor that is used to measure the current is not
	 * perfectly precise, it needs to be calibrated by a multiplier and
	 * divisor if a very precise reading is needed.
	 * 
	 * For example, if you are expecting a measurement of 1000mA and you
	 * are measuring 1023mA, you can calibrate the Voltage/Current Bricklet 
	 * by setting the multiplier to 1000 and the divisor to 1023.
	 */
	public void setCalibration(int gainMultiplier, int gainDivisor) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_CALIBRATION, this);
		bb.putShort((short)gainMultiplier);
		bb.putShort((short)gainDivisor);

		sendRequest(bb.array());
	}

	/**
	 * Returns the calibration as set by {@link BrickletVoltageCurrent#setCalibration(int, int)}.
	 */
	public Calibration getCalibration() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CALIBRATION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Calibration obj = new Calibration();
		obj.gainMultiplier = IPConnection.unsignedShort(bb.getShort());
		obj.gainDivisor = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletVoltageCurrent.CurrentListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletVoltageCurrent.CurrentListener} is only triggered if the current has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setCurrentCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_CURRENT_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletVoltageCurrent#setCurrentCallbackPeriod(long)}.
	 */
	public long getCurrentCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CURRENT_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletVoltageCurrent.VoltageListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletVoltageCurrent.VoltageListener} is only triggered if the voltage has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setVoltageCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_VOLTAGE_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletVoltageCurrent#setVoltageCallbackPeriod(long)}.
	 */
	public long getVoltageCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VOLTAGE_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickletVoltageCurrent.PowerListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * {@link BrickletVoltageCurrent.PowerListener} is only triggered if the power has changed since the
	 * last triggering.
	 * 
	 * The default value is 0.
	 */
	public void setPowerCallbackPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_POWER_CALLBACK_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickletVoltageCurrent#getPowerCallbackPeriod()}.
	 */
	public long getPowerCallbackPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_POWER_CALLBACK_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the thresholds for the {@link BrickletVoltageCurrent.CurrentReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the current is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the current is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the current is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the current is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setCurrentCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_CURRENT_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletVoltageCurrent#setCurrentCallbackThreshold(char, int, int)}.
	 */
	public CurrentCallbackThreshold getCurrentCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CURRENT_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		CurrentCallbackThreshold obj = new CurrentCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletVoltageCurrent.VoltageReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the voltage is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the voltage is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the voltage is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the voltage is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setVoltageCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_VOLTAGE_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletVoltageCurrent#setVoltageCallbackThreshold(char, int, int)}.
	 */
	public VoltageCallbackThreshold getVoltageCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_VOLTAGE_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		VoltageCallbackThreshold obj = new VoltageCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the thresholds for the {@link BrickletVoltageCurrent.PowerReachedListener} listener. 
	 * 
	 * The following options are possible:
	 * 
	 * \verbatim
	 *  "Option", "Description"
	 * 
	 *  "'x'",    "Listener is turned off"
	 *  "'o'",    "Listener is triggered when the power is *outside* the min and max values"
	 *  "'i'",    "Listener is triggered when the power is *inside* the min and max values"
	 *  "'<'",    "Listener is triggered when the power is smaller than the min value (max is ignored)"
	 *  "'>'",    "Listener is triggered when the power is greater than the min value (max is ignored)"
	 * \endverbatim
	 * 
	 * The default value is ('x', 0, 0).
	 */
	public void setPowerCallbackThreshold(char option, int min, int max) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)17, FUNCTION_SET_POWER_CALLBACK_THRESHOLD, this);
		bb.put((byte)option);
		bb.putInt(min);
		bb.putInt(max);

		sendRequest(bb.array());
	}

	/**
	 * Returns the threshold as set by {@link BrickletVoltageCurrent#setPowerCallbackThreshold(char, int, int)}.
	 */
	public PowerCallbackThreshold getPowerCallbackThreshold() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_POWER_CALLBACK_THRESHOLD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		PowerCallbackThreshold obj = new PowerCallbackThreshold();
		obj.option = (char)(bb.get());
		obj.min = (bb.getInt());
		obj.max = (bb.getInt());

		return obj;
	}

	/**
	 * Sets the period in ms with which the threshold listeners
	 * 
	 * * {@link BrickletVoltageCurrent.CurrentReachedListener},
	 * * {@link BrickletVoltageCurrent.VoltageReachedListener},
	 * * {@link BrickletVoltageCurrent.PowerReachedListener}
	 * 
	 * are triggered, if the thresholds
	 * 
	 * * {@link BrickletVoltageCurrent#setCurrentCallbackThreshold(char, int, int)},
	 * * {@link BrickletVoltageCurrent#setVoltageCallbackThreshold(char, int, int)},
	 * * {@link BrickletVoltageCurrent#setPowerCallbackThreshold(char, int, int)}
	 * 
	 * keep being reached.
	 * 
	 * The default value is 100.
	 */
	public void setDebouncePeriod(long debounce) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_DEBOUNCE_PERIOD, this);
		bb.putInt((int)debounce);

		sendRequest(bb.array());
	}

	/**
	 * Returns the debounce period as set by {@link BrickletVoltageCurrent#setDebouncePeriod(long)}.
	 */
	public long getDebouncePeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_DEBOUNCE_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long debounce = IPConnection.unsignedInt(bb.getInt());

		return debounce;
	}

	/**
	 * Returns the UID, the UID where the Bricklet is connected to, 
	 * the position, the hardware and firmware version as well as the
	 * device identifier.
	 * 
	 * The position can be 'a', 'b', 'c' or 'd'.
	 * 
	 * The device identifiers can be found :ref:`here <device_identifier>`.
	 * 
	 * .. versionadded:: 2.0.0~(Plugin)
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
	 * Adds a Current listener.
	 */
	public void addCurrentListener(CurrentListener listener) {
		listenerCurrent.add(listener);
	}

	/**
	 * Removes a Current listener.
	 */
	public void removeCurrentListener(CurrentListener listener) {
		listenerCurrent.remove(listener);
	}

	/**
	 * Adds a Voltage listener.
	 */
	public void addVoltageListener(VoltageListener listener) {
		listenerVoltage.add(listener);
	}

	/**
	 * Removes a Voltage listener.
	 */
	public void removeVoltageListener(VoltageListener listener) {
		listenerVoltage.remove(listener);
	}

	/**
	 * Adds a Power listener.
	 */
	public void addPowerListener(PowerListener listener) {
		listenerPower.add(listener);
	}

	/**
	 * Removes a Power listener.
	 */
	public void removePowerListener(PowerListener listener) {
		listenerPower.remove(listener);
	}

	/**
	 * Adds a CurrentReached listener.
	 */
	public void addCurrentReachedListener(CurrentReachedListener listener) {
		listenerCurrentReached.add(listener);
	}

	/**
	 * Removes a CurrentReached listener.
	 */
	public void removeCurrentReachedListener(CurrentReachedListener listener) {
		listenerCurrentReached.remove(listener);
	}

	/**
	 * Adds a VoltageReached listener.
	 */
	public void addVoltageReachedListener(VoltageReachedListener listener) {
		listenerVoltageReached.add(listener);
	}

	/**
	 * Removes a VoltageReached listener.
	 */
	public void removeVoltageReachedListener(VoltageReachedListener listener) {
		listenerVoltageReached.remove(listener);
	}

	/**
	 * Adds a PowerReached listener.
	 */
	public void addPowerReachedListener(PowerReachedListener listener) {
		listenerPowerReached.add(listener);
	}

	/**
	 * Removes a PowerReached listener.
	 */
	public void removePowerReachedListener(PowerReachedListener listener) {
		listenerPowerReached.remove(listener);
	}
}