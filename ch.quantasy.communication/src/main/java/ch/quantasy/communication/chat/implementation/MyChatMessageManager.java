package ch.quantasy.communication.chat.implementation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ch.quantasy.communication.channel.definition.ChannelManager;
import ch.quantasy.communication.channel.definition.MessageReceiver;
import ch.quantasy.communication.channel.definition.MessageSender;
import ch.quantasy.communication.channel.implementation.DefaultChannelManager;
import ch.quantasy.communication.channel.implementation.udp.UDPBroadcastChannel;
import ch.quantasy.communication.chat.definition.Chat;
import ch.quantasy.communication.chat.definition.ChatMessage;
import ch.quantasy.communication.chat.definition.ChatMessageManager;

public class MyChatMessageManager implements ChatMessageManager {
	private final Chat myChat;
	private ChannelManager<ChatMessage> channelManager;
	
	public MyChatMessageManager(final Chat myChat) {
		initNetworkElements();
		channelManager.getMessageReceiver().addMessageReceiverListener(this);
		this.sender = channelManager.getMessageSender();
		this.myChat = myChat;
	}
	
	private void initNetworkElements() {
		try {
			// http://en.wikipedia.org/wiki/Multicast_address
			// http://code.google.com/p/boxeeremote/wiki/AndroidUDP
			this.channelManager = new DefaultChannelManager<ChatMessage>(
					new UDPBroadcastChannel<ChatMessage>(
							InetAddress.getByName("224.0.2.4"), 4444, 2));
		} catch (final UnknownHostException e) {
			e.printStackTrace();
			// Exception-handling
		} catch (final IOException e) {
			e.printStackTrace();
			// Exception-handling
		}
	}
	

	@Override
	public Chat getChat() {
		return this.myChat;
	}

	// Things required by ChatMessageReceiver
	@Override
	public void messageReceived(final MessageReceiver<ChatMessage> receiver,
			final ChatMessage chatMessage) {
		this.myChat.onMessageReceived(chatMessage);
	}	

	// Things required by ChatMessageSender
	private ChatMessage latestMessage;
	private final MessageSender<ChatMessage> sender;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.quantasy.communication.chat.implementation.ChatMessageSender
	 * #send(ch.quantasy.communication.chat.definition.ChatMessage)
	 */
	@Override
	public void send(final ChatMessage message) {
		this.latestMessage = message;
		this.sender.sendRequest(this);
	}

	@Override
	public ChatMessage getLatestMessage() {
		return this.latestMessage;
	}

	@Override
	public void messageDelivered(final ChatMessage channelMessage) {
		this.myChat.onMessageDelivered();
	}
}
