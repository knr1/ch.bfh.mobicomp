package ch.quantasy.communication.channel.implementation.udp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import ch.quantasy.communication.channel.definition.ChannelMessage;

public class UDPUnicastChannel<E extends ChannelMessage<?, ?>> extends
		AUDPChannel<E> {

	private final DatagramSocket socket;
	private final InetAddress remoteInetAddress;
	private final int localPort;
	private final int remotePort;

	public UDPUnicastChannel(final int localPort,
			final InetAddress remoteInetAddress, final int remotePort)
			throws SocketException {
		this.remoteInetAddress = remoteInetAddress;
		this.remotePort = remotePort;
		this.localPort = localPort;
		this.socket = new DatagramSocket(this.localPort);
	}

	@Override
	protected synchronized DatagramSocket getDatagramSocket() {
		return this.socket;
	}

	@Override
	protected InetAddress getRemoteInetAddress() {
		return this.remoteInetAddress;
	}

	@Override
	protected int getRemotePort() {
		return this.remotePort;
	}

}
