package ch.quantasy.communication.channel.definition;

/**
 * As the read of a {@link Channel} is blocking, the implementation of this
 * Interface shall encapsulate the read with this Receiver (Observable). The
 * pattern to be implemented is a data push.
 * 
 * @author reto
 * 
 * @param <E>
 *            The Message-Type expected
 */
public interface MessageReceiver<E extends ChannelMessage<?, ?>> {
	public E getLatestMessage();

	/**
	 * Any observer can attach to it and will be notified on read.
	 * 
	 * @param listener
	 *            The observer.
	 */
	public void addMessageReceiverListener(MessageReceiverListener<E> listener);

	/**
	 * Any attached observer can be detached and will not be notified any more
	 * 
	 * @param listener
	 *            The observer to detach.
	 */
	public void removeMessageReceiverListener(
			MessageReceiverListener<E> listener);

}
