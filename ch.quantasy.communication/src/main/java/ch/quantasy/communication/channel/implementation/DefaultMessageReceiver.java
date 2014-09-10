package ch.quantasy.communication.channel.implementation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ch.quantasy.communication.channel.definition.Channel;
import ch.quantasy.communication.channel.definition.ChannelMessage;
import ch.quantasy.communication.channel.definition.MessageReceiver;
import ch.quantasy.communication.channel.definition.MessageReceiverListener;

public class DefaultMessageReceiver<E extends ChannelMessage<?, ?>> implements
		MessageReceiver<E> {
	private final Set<MessageReceiverListener<E>> listeners;
	private E latestMessage;
	private final Channel<E> channel;
	private Thread receiverThread;

	public DefaultMessageReceiver(final Channel<E> channel) {
		this.channel = channel;
		this.listeners = new HashSet<MessageReceiverListener<E>>();
	}

	@Override
	public void addMessageReceiverListener(
			final MessageReceiverListener<E> listener) {
		this.listeners.add(listener);
		synchronized (this) {
			if (!this.listeners.isEmpty() && (this.receiverThread == null)) {
				try {
					this.channel.open();
					this.receiverThread = new Thread(new Receiver());
					this.receiverThread.start();
				} catch (final IOException e) {
					e.printStackTrace();
					// Well that's no good!
					// TODO: Exception-Handling
				}

			}
		}

	}

	@Override
	public E getLatestMessage() {
		return this.latestMessage;

	}

	@Override
	public void removeMessageReceiverListener(
			final MessageReceiverListener<E> listener) {
		this.listeners.remove(listener);
		if (this.listeners.isEmpty()) {
			try {
				this.channel.close();
			} catch (final IOException e1) {
				// so what, That is bad but what can we do.
			}
			this.receiverThread.interrupt();
			try {
				this.receiverThread.join();
			} catch (final InterruptedException e) {
				// Someone woke us up, that is ok.
			}
			this.receiverThread = null;
		}

	}

	class Receiver implements Runnable {
		private final ExecutorService executorService;

		public Receiver() {
			this.executorService = Executors.newCachedThreadPool();
		}

		@SuppressWarnings({ "synthetic-access" })
		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					final E message = DefaultMessageReceiver.this.channel
							.read();
					if (message == null) {
						continue;
					}
					DefaultMessageReceiver.this.latestMessage = message;
					for (final MessageReceiverListener<E> listener : DefaultMessageReceiver.this.listeners) {
						this.executorService.execute(new Runnable() {
							@Override
							public void run() {
								listener.messageReceived(
										DefaultMessageReceiver.this, message);
							}
						});
					}
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
				// TODO: Exception Handling
			}
		}
	}
}
