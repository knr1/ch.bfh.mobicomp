package ch.quantasy.tinkerforge.tinker.agency.implementation;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;
import ch.quantasy.tinkerforge.tinker.core.implementation.AbstractTinkerforgeStackManager;

import com.tinkerforge.Device;

public class TinkerforgeStackAgent extends AbstractTinkerforgeStackManager {
	public static final int DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS=1000*60;
	private int connectionTimeoutInMilliseconds;
	private Timer timer;
	private Set<TinkerforgeApplication> tinkerforgeApplications;
	private TinkerforgeStackAgentIdentifier tinkerforgeStackAgentIdentifier;
	private Exception actualConnectionException;

	public TinkerforgeStackAgent(TinkerforgeStackAgentIdentifier tinkerforgeStackAgentIdentifier) {
		super(tinkerforgeStackAgentIdentifier.getHostName(), tinkerforgeStackAgentIdentifier.getPort());
		this.connectionTimeoutInMilliseconds=DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;
		this.tinkerforgeStackAgentIdentifier = tinkerforgeStackAgentIdentifier;
		tinkerforgeApplications = new HashSet<TinkerforgeApplication>();
	}

	public int getConnectionTimeoutInMilliseconds() {
		return connectionTimeoutInMilliseconds;
	}
	
	public void setConnectionTimeoutInMilliseconds(int connectionTimeoutInMilliseconds) {
		this.connectionTimeoutInMilliseconds = connectionTimeoutInMilliseconds;
	}
	
	public Set<TinkerforgeApplication> getTinkerforgeApplications() {
		return new HashSet<TinkerforgeApplication>(tinkerforgeApplications);
	}

	public Exception getActualConnectionException() {
		return actualConnectionException;
	}

	public TinkerforgeStackAgentIdentifier getStackAgentIdentifier() {
		return tinkerforgeStackAgentIdentifier;
	}

	/**
	 * This should not be called directly but by the application itself.
	 * 
	 * @param tinkerforgeApplication
	 */
	public void addTinkerforgeApplication(TinkerforgeApplication tinkerforgeApplication) {
		if (tinkerforgeApplications.add(tinkerforgeApplication)) {
			if (super.isConnected()) {
				tinkerforgeApplication.stackConnected(this);
				for (Device device : super.getConnectedDeviceList()) {
					tinkerforgeApplication.deviceConnected(this, device);
				}
			} else {
				connect();
			}
		}
	}

	/**
	 * This should not be called directly but by the application itself.
	 * 
	 * @param tinkerforgeApplication
	 */
	public void removeTinkerforgeApplication(TinkerforgeApplication tinkerforgeApplication) {
		if (tinkerforgeApplications.contains(tinkerforgeApplication)) {
			tinkerforgeApplication.stackAgentIsDisconnecting(this);
			for (Device device : super.getConnectedDeviceList()) {
				tinkerforgeApplication.deviceIsDisconnecting(this, device);
			}
		}
		if (tinkerforgeApplications.remove(tinkerforgeApplication)) {
			tinkerforgeApplication.stackDisconnected(this);
			for (Device device : super.getConnectedDeviceList()) {
				tinkerforgeApplication.deviceDisconnected(this, device);
			}
			if (tinkerforgeApplications.isEmpty())
				super.disconnect();
		}
	}
	
	@Override
	protected void connect() {
		timer=new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					TinkerforgeStackAgent.super.connect();
					TinkerforgeStackAgent.this.actualConnectionException = null;
					timer.cancel();
				} catch (UnknownHostException e) {
					TinkerforgeStackAgent.this.actualConnectionException = e;
				} catch (IOException e) {
					TinkerforgeStackAgent.this.actualConnectionException = e;
				}
			}
		}, 0, TinkerforgeStackAgent.this.getConnectionTimeoutInMilliseconds());

	}
	
	@Override
	protected void disconnect() {
		if(timer!=null)timer.cancel();
		super.disconnect();
	}

	@Override
	protected void connected() {
		for (TinkerforgeApplication tinkerforgeApplication : tinkerforgeApplications) {
			tinkerforgeApplication.stackConnected(this);
		}

	}

	@Override
	protected void disconnected() {
		for (TinkerforgeApplication tinkerforgeApplication : tinkerforgeApplications) {
			tinkerforgeApplication.stackDisconnected(this);
		}
	}

	@Override
	protected void deviceConnected(Device device) {
		for (TinkerforgeApplication tinkerforgeApplication : tinkerforgeApplications) {
			tinkerforgeApplication.deviceConnected(this, device);
		}
	}

	@Override
	protected void deviceReConnected(Device device) {
		for (TinkerforgeApplication tinkerforgeApplication : tinkerforgeApplications) {
			tinkerforgeApplication.deviceConnected(this, device);
		}
	}

	@Override
	protected void deviceDisconnected(Device device) {
		for (TinkerforgeApplication tinkerforgeApplication : tinkerforgeApplications) {
			tinkerforgeApplication.deviceDisconnected(this, device);
		}
	}

	@Override
	public String toString() {
		return "TinkerforgeStackAgent [tinkerforgeStackAgentIdentifier=" + tinkerforgeStackAgentIdentifier + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tinkerforgeStackAgentIdentifier == null) ? 0 : tinkerforgeStackAgentIdentifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TinkerforgeStackAgent other = (TinkerforgeStackAgent) obj;
		if (tinkerforgeStackAgentIdentifier == null) {
			if (other.tinkerforgeStackAgentIdentifier != null)
				return false;
		} else if (!tinkerforgeStackAgentIdentifier.equals(other.tinkerforgeStackAgentIdentifier))
			return false;
		return true;
	}

}
