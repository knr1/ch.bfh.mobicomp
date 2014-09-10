package ch.quantasy.communication.channel.implementation.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import ch.quantasy.communication.channel.definition.ChannelMessage;

public class UDPBroadcastChannel<E extends ChannelMessage<?, ?>> extends
		AUDPChannel<E> {

	private final MulticastSocket socket;
	private final InetAddress inetAddress;
	private final int port;

	public UDPBroadcastChannel(final InetAddress broadcastAddress,
			final int port, final int timeToLive) throws IOException {
		this.inetAddress = broadcastAddress;
		this.port = port;
		this.socket = new MulticastSocket(this.port);
		this.socket.setTimeToLive(timeToLive);
		this.join();
	}

	@Override
	protected InetAddress getRemoteInetAddress() {
		return this.inetAddress;
	}

	@Override
	protected int getRemotePort() {
		return this.port;
	}

	@Override
	protected synchronized DatagramSocket getDatagramSocket() {
		return this.socket;
	}

	private void join() throws IOException {
		this.socket.joinGroup(this.inetAddress);
	}
}
