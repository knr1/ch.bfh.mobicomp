package ch.quantasy.communication.channel.definition;

import java.io.IOException;

/**
 * Represents any mean of transportation for any message ({@link ChannelMessage}
 * .
 * 
 * @author reto
 * 
 * @param <E>
 */
public interface Channel<E extends ChannelMessage<?, ?>> {

	/**
	 * Writes a message to the Channel
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void write(E message) throws IOException;

	/**
	 * Closes the channel so it is no longer possible to store nor to receive
	 * messages
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException;

	/**
	 * Opens the channel to be ready to store and receive messages
	 * 
	 * @throws IOException
	 */
	public void open() throws IOException;

	/**
	 * Reads on the channel until a message is stored (Blocking)
	 * 
	 * @return The stored message
	 * @throws IOException
	 */
	public E read() throws IOException;

}