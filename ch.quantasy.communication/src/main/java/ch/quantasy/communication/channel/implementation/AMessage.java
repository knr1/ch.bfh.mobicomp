package ch.quantasy.communication.channel.implementation;

import java.io.Serializable;

import ch.quantasy.communication.channel.definition.ChannelMessage;

public abstract class AMessage<E extends Serializable, F extends Serializable>
		implements ChannelMessage<E, F> {

	private static final long serialVersionUID = 1L;
	private final E header;
	private final F body;

	public AMessage(final E header, final F body) {
		this.header = header;
		this.body = body;
	}

	@Override
	public F getBody() {
		return this.body;
	}

	@Override
	public E getHeader() {
		return this.header;
	}

	@Override
	public String toString() {
		return this.header + ": " + this.body;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AMessage other = (AMessage) obj;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		return true;
	}
	
}
