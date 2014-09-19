package ch.quantasy.communication.channel.definition;

/**
 * Is an aggregate of one {@link MessageReceiver} and one {@link MessageSender}
 * 
 * @author reto
 * 
 * @param <E>
 */
public interface ChannelManager<E extends ChannelMessage<?, ?>> {
	/**
	 * @return The {@link MessageReceiver} in use
	 */
	public MessageReceiver<E> getMessageReceiver();

	/**
	 * @return The {@link MessageSender} in use
	 */
	public MessageSender<E> getMessageSender();
}
