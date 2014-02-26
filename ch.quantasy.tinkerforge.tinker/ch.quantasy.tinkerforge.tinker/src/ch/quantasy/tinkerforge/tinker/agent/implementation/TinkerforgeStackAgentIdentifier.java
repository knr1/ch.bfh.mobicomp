package ch.quantasy.tinkerforge.tinker.agent.implementation;

import ch.quantasy.tinkerforge.tinker.core.implementation.AbstractTinkerforgeStackManager;

public class TinkerforgeStackAgentIdentifier {
	public static final int DEFAULT_PORT = AbstractTinkerforgeStackManager.DEFAULT_PORT;
	private final String hostName;
	private final int port;

	public TinkerforgeStackAgentIdentifier(final String host) {
		this(host, TinkerforgeStackAgentIdentifier.DEFAULT_PORT);
	}

	public TinkerforgeStackAgentIdentifier(final String hostName, final int port) {
		if ((hostName == null) || hostName.equals("")) {
			throw new IllegalArgumentException();
		}
		if ((port < 1) || (port > 65535)) {
			throw new IllegalArgumentException();
		}
		this.hostName = hostName;
		this.port = port;
	}

	public String getHostName() {
		return this.hostName;
	}

	public int getPort() {
		return this.port;
	}

	@Override
	public String toString() {
		return "TinkerforgeStackAgentIdentifier [hostName=" + this.hostName + ", port=" + this.port + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.hostName == null) ? 0 : this.hostName.hashCode());
		result = (prime * result) + this.port;
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
		final TinkerforgeStackAgentIdentifier other = (TinkerforgeStackAgentIdentifier) obj;
		if (this.hostName == null) {
			if (other.hostName != null) {
				return false;
			}
		} else if (!this.hostName.equals(other.hostName)) {
			return false;
		}
		if (this.port != other.port) {
			return false;
		}
		return true;
	}

}
