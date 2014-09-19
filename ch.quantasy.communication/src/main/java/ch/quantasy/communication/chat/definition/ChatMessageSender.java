package ch.quantasy.communication.chat.definition;

import ch.quantasy.communication.channel.definition.MessageProducer;

public interface ChatMessageSender extends MessageProducer<ChatMessage> {
	public void send(ChatMessage message);

}