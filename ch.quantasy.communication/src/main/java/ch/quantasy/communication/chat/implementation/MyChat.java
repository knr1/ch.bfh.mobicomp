package ch.quantasy.communication.chat.implementation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ch.quantasy.communication.chat.definition.Chat;
import ch.quantasy.communication.chat.definition.ChatMessage;
import ch.quantasy.communication.chat.definition.ChatMessageManager;

public class MyChat extends JFrame implements Chat {
	private ChatMessageManager chatMessageManager;
	
	private JButton sendButton;
	private JTextField sendEditText;
	private JScrollPane receiveTextScrollView;
	private JTextArea receiveTextView;
	private final String id;

	public MyChat() {
		this.id = "" + System.currentTimeMillis() % 100000;
		this.initGUIElements();
		this.initGUIListeners();
		this.setVisible(true);
	}

	

	private void initGUIElements() {
		this.setTitle(this.id);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.sendButton = new JButton("Send");
		this.sendEditText = new JTextField();
		this.receiveTextView = new JTextArea(20, 40);
		this.receiveTextView.setEditable(false);
		this.receiveTextScrollView = new JScrollPane();
		this.receiveTextScrollView.setViewportView(this.receiveTextView);
		this.getContentPane().add(this.receiveTextScrollView);
		final JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(this.sendEditText);
		southPanel.add(this.sendButton, BorderLayout.EAST);
		this.getContentPane().add(southPanel, BorderLayout.SOUTH);
		this.pack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.quantasy.communication.chat.implementation.Chat#addMessage
	 * (ch.quantasy.communication.chat.definition.ChatMessage)
	 */
	@Override
	public void onMessageReceived(final ChatMessage chatMessage) {
		this.receiveTextView.append(chatMessage.getHeader() + ">"
				+ chatMessage.getBody() + "\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.quantasy.communication.chat.implementation.Chat#
	 * setMessageDelivered()
	 */
	@Override
	public synchronized void onMessageDelivered() {
		this.sendButton.setEnabled(true);

	}

	private void initGUIListeners() {
		final ActionListener listener = new OnSendButtonClick();
		this.sendButton.addActionListener(listener);
		this.sendEditText.addActionListener(listener);
		this.chatMessageManager = new MyChatMessageManager(this);

	}

	class OnSendButtonClick implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent evt) {
			synchronized (MyChat.this) {

				MyChat.this.chatMessageManager.send(new MyChatMessage(
						MyChat.this.id, MyChat.this.sendEditText.getText()
								.toString()));
				MyChat.this.sendEditText.selectAll();
				MyChat.this.sendButton.setEnabled(false);
			}
		}
	}

	public static void main(final String[] args) {
		new MyChat();
	}
}