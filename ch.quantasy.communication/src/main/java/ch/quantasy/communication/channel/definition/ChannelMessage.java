package ch.quantasy.communication.channel.definition;

import java.io.Serializable;

/**
 * The message that can be sent via a {@link Channel}
 * 
 * @author reto
 * 
 * @param <E>
 *            The header-Type of the Message
 * @param <F>
 *            The body-Type of the Message
 */
public interface ChannelMessage<E extends Serializable, F extends Serializable>
		extends Serializable {
	/**
	 * @return The header information
	 */
	public E getHeader();

	/**
	 * @return The body information
	 */
	public F getBody();
}
