package ch.quantasy.communication.channel.definition;

/**
 * The implementation of this interface manages the atomic writing into a
 * Stream/Channel. Hence, this is part of a notification (push) message (pull)
 * pattern. When a {@link MessageProducer} has some data ready to be sent, it
 * notifies the {@link MessageSender}. As soon as the {@link MessageSender} is
 * ready to treat the notified messageProducer, it gets its latest data and
 * processes it.
 * 
 * @author reto
 * 
 * @param <E>
 */
public interface MessageSender<E extends ChannelMessage<?, ?>> {
	public void sendRequest(MessageProducer<E> producer);
}
