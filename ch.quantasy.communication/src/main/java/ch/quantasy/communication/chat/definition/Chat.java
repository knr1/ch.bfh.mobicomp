package ch.quantasy.communication.chat.definition;

public interface Chat {

	public abstract void onMessageReceived(ChatMessage chatMessage);

	public abstract void onMessageDelivered();

}