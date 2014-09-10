package ch.quantasy.tinkerforge.tinker.application.definition;

import ch.quantasy.tinkerforge.tinker.agent.definition.Agent;

public interface Application<E extends Application<E>> {
	
	/**
	 * Called after the connection to the {@link Agent}.
	 * 
	 * @param agent
	 */
	public void connectedToAgent(Agent<E> agent);
	
	/**
	 * Called after the disconnection from the {@link Agent}.
	 * 
	 * @param agent
	 */
	public void disconnectedFromAgent(Agent<E> agent);
	
	/**
	 * Called prior to the disconnection form the {@link Agent}. It is the last
	 * chance to act on the connected {@link Agent}
	 * 
	 * @param agent
	 */
	public void disconnectingFromAgent(Agent<E> agent);

}
