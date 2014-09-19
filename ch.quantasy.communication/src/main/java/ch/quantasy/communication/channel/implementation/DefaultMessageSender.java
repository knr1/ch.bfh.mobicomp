package ch.quantasy.communication.channel.implementation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ch.quantasy.communication.channel.definition.Channel;
import ch.quantasy.communication.channel.definition.ChannelMessage;
import ch.quantasy.communication.channel.definition.MessageProducer;
import ch.quantasy.communication.channel.definition.MessageSender;

public class DefaultMessageSender<E extends ChannelMessage<?, ?>> implements
		MessageSender<E> {
	private final List<MessageProducer<E>> producers;
	private final Lock producersLock;
	private final Condition producersLockCondition;
	private final Channel<E> channel;

	public DefaultMessageSender(final Channel<E> channel) {
		this.channel = channel;
		this.producers = new ArrayList<MessageProducer<E>>();
		this.producersLock = new ReentrantLock();
		this.producersLockCondition = this.producersLock.newCondition();
		new Thread(new Sender()).start();
	}

	@Override
	public void sendRequest(final MessageProducer<E> producer) {
		if (producer == null) {
			return;
		}
		try {
			this.producersLock.lock();

			this.producersLockCondition.signalAll();
			if (this.producers.contains(producer)) {
				return;
			}
			this.producers.add(producer);
		} finally {
			this.producersLock.unlock();
		}
	}

	class Sender implements Runnable {
		@SuppressWarnings("synthetic-access")
		@Override
		public void run() {
			try {
				while (true) {
					MessageProducer<E> producer = null;
					while (producer == null) {
						try {
							DefaultMessageSender.this.producersLock.lock();
							if (DefaultMessageSender.this.producers.isEmpty()) {
								try {
									DefaultMessageSender.this.producersLockCondition
											.await();
								} catch (final InterruptedException e) {
									// OK, someone woke us up
								}
							} else {
								producer = DefaultMessageSender.this.producers
										.remove(0);
							}
						} finally {
							DefaultMessageSender.this.producersLock.unlock();
						}
					}
					final E message = producer.getLatestMessage();
					if (message != null) {
						DefaultMessageSender.this.channel.write(message);
						producer.messageDelivered(message);

					}
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
				// gone
				// TODO: Exception handling
			}
		};
	}
}
