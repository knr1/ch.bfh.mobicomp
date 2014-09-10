package ch.quantasy.communication.channel.implementation.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import ch.quantasy.communication.channel.definition.ChannelMessage;
import ch.quantasy.communication.channel.implementation.AStreamChannel;

/**
 * This class allows to send and receive UDP-Data
 * 
 * @author reto
 * 
 * @param <E>
 */
public abstract class AUDPChannel<E extends ChannelMessage<?, ?>> extends
		AStreamChannel<E> {
	public static final int MAXIMUM_TRANSMISSION_UNIT = 65530;

	public AUDPChannel() {
	}

	protected abstract DatagramSocket getDatagramSocket();

	protected abstract InetAddress getRemoteInetAddress();

	protected abstract int getRemotePort();

	private byte[] receive() throws IOException {
		final byte[] buffer = new byte[AUDPChannel.MAXIMUM_TRANSMISSION_UNIT];
		final DatagramPacket datagram = new DatagramPacket(buffer,
				buffer.length);
		this.getDatagramSocket().receive(datagram);
		return buffer;
	}

	private void send(final byte[] buffer) throws IOException {

		final DatagramPacket datagram = new DatagramPacket(buffer,
				buffer.length, this.getRemoteInetAddress(),
				this.getRemotePort());
		this.getDatagramSocket().send(datagram);
	}

	@Override
	public void open() throws IOException {
		this.getDatagramSocket().setSoTimeout(0);
	}

	@Override
	public void close() throws IOException {
		this.getDatagramSocket().setSoTimeout(1);
	}

	@Override
	protected InputStream getInputStream() {
		InputStream inputStream = null;
		try {
			inputStream = new ByteArrayInputStream(this.receive());
		} catch (final IOException e) {

			e.printStackTrace();
		}
		return inputStream;
	}

	@Override
	protected OutputStream getOutputStream() {
		return new UDPFlushableByteArrayOutputStream();
	}

	/**
	 * This class allows a @see {@link ByteArrayOutputStream} to be flush-able
	 * over a {@link UDP-Socket}.
	 * 
	 * This renders UDP communication quasi stream oriented.
	 * 
	 * @author reto
	 * 
	 */
	class UDPFlushableByteArrayOutputStream extends ByteArrayOutputStream {
		@Override
		public void flush() throws IOException {
			super.flush(); //NoEffect on ByteArrayOutputStream
			super.close(); //NoEffect on ByteArrayOutputStream

			final byte[] buffer = super.toByteArray();
			AUDPChannel.this.send(buffer);
		}
	}

}
