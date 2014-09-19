package ch.quantasy.communication.channel.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import ch.quantasy.communication.channel.definition.Channel;
import ch.quantasy.communication.channel.definition.ChannelMessage;

public abstract class AStreamChannel<E extends ChannelMessage<?, ?>> implements
		Channel<E> {

	protected abstract InputStream getInputStream();

	protected abstract OutputStream getOutputStream();

	@Override
	public void write(final E message) throws IOException {
		final ObjectOutputStream oos = new ObjectOutputStream(
				this.getOutputStream());
		oos.writeObject(message);
		oos.flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E read() throws IOException {
		while (true) {
			E message = null;
			try {
				final ObjectInputStream ois = new ObjectInputStream(
						this.getInputStream());
				message = (E) ois.readObject();
			} catch (final InvalidClassException e) {
				e.printStackTrace();
				// non-compatible Message received
				// TODO: Optional Logging
				continue;
			} catch (final ClassNotFoundException e) {
				e.printStackTrace();
				// "Requested Message-Class not found
				// TODO: Optional Logging
				continue;
			} catch (final Throwable t) {
				return null;
			}
			return message;
		}
	}

}
