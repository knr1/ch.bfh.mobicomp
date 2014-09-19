package ch.quantasy.communication.chat.definition;

public interface ChatMessageManager extends ChatMessageReceiver,
		ChatMessageSender {
	public Chat getChat();
}
