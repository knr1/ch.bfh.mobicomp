package ch.quantasy.tinkerforge.tinker.core.implementation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tinkerforge.AlreadyConnectedException;
import com.tinkerforge.Device;
import com.tinkerforge.IPConnection;
import com.tinkerforge.IPConnection.ConnectedListener;
import com.tinkerforge.IPConnection.DisconnectedListener;
import com.tinkerforge.IPConnection.EnumerateListener;
import com.tinkerforge.NotConnectedException;
import com.tinkerforge.TinkerforgeException;

/**
 * This class tries to give a helping hand in order to represent a
 * Tinkerforge-stack. It gives convenience-methods for the Java-Developer.
 * 
 * @author reto
 * 
 */
public abstract class AbstractTinkerforgeStackManager {
	public static final int DEFAULT_PORT = 4223;
	private final String host;
	private final int port;

	private final IPConnection ipConnection;
	private final Map<String, Device> deviceMap;

	private final IPConnectionHandler ipConnectionHandler;
	private final DeviceEnumerationHandler deviceEnumerationHandler;

	/**
	 * Creates a representation of a Tinkerforge-Stack. Either at 'localhost' or
	 * via a 'Server'-name given in the WLAN-Brick
	 * 
	 * @param host
	 */
	public AbstractTinkerforgeStackManager(String host) {
		this(host, DEFAULT_PORT);
	}

	/**
	 * Creates a representation of a Tinkerforge-Stack. Either at 'localhost' or
	 * via a 'Server'-name given in the WLAN-Brick
	 * 
	 * @param host
	 * @param port
	 */
	public AbstractTinkerforgeStackManager(String host, int port) {
		if (host == null || port < 0)
			throw new IllegalArgumentException();
		this.deviceMap = new HashMap<String, Device>();
		this.host = host;
		this.port = port;
		this.ipConnection = new IPConnection();
		this.ipConnectionHandler = new IPConnectionHandler(ipConnection);
		this.deviceEnumerationHandler = new DeviceEnumerationHandler(ipConnection);
	}

	/**
	 * Connects to the real Tinkerforge-Stack. It then requests the connected
	 * {@link Device}s. Therefore it waits for some time (3-seconds)
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	protected void connect() throws UnknownHostException, IOException {
		try {
			this.ipConnection.connect(host, port);
			Thread.sleep(3000);
		} catch (AlreadyConnectedException e) {
			// So what
		} catch (InterruptedException e) {
			// OK, we go on
		}
	}

	/**
	 * Disconnects from a real Tinkerforge-Stack.
	 */
	protected void disconnect() {
		try {
			this.ipConnection.disconnect();
		} catch (NotConnectedException e) {
			// So what
		}
	}
	public boolean isConnected(){
		return this.ipConnection!=null && this.ipConnection.getConnectionState()==IPConnection.CONNECTION_STATE_CONNECTED;
	}

	/**
	 * Called when the brick has been connected
	 */
	protected abstract void connected();

	/**
	 * Called when the Brick has been disconnected
	 */
	protected abstract void disconnected();

	/**
	 * Called when a new {@link Device} has been found/connected
	 * 
	 * @param device
	 */
	protected abstract void deviceConnected(Device device);

	/**
	 * Called when a {@link Device} already known to the system has been
	 * reconnected. A good strategy is to drop the old device-instance and connect
	 * the Listeners to this new instance of the {@link Device}.
	 * 
	 * @param device
	 */
	protected abstract void deviceReConnected(Device device);

	/**
	 * Called when a {@link Device} already known to the system has been
	 * disconnected.
	 * 
	 * @param device
	 */
	protected abstract void deviceDisconnected(Device device);

	public boolean isConnected(Device device) {
		if (device == null)
			throw new IllegalArgumentException();
		try {
			device.getIdentity();
			return true;
		} catch (TinkerforgeException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns the most recent list of all valid connected {@link Device}s.
	 * 
	 * @return
	 */
	public List<Device> getConnectedDeviceList() {
		return new ArrayList<Device>(deviceMap.values());
	}

	private class IPConnectionHandler implements ConnectedListener, DisconnectedListener {
		private IPConnection connection;

		public IPConnectionHandler(IPConnection connection) {
			this.connection = connection;
			connection.addConnectedListener(this);
			connection.addDisconnectedListener(this);
		}

		@Override
		public void disconnected(short disconnectReason) {
			System.out.println(disconnectReason);
			AbstractTinkerforgeStackManager.this.disconnected();

		}

		@Override
		public void connected(short disconnectReason) {
			try {
				connection.enumerate();
				AbstractTinkerforgeStackManager.this.connected();
			} catch (NotConnectedException ex) {
				// Well, this should not happen?!
				// But will treat it gracefully.
				ex.printStackTrace();
			}
		}
	}

	private class DeviceEnumerationHandler implements EnumerateListener {
		private IPConnection connection;

		public DeviceEnumerationHandler(IPConnection connection) {
			this.connection = connection;
			this.connection.addEnumerateListener(this);
		}

		@Override
		public void enumerate(String uid, String connectedUid, char position, short[] hardwareVersion,
				short[] firmwareVersion, int deviceIdentifier, short enumerationType) {
			createDevice(deviceIdentifier, uid);
			switch (enumerationType) {
			case 0:
				deviceConnected(deviceMap.get(uid));
				break;
			case 1:
				deviceReConnected(deviceMap.get(uid));
				break;
			case 2:
				deviceDisconnected(deviceMap.get(uid));
				break;
			}

		}

		private void createDevice(int deviceIdentifier, String uid) {
			if (!deviceMap.containsKey(uid)) {
				try {
					Device device = (Device) TinkerforgeDevice.getDevice(deviceIdentifier).device.getDeclaredConstructor(
							String.class, IPConnection.class).newInstance(uid, ipConnection);
					device.getIdentity();
					deviceMap.put(uid, device);
				} catch (TinkerforgeException ex) {
					ex.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

}
