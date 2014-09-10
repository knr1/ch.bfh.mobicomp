package ch.quantasy.communication.chat.implementation;

import ch.quantasy.communication.channel.implementation.AMessage;
import ch.quantasy.communication.chat.definition.ChatMessage;

public class MyChatMessage extends AMessage<String, String> implements
		ChatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyChatMessage(final String header, final String body) {
		super(header, body);
	}
}
