package ch.quantasy.communication.channel.implementation;

import ch.quantasy.communication.channel.definition.Channel;
import ch.quantasy.communication.channel.definition.ChannelManager;
import ch.quantasy.communication.channel.definition.ChannelMessage;
import ch.quantasy.communication.channel.definition.MessageReceiver;
import ch.quantasy.communication.channel.definition.MessageSender;

public class DefaultChannelManager<E extends ChannelMessage<?, ?>> implements
		ChannelManager<E> {
	private final MessageReceiver<E> receiver;
	private final MessageSender<E> sender;

	public DefaultChannelManager(final Channel<E> channel) {
		this(channel, new DefaultMessageReceiver<E>(channel),
				new DefaultMessageSender<E>(channel));
	}

	public DefaultChannelManager(final Channel<E> channel,
			final MessageReceiver<E> receiver, final MessageSender<E> sender) {
		this.receiver = receiver;
		this.sender = sender;
	}

	@Override
	public MessageReceiver<E> getMessageReceiver() {
		return this.receiver;
	}

	@Override
	public MessageSender<E> getMessageSender() {
		return this.sender;
	}

}
