package ch.quantasy.communication.channel.definition;

/**
 * Any observer of the {@link MessageReceiver} has to implement this interface
 * 
 * @author reto
 * 
 * @param <E>
 *            The message-Type
 */
public interface MessageReceiverListener<E extends ChannelMessage<?, ?>> {
	/**
	 * This mehtod will be called by the {@link MessageReceiver} (the
	 * observable) if a Message arrived.
	 * 
	 * @param receiver
	 *            the observable that invoked this method
	 * @param message
	 *            the message the observable gained.
	 */
	public void messageReceived(MessageReceiver<E> receiver, E message);
}
