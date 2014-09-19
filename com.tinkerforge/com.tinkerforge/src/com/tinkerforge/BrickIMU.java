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
 * Device for sensing acceleration, magnetic field and angular velocity
 */
public class BrickIMU extends Device {
	public final static int DEVICE_IDENTIFIER = 16;

	public final static byte FUNCTION_GET_ACCELERATION = (byte)1;
	public final static byte FUNCTION_GET_MAGNETIC_FIELD = (byte)2;
	public final static byte FUNCTION_GET_ANGULAR_VELOCITY = (byte)3;
	public final static byte FUNCTION_GET_ALL_DATA = (byte)4;
	public final static byte FUNCTION_GET_ORIENTATION = (byte)5;
	public final static byte FUNCTION_GET_QUATERNION = (byte)6;
	public final static byte FUNCTION_GET_IMU_TEMPERATURE = (byte)7;
	public final static byte FUNCTION_LEDS_ON = (byte)8;
	public final static byte FUNCTION_LEDS_OFF = (byte)9;
	public final static byte FUNCTION_ARE_LEDS_ON = (byte)10;
	public final static byte FUNCTION_SET_ACCELERATION_RANGE = (byte)11;
	public final static byte FUNCTION_GET_ACCELERATION_RANGE = (byte)12;
	public final static byte FUNCTION_SET_MAGNETOMETER_RANGE = (byte)13;
	public final static byte FUNCTION_GET_MAGNETOMETER_RANGE = (byte)14;
	public final static byte FUNCTION_SET_CONVERGENCE_SPEED = (byte)15;
	public final static byte FUNCTION_GET_CONVERGENCE_SPEED = (byte)16;
	public final static byte FUNCTION_SET_CALIBRATION = (byte)17;
	public final static byte FUNCTION_GET_CALIBRATION = (byte)18;
	public final static byte FUNCTION_SET_ACCELERATION_PERIOD = (byte)19;
	public final static byte FUNCTION_GET_ACCELERATION_PERIOD = (byte)20;
	public final static byte FUNCTION_SET_MAGNETIC_FIELD_PERIOD = (byte)21;
	public final static byte FUNCTION_GET_MAGNETIC_FIELD_PERIOD = (byte)22;
	public final static byte FUNCTION_SET_ANGULAR_VELOCITY_PERIOD = (byte)23;
	public final static byte FUNCTION_GET_ANGULAR_VELOCITY_PERIOD = (byte)24;
	public final static byte FUNCTION_SET_ALL_DATA_PERIOD = (byte)25;
	public final static byte FUNCTION_GET_ALL_DATA_PERIOD = (byte)26;
	public final static byte FUNCTION_SET_ORIENTATION_PERIOD = (byte)27;
	public final static byte FUNCTION_GET_ORIENTATION_PERIOD = (byte)28;
	public final static byte FUNCTION_SET_QUATERNION_PERIOD = (byte)29;
	public final static byte FUNCTION_GET_QUATERNION_PERIOD = (byte)30;
	public final static byte CALLBACK_ACCELERATION = (byte)31;
	public final static byte CALLBACK_MAGNETIC_FIELD = (byte)32;
	public final static byte CALLBACK_ANGULAR_VELOCITY = (byte)33;
	public final static byte CALLBACK_ALL_DATA = (byte)34;
	public final static byte CALLBACK_ORIENTATION = (byte)35;
	public final static byte CALLBACK_QUATERNION = (byte)36;
	public final static byte FUNCTION_ORIENTATION_CALCULATION_ON = (byte)37;
	public final static byte FUNCTION_ORIENTATION_CALCULATION_OFF = (byte)38;
	public final static byte FUNCTION_IS_ORIENTATION_CALCULATION_ON = (byte)39;
	public final static byte FUNCTION_GET_PROTOCOL1_BRICKLET_NAME = (byte)241;
	public final static byte FUNCTION_GET_CHIP_TEMPERATURE = (byte)242;
	public final static byte FUNCTION_RESET = (byte)243;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;

	public final static short CALIBRATION_TYPE_ACCELEROMETER_GAIN = (short)0;
	public final static short CALIBRATION_TYPE_ACCELEROMETER_BIAS = (short)1;
	public final static short CALIBRATION_TYPE_MAGNETOMETER_GAIN = (short)2;
	public final static short CALIBRATION_TYPE_MAGNETOMETER_BIAS = (short)3;
	public final static short CALIBRATION_TYPE_GYROSCOPE_GAIN = (short)4;
	public final static short CALIBRATION_TYPE_GYROSCOPE_BIAS = (short)5;

	private List<AccelerationListener> listenerAcceleration = new CopyOnWriteArrayList<AccelerationListener>();
	private List<MagneticFieldListener> listenerMagneticField = new CopyOnWriteArrayList<MagneticFieldListener>();
	private List<AngularVelocityListener> listenerAngularVelocity = new CopyOnWriteArrayList<AngularVelocityListener>();
	private List<AllDataListener> listenerAllData = new CopyOnWriteArrayList<AllDataListener>();
	private List<OrientationListener> listenerOrientation = new CopyOnWriteArrayList<OrientationListener>();
	private List<QuaternionListener> listenerQuaternion = new CopyOnWriteArrayList<QuaternionListener>();

	public class Acceleration {
		public short x;
		public short y;
		public short z;

		public String toString() {
			return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class MagneticField {
		public short x;
		public short y;
		public short z;

		public String toString() {
			return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class AngularVelocity {
		public short x;
		public short y;
		public short z;

		public String toString() {
			return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + "]";
		}
	}

	public class AllData {
		public short accX;
		public short accY;
		public short accZ;
		public short magX;
		public short magY;
		public short magZ;
		public short angX;
		public short angY;
		public short angZ;
		public short temperature;

		public String toString() {
			return "[" + "accX = " + accX + ", " + "accY = " + accY + ", " + "accZ = " + accZ + ", " + "magX = " + magX + ", " + "magY = " + magY + ", " + "magZ = " + magZ + ", " + "angX = " + angX + ", " + "angY = " + angY + ", " + "angZ = " + angZ + ", " + "temperature = " + temperature + "]";
		}
	}

	public class Orientation {
		public short roll;
		public short pitch;
		public short yaw;

		public String toString() {
			return "[" + "roll = " + roll + ", " + "pitch = " + pitch + ", " + "yaw = " + yaw + "]";
		}
	}

	public class Quaternion {
		public float x;
		public float y;
		public float z;
		public float w;

		public String toString() {
			return "[" + "x = " + x + ", " + "y = " + y + ", " + "z = " + z + ", " + "w = " + w + "]";
		}
	}

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
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickIMU#setAccelerationPeriod(long)}. The parameters are the acceleration
	 * for the x, y and z axis.
	 */
	public interface AccelerationListener {
		public void acceleration(short x, short y, short z);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickIMU#setMagneticFieldPeriod(long)}. The parameters are the magnetic field
	 * for the x, y and z axis.
	 */
	public interface MagneticFieldListener {
		public void magneticField(short x, short y, short z);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickIMU#setAngularVelocityPeriod(long)}. The parameters are the angular velocity
	 * for the x, y and z axis.
	 */
	public interface AngularVelocityListener {
		public void angularVelocity(short x, short y, short z);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickIMU#setAllDataPeriod(long)}. The parameters are the acceleration,
	 * the magnetic field and the angular velocity for the x, y and z axis as
	 * well as the temperature of the IMU Brick.
	 */
	public interface AllDataListener {
		public void allData(short accX, short accY, short accZ, short magX, short magY, short magZ, short angX, short angY, short angZ, short temperature);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickIMU#setOrientationPeriod(long)}. The parameters are the orientation
	 * (roll, pitch and yaw) of the IMU Brick in Euler angles. See
	 * {@link BrickIMU#getOrientation()} for details.
	 */
	public interface OrientationListener {
		public void orientation(short roll, short pitch, short yaw);
	}

	/**
	 * This listener is triggered periodically with the period that is set by
	 * {@link BrickIMU#setQuaternionPeriod(long)}. The parameters are the orientation
	 * (x, y, z, w) of the IMU Brick in quaternions. See {@link BrickIMU#getQuaternion()}
	 * for details.
	 */
	public interface QuaternionListener {
		public void quaternion(float x, float y, float z, float w);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickIMU(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 1;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ACCELERATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MAGNETIC_FIELD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANGULAR_VELOCITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALL_DATA)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ORIENTATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_QUATERNION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IMU_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_LEDS_ON)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_LEDS_OFF)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_ARE_LEDS_ON)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ACCELERATION_RANGE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ACCELERATION_RANGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MAGNETOMETER_RANGE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MAGNETOMETER_RANGE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CONVERGENCE_SPEED)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CONVERGENCE_SPEED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CALIBRATION)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CALIBRATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ACCELERATION_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ACCELERATION_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_MAGNETIC_FIELD_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_MAGNETIC_FIELD_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ANGULAR_VELOCITY_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ANGULAR_VELOCITY_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ALL_DATA_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ALL_DATA_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_ORIENTATION_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_ORIENTATION_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_QUATERNION_PERIOD)] = RESPONSE_EXPECTED_FLAG_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_QUATERNION_PERIOD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_ORIENTATION_CALCULATION_ON)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_ORIENTATION_CALCULATION_OFF)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_ORIENTATION_CALCULATION_ON)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_PROTOCOL1_BRICKLET_NAME)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CHIP_TEMPERATURE)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_RESET)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ACCELERATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_MAGNETIC_FIELD)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ANGULAR_VELOCITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ALL_DATA)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_ORIENTATION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_QUATERNION)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_ACCELERATION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				for(AccelerationListener listener: listenerAcceleration) {
					listener.acceleration(x, y, z);
				}
			}
		};

		callbacks[CALLBACK_MAGNETIC_FIELD] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				for(MagneticFieldListener listener: listenerMagneticField) {
					listener.magneticField(x, y, z);
				}
			}
		};

		callbacks[CALLBACK_ANGULAR_VELOCITY] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short x = (bb.getShort());
				short y = (bb.getShort());
				short z = (bb.getShort());

				for(AngularVelocityListener listener: listenerAngularVelocity) {
					listener.angularVelocity(x, y, z);
				}
			}
		};

		callbacks[CALLBACK_ALL_DATA] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short accX = (bb.getShort());
				short accY = (bb.getShort());
				short accZ = (bb.getShort());
				short magX = (bb.getShort());
				short magY = (bb.getShort());
				short magZ = (bb.getShort());
				short angX = (bb.getShort());
				short angY = (bb.getShort());
				short angZ = (bb.getShort());
				short temperature = (bb.getShort());

				for(AllDataListener listener: listenerAllData) {
					listener.allData(accX, accY, accZ, magX, magY, magZ, angX, angY, angZ, temperature);
				}
			}
		};

		callbacks[CALLBACK_ORIENTATION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short roll = (bb.getShort());
				short pitch = (bb.getShort());
				short yaw = (bb.getShort());

				for(OrientationListener listener: listenerOrientation) {
					listener.orientation(roll, pitch, yaw);
				}
			}
		};

		callbacks[CALLBACK_QUATERNION] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				float x = (bb.getFloat());
				float y = (bb.getFloat());
				float z = (bb.getFloat());
				float w = (bb.getFloat());

				for(QuaternionListener listener: listenerQuaternion) {
					listener.quaternion(x, y, z, w);
				}
			}
		};
	}

	/**
	 * Returns the calibrated acceleration from the accelerometer for the 
	 * x, y and z axis in mG (G/1000, 1G = 9.80605m/s²).
	 * 
	 * If you want to get the acceleration periodically, it is recommended 
	 * to use the listener {@link BrickIMU.AccelerationListener} and set the period with 
	 * {@link BrickIMU#setAccelerationPeriod(long)}.
	 */
	public Acceleration getAcceleration() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ACCELERATION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Acceleration obj = new Acceleration();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());
		obj.z = (bb.getShort());

		return obj;
	}

	/**
	 * Returns the calibrated magnetic field from the magnetometer for the 
	 * x, y and z axis in mG (Milligauss or Nanotesla).
	 * 
	 * If you want to get the magnetic field periodically, it is recommended 
	 * to use the listener {@link BrickIMU.MagneticFieldListener} and set the period with 
	 * {@link BrickIMU#setMagneticFieldPeriod(long)}.
	 */
	public MagneticField getMagneticField() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MAGNETIC_FIELD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		MagneticField obj = new MagneticField();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());
		obj.z = (bb.getShort());

		return obj;
	}

	/**
	 * Returns the calibrated angular velocity from the gyroscope for the 
	 * x, y and z axis in °/17.5s (you have to divide by 17.5 to
	 * get the value in °/s).
	 * 
	 * If you want to get the angular velocity periodically, it is recommended 
	 * to use the listener {@link BrickIMU.AngularVelocityListener} and set the period with 
	 * {@link BrickIMU#setAngularVelocityPeriod(long)}.
	 */
	public AngularVelocity getAngularVelocity() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANGULAR_VELOCITY, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AngularVelocity obj = new AngularVelocity();
		obj.x = (bb.getShort());
		obj.y = (bb.getShort());
		obj.z = (bb.getShort());

		return obj;
	}

	/**
	 * Returns the data from {@link BrickIMU#getAcceleration()}, {@link BrickIMU#getMagneticField()} 
	 * and {@link BrickIMU#getAngularVelocity()} as well as the temperature of the IMU Brick.
	 * 
	 * The temperature is given in °C/100.
	 * 
	 * If you want to get the data periodically, it is recommended 
	 * to use the listener {@link BrickIMU.AllDataListener} and set the period with 
	 * {@link BrickIMU#setAllDataPeriod(long)}.
	 */
	public AllData getAllData() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ALL_DATA, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		AllData obj = new AllData();
		obj.accX = (bb.getShort());
		obj.accY = (bb.getShort());
		obj.accZ = (bb.getShort());
		obj.magX = (bb.getShort());
		obj.magY = (bb.getShort());
		obj.magZ = (bb.getShort());
		obj.angX = (bb.getShort());
		obj.angY = (bb.getShort());
		obj.angZ = (bb.getShort());
		obj.temperature = (bb.getShort());

		return obj;
	}

	/**
	 * Returns the current orientation (roll, pitch, yaw) of the IMU Brick as Euler
	 * angles in one-hundredth degree. Note that Euler angles always experience a
	 * `gimbal lock <http://en.wikipedia.org/wiki/Gimbal_lock>`__.
	 * 
	 * We recommend that you use quaternions instead.
	 * 
	 * The order to sequence in which the orientation values should be applied is 
	 * roll, yaw, pitch. 
	 * 
	 * If you want to get the orientation periodically, it is recommended 
	 * to use the listener {@link BrickIMU.OrientationListener} and set the period with 
	 * {@link BrickIMU#setOrientationPeriod(long)}.
	 */
	public Orientation getOrientation() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ORIENTATION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Orientation obj = new Orientation();
		obj.roll = (bb.getShort());
		obj.pitch = (bb.getShort());
		obj.yaw = (bb.getShort());

		return obj;
	}

	/**
	 * Returns the current orientation (x, y, z, w) of the IMU as 
	 * `quaternions <http://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation>`__.
	 * 
	 * You can go from quaternions to Euler angles with the following formula::
	 * 
	 *  roll  = atan2(2*y*w - 2*x*z, 1 - 2*y*y - 2*z*z)
	 *  pitch = atan2(2*x*w - 2*y*z, 1 - 2*x*x - 2*z*z)
	 *  yaw   =  asin(2*x*y + 2*z*w)
	 * 
	 * This process is not reversible, because of the 
	 * `gimbal lock <http://en.wikipedia.org/wiki/Gimbal_lock>`__.
	 * 
	 * Converting the quaternions to an OpenGL transformation matrix is
	 * possible with the following formula::
	 * 
	 *  matrix = [[1 - 2*(y*y + z*z),     2*(x*y - w*z),     2*(x*z + w*y), 0],
	 *            [    2*(x*y + w*z), 1 - 2*(x*x + z*z),     2*(y*z - w*x), 0],
	 *            [    2*(x*z - w*y),     2*(y*z + w*x), 1 - 2*(x*x + y*y), 0],
	 *            [                0,                 0,                 0, 1]]
	 * 
	 * If you want to get the quaternions periodically, it is recommended 
	 * to use the listener {@link BrickIMU.QuaternionListener} and set the period with 
	 * {@link BrickIMU#setQuaternionPeriod(long)}.
	 */
	public Quaternion getQuaternion() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_QUATERNION, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Quaternion obj = new Quaternion();
		obj.x = (bb.getFloat());
		obj.y = (bb.getFloat());
		obj.z = (bb.getFloat());
		obj.w = (bb.getFloat());

		return obj;
	}

	/**
	 * Returns the temperature of the IMU Brick. The temperature is given in 
	 * °C/100.
	 */
	public short getIMUTemperature() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_IMU_TEMPERATURE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short temperature = (bb.getShort());

		return temperature;
	}

	/**
	 * Turns the orientation and direction LEDs of the IMU Brick on.
	 */
	public void ledsOn() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_LEDS_ON, this);

		sendRequest(bb.array());
	}

	/**
	 * Turns the orientation and direction LEDs of the IMU Brick off.
	 */
	public void ledsOff() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_LEDS_OFF, this);

		sendRequest(bb.array());
	}

	/**
	 * Returns *true* if the orientation and direction LEDs of the IMU Brick
	 * are on, *false* otherwise.
	 */
	public boolean areLedsOn() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_ARE_LEDS_ON, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean leds = (bb.get()) != 0;

		return leds;
	}

	/**
	 * Not implemented yet.
	 */
	public void setAccelerationRange(short range) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_ACCELERATION_RANGE, this);
		bb.put((byte)range);

		sendRequest(bb.array());
	}

	/**
	 * Not implemented yet.
	 */
	public short getAccelerationRange() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ACCELERATION_RANGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short range = IPConnection.unsignedByte(bb.get());

		return range;
	}

	/**
	 * Not implemented yet.
	 */
	public void setMagnetometerRange(short range) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_SET_MAGNETOMETER_RANGE, this);
		bb.put((byte)range);

		sendRequest(bb.array());
	}

	/**
	 * Not implemented yet.
	 */
	public short getMagnetometerRange() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MAGNETOMETER_RANGE, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short range = IPConnection.unsignedByte(bb.get());

		return range;
	}

	/**
	 * Sets the convergence speed of the IMU Brick in °/s. The convergence speed 
	 * determines how the different sensor measurements are fused.
	 * 
	 * If the orientation of the IMU Brick is off by 10° and the convergence speed is 
	 * set to 20°/s, it will take 0.5s until the orientation is corrected. However,
	 * if the correct orientation is reached and the convergence speed is too high,
	 * the orientation will fluctuate with the fluctuations of the accelerometer and
	 * the magnetometer.
	 * 
	 * If you set the convergence speed to 0, practically only the gyroscope is used
	 * to calculate the orientation. This gives very smooth movements, but errors of the
	 * gyroscope will not be corrected. If you set the convergence speed to something
	 * above 500, practically only the magnetometer and the accelerometer are used to
	 * calculate the orientation. In this case the movements are abrupt and the values
	 * will fluctuate, but there won't be any errors that accumulate over time.
	 * 
	 * In an application with high angular velocities, we recommend a high convergence
	 * speed, so the errors of the gyroscope can be corrected fast. In applications with
	 * only slow movements we recommend a low convergence speed. You can change the
	 * convergence speed on the fly. So it is possible (and recommended) to increase 
	 * the convergence speed before an abrupt movement and decrease it afterwards 
	 * again.
	 * 
	 * You might want to play around with the convergence speed in the Brick Viewer to
	 * get a feeling for a good value for your application.
	 * 
	 * The default value is 30.
	 */
	public void setConvergenceSpeed(int speed) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)10, FUNCTION_SET_CONVERGENCE_SPEED, this);
		bb.putShort((short)speed);

		sendRequest(bb.array());
	}

	/**
	 * Returns the convergence speed as set by {@link BrickIMU#setConvergenceSpeed(int)}.
	 */
	public int getConvergenceSpeed() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_CONVERGENCE_SPEED, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		int speed = IPConnection.unsignedShort(bb.getShort());

		return speed;
	}

	/**
	 * There are several different types that can be calibrated:
	 * 
	 * \verbatim
	 *  "Type", "Description", "Values"
	 * 
	 *  "0",    "Accelerometer Gain", "``[mul x, mul y, mul z, div x, div y, div z, 0, 0, 0, 0]``"
	 *  "1",    "Accelerometer Bias", "``[bias x, bias y, bias z, 0, 0, 0, 0, 0, 0, 0]``"
	 *  "2",    "Magnetometer Gain",  "``[mul x, mul y, mul z, div x, div y, div z, 0, 0, 0, 0]``"
	 *  "3",    "Magnetometer Bias",  "``[bias x, bias y, bias z, 0, 0, 0, 0, 0, 0, 0]``"
	 *  "4",    "Gyroscope Gain",     "``[mul x, mul y, mul z, div x, div y, div z, 0, 0, 0, 0]``"
	 *  "5",    "Gyroscope Bias",     "``[bias xl, bias yl, bias zl, temp l, bias xh, bias yh, bias zh, temp h, 0, 0]``"
	 * \endverbatim
	 * 
	 * The calibration via gain and bias is done with the following formula::
	 * 
	 *  new_value = (bias + orig_value) * gain_mul / gain_div
	 * 
	 * If you really want to write your own calibration software, please keep
	 * in mind that you first have to undo the old calibration (set bias to 0 and
	 * gain to 1/1) and that you have to average over several thousand values
	 * to obtain a usable result in the end.
	 * 
	 * The gyroscope bias is highly dependent on the temperature, so you have to
	 * calibrate the bias two times with different temperatures. The values ``xl``,
	 * ``yl``, ``zl `` and ``temp l`` are the bias for ``x``, ``y``, ``z`` and the
	 * corresponding temperature for a low temperature. The values ``xh``, ``yh``,
	 * ``zh`` and ``temp h`` are the same for a high temperatures. The temperature
	 * difference should be at least 5°C. If you have a temperature where the
	 * IMU Brick is mostly used, you should use this temperature for one of the
	 * sampling points.
	 * 
	 * \note
	 *  We highly recommend that you use the Brick Viewer to calibrate your
	 *  IMU Brick.
	 */
	public void setCalibration(short typ, short[] data) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)29, FUNCTION_SET_CALIBRATION, this);
		bb.put((byte)typ);
		for(int i = 0; i < 10; i++) {
			bb.putShort(data[i]);
		}


		sendRequest(bb.array());
	}

	/**
	 * Returns the calibration for a given type as set by {@link BrickIMU#setCalibration(short, short[])}.
	 */
	public short[] getCalibration(short typ) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)9, FUNCTION_GET_CALIBRATION, this);
		bb.put((byte)typ);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short[] data = new short[10];
		for(int i = 0; i < 10; i++) {
			data[i] = (bb.getShort());
		}


		return data;
	}

	/**
	 * Sets the period in ms with which the {@link BrickIMU.AccelerationListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 * 
	 * The default value is 0.
	 */
	public void setAccelerationPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_ACCELERATION_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickIMU#setAccelerationPeriod(long)}.
	 */
	public long getAccelerationPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ACCELERATION_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickIMU.MagneticFieldListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 */
	public void setMagneticFieldPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_MAGNETIC_FIELD_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickIMU#setMagneticFieldPeriod(long)}.
	 */
	public long getMagneticFieldPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_MAGNETIC_FIELD_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickIMU.AngularVelocityListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 */
	public void setAngularVelocityPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_ANGULAR_VELOCITY_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickIMU#setAngularVelocityPeriod(long)}.
	 */
	public long getAngularVelocityPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ANGULAR_VELOCITY_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickIMU.AllDataListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 */
	public void setAllDataPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_ALL_DATA_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickIMU#setAllDataPeriod(long)}.
	 */
	public long getAllDataPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ALL_DATA_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickIMU.OrientationListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 */
	public void setOrientationPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_ORIENTATION_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickIMU#setOrientationPeriod(long)}.
	 */
	public long getOrientationPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_ORIENTATION_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Sets the period in ms with which the {@link BrickIMU.QuaternionListener} listener is triggered
	 * periodically. A value of 0 turns the listener off.
	 */
	public void setQuaternionPeriod(long period) throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)12, FUNCTION_SET_QUATERNION_PERIOD, this);
		bb.putInt((int)period);

		sendRequest(bb.array());
	}

	/**
	 * Returns the period as set by {@link BrickIMU#setQuaternionPeriod(long)}.
	 */
	public long getQuaternionPeriod() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_GET_QUATERNION_PERIOD, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		long period = IPConnection.unsignedInt(bb.getInt());

		return period;
	}

	/**
	 * Turns the orientation calculation of the IMU Brick on.
	 * 
	 * As default the calculation is on.
	 * 
	 * .. versionadded:: 2.0.2~(Firmware)
	 */
	public void orientationCalculationOn() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_ORIENTATION_CALCULATION_ON, this);

		sendRequest(bb.array());
	}

	/**
	 * Turns the orientation calculation of the IMU Brick off.
	 * 
	 * If the calculation is off, {@link BrickIMU#getOrientation()} will return
	 * the last calculated value until the calculation is turned on again.
	 * 
	 * The trigonometric functions that are needed to calculate the orientation 
	 * are very expensive. We recommend to turn the orientation calculation
	 * off if the orientation is not needed, to free calculation time for the
	 * sensor fusion algorithm.
	 * 
	 * As default the calculation is on.
	 * 
	 * .. versionadded:: 2.0.2~(Firmware)
	 */
	public void orientationCalculationOff() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_ORIENTATION_CALCULATION_OFF, this);

		sendRequest(bb.array());
	}

	/**
	 * Returns *true* if the orientation calculation of the IMU Brick
	 * is on, *false* otherwise.
	 * 
	 * .. versionadded:: 2.0.2~(Firmware)
	 */
	public boolean isOrientationCalculationOn() throws TimeoutException, NotConnectedException {
		ByteBuffer bb = ipcon.createRequestPacket((byte)8, FUNCTION_IS_ORIENTATION_CALCULATION_ON, this);

		byte[] response = sendRequest(bb.array());

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean orientationCalculationOn = (bb.get()) != 0;

		return orientationCalculationOn;
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
	 * .. versionadded:: 1.0.7~(Firmware)
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
	 * .. versionadded:: 1.0.7~(Firmware)
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
	 * Adds a Acceleration listener.
	 */
	public void addAccelerationListener(AccelerationListener listener) {
		listenerAcceleration.add(listener);
	}

	/**
	 * Removes a Acceleration listener.
	 */
	public void removeAccelerationListener(AccelerationListener listener) {
		listenerAcceleration.remove(listener);
	}

	/**
	 * Adds a MagneticField listener.
	 */
	public void addMagneticFieldListener(MagneticFieldListener listener) {
		listenerMagneticField.add(listener);
	}

	/**
	 * Removes a MagneticField listener.
	 */
	public void removeMagneticFieldListener(MagneticFieldListener listener) {
		listenerMagneticField.remove(listener);
	}

	/**
	 * Adds a AngularVelocity listener.
	 */
	public void addAngularVelocityListener(AngularVelocityListener listener) {
		listenerAngularVelocity.add(listener);
	}

	/**
	 * Removes a AngularVelocity listener.
	 */
	public void removeAngularVelocityListener(AngularVelocityListener listener) {
		listenerAngularVelocity.remove(listener);
	}

	/**
	 * Adds a AllData listener.
	 */
	public void addAllDataListener(AllDataListener listener) {
		listenerAllData.add(listener);
	}

	/**
	 * Removes a AllData listener.
	 */
	public void removeAllDataListener(AllDataListener listener) {
		listenerAllData.remove(listener);
	}

	/**
	 * Adds a Orientation listener.
	 */
	public void addOrientationListener(OrientationListener listener) {
		listenerOrientation.add(listener);
	}

	/**
	 * Removes a Orientation listener.
	 */
	public void removeOrientationListener(OrientationListener listener) {
		listenerOrientation.remove(listener);
	}

	/**
	 * Adds a Quaternion listener.
	 */
	public void addQuaternionListener(QuaternionListener listener) {
		listenerQuaternion.add(listener);
	}

	/**
	 * Removes a Quaternion listener.
	 */
	public void removeQuaternionListener(QuaternionListener listener) {
		listenerQuaternion.remove(listener);
	}
}