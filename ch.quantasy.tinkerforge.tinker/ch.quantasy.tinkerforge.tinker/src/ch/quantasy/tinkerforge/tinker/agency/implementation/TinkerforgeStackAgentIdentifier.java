package ch.quantasy.tinkerforge.tinker.agency.implementation;

import ch.quantasy.tinkerforge.tinker.core.implementation.AbstractTinkerforgeStackManager;

public class TinkerforgeStackAgentIdentifier{
	public static final int DEFAULT_PORT=AbstractTinkerforgeStackManager.DEFAULT_PORT;
	private String hostName;
	private int port;
	public TinkerforgeStackAgentIdentifier(String host) {
		this(host,DEFAULT_PORT);
	}
	public TinkerforgeStackAgentIdentifier(String hostName, int port) {
		if(hostName==null || hostName.equals("")) throw new IllegalArgumentException();
		if(port<1||port>65535) throw new IllegalArgumentException();
		this.hostName = hostName;
		this.port = port;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public int getPort() {
		return port;
	}
	
	@Override
	public String toString() {
		return "TinkerforgeStackAgentIdentifier [hostName=" + hostName + ", port=" + port + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
		result = prime * result + port;
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
		TinkerforgeStackAgentIdentifier other = (TinkerforgeStackAgentIdentifier) obj;
		if (hostName == null) {
			if (other.hostName != null)
				return false;
		} else if (!hostName.equals(other.hostName))
			return false;
		if (port != other.port)
			return false;
		return true;
	}
	
	
}

