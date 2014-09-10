package ch.quantasy.communication.channel.definition;

/**
 * Any implementation of this interface is allowed to send a
 * {@link ChannelMessage} to a {@link MessageSender}. This is part of a
 * notification (push) data (pull) pattern.
 * 
 * @author reto
 * 
 * @param <E>
 */
public interface MessageProducer<E extends ChannelMessage<?, ?>> {
	/**
	 * When ready the {@link MessageSender} that was invoked will fetch the
	 * {@link ChannelMessage} via this method.
	 * 
	 * @return The {@link ChannelMessage} the {@link MessageProducer} wants to
	 *         be sent.
	 */
	public E getLatestMessage();

	/**
	 * When the {@link MessageSender} fetched the {@link ChannelMessage} from
	 * the {@link MessageProducer#getLatestMessage()} method and successfully
	 * sent the {@link ChannelMessage} to the channel, this method will be
	 * invoked.
	 * 
	 * @param message
	 *            The message that was sent.
	 */
	public void messageDelivered(E message);

}
