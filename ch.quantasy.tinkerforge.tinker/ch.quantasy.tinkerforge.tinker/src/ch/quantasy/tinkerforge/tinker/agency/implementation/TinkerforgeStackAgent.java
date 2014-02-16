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
	public static final int DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS = 1000 * 60;
	private int connectionTimeoutInMilliseconds;
	private Timer timer;
	private final Set<TinkerforgeApplication> tinkerforgeApplications;
	private final TinkerforgeStackAgentIdentifier tinkerforgeStackAgentIdentifier;
	private Exception actualConnectionException;

	public TinkerforgeStackAgent(final TinkerforgeStackAgentIdentifier tinkerforgeStackAgentIdentifier) {
		super(tinkerforgeStackAgentIdentifier.getHostName(), tinkerforgeStackAgentIdentifier.getPort());
		this.connectionTimeoutInMilliseconds = TinkerforgeStackAgent.DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;
		this.tinkerforgeStackAgentIdentifier = tinkerforgeStackAgentIdentifier;
		this.tinkerforgeApplications = new HashSet<TinkerforgeApplication>();
	}

	public int getConnectionTimeoutInMilliseconds() {
		return this.connectionTimeoutInMilliseconds;
	}

	public void setConnectionTimeoutInMilliseconds(final int connectionTimeoutInMilliseconds) {
		this.connectionTimeoutInMilliseconds = connectionTimeoutInMilliseconds;
	}

	public Set<TinkerforgeApplication> getTinkerforgeApplications() {
		return new HashSet<TinkerforgeApplication>(this.tinkerforgeApplications);
	}

	public Exception getActualConnectionException() {
		return this.actualConnectionException;
	}

	public TinkerforgeStackAgentIdentifier getStackAgentIdentifier() {
		return this.tinkerforgeStackAgentIdentifier;
	}

	/**
	 * This should not be called directly but by the application itself.
	 * 
	 * @param tinkerforgeApplication
	 */
	public void addTinkerforgeApplication(final TinkerforgeApplication tinkerforgeApplication) {
		if (this.tinkerforgeApplications.add(tinkerforgeApplication)) {
			if (super.isConnected()) {
				tinkerforgeApplication.stackConnected(this);
				for (final Device device : super.getConnectedDeviceList()) {
					tinkerforgeApplication.deviceConnected(this, device);
				}
			} else {
				this.connect();
			}
		}
	}

	/**
	 * This should not be called directly but by the application itself.
	 * 
	 * @param tinkerforgeApplication
	 */
	public void removeTinkerforgeApplication(final TinkerforgeApplication tinkerforgeApplication) {
		if (this.tinkerforgeApplications.contains(tinkerforgeApplication)) {
			tinkerforgeApplication.stackAgentIsDisconnecting(this);
			for (final Device device : super.getConnectedDeviceList()) {
				tinkerforgeApplication.deviceIsDisconnecting(this, device);
			}
		}
		if (this.tinkerforgeApplications.remove(tinkerforgeApplication)) {
			tinkerforgeApplication.stackDisconnected(this);
			for (final Device device : super.getConnectedDeviceList()) {
				tinkerforgeApplication.deviceDisconnected(this, device);
			}
			if (this.tinkerforgeApplications.isEmpty()) {
				super.disconnect();
			}
		}
	}

	@Override
	protected void connect() {
		this.timer = new Timer(true);
		this.timer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					TinkerforgeStackAgent.super.connect();
					TinkerforgeStackAgent.this.actualConnectionException = null;
					TinkerforgeStackAgent.this.timer.cancel();
				} catch (final UnknownHostException e) {
					TinkerforgeStackAgent.this.actualConnectionException = e;
				} catch (final IOException e) {
					TinkerforgeStackAgent.this.actualConnectionException = e;
				}
			}
		}, 0, TinkerforgeStackAgent.this.getConnectionTimeoutInMilliseconds());

	}

	@Override
	protected void disconnect() {
		if (this.timer != null) {
			this.timer.cancel();
		}
		super.disconnect();
	}

	@Override
	protected void connected() {
		for (final TinkerforgeApplication tinkerforgeApplication : this.tinkerforgeApplications) {
			tinkerforgeApplication.stackConnected(this);
		}

	}

	@Override
	protected void disconnected() {
		for (final TinkerforgeApplication tinkerforgeApplication : this.tinkerforgeApplications) {
			tinkerforgeApplication.stackDisconnected(this);
		}
	}

	@Override
	protected void deviceConnected(final Device device) {
		for (final TinkerforgeApplication tinkerforgeApplication : this.tinkerforgeApplications) {
			tinkerforgeApplication.deviceConnected(this, device);
		}
	}

	@Override
	protected void deviceReConnected(final Device device) {
		for (final TinkerforgeApplication tinkerforgeApplication : this.tinkerforgeApplications) {
			tinkerforgeApplication.deviceConnected(this, device);
		}
	}

	@Override
	protected void deviceDisconnected(final Device device) {
		for (final TinkerforgeApplication tinkerforgeApplication : this.tinkerforgeApplications) {
			tinkerforgeApplication.deviceDisconnected(this, device);
		}
	}

	@Override
	public String toString() {
		return "TinkerforgeStackAgent [tinkerforgeStackAgentIdentifier=" + this.tinkerforgeStackAgentIdentifier + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.tinkerforgeStackAgentIdentifier == null) ? 0 : this.tinkerforgeStackAgentIdentifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final TinkerforgeStackAgent other = (TinkerforgeStackAgent) obj;
		if (this.tinkerforgeStackAgentIdentifier == null) {
			if (other.tinkerforgeStackAgentIdentifier != null) {
				return false;
			}
		} else if (!this.tinkerforgeStackAgentIdentifier.equals(other.tinkerforgeStackAgentIdentifier)) {
			return false;
		}
		return true;
	}

}
