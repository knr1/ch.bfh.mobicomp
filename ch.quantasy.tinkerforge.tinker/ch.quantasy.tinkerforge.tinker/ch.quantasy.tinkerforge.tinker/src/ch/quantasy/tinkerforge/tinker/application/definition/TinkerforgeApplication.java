package ch.quantasy.tinkerforge.tinker.application.definition;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;

import com.tinkerforge.Device;

public interface TinkerforgeApplication extends Application<TinkerforgeApplication>{
	
	/**
	 * Called by the Agent if the stack has been connected.
	 * 
	 * @param tinkerforgeSatckAgent
	 *          the stack that has connected
	 */
	public void stackConnected(TinkerforgeStackAgent tinkerforgeSatckAgent);

	
	/**
	 * Called by the Agent if the stack has been disconnected
	 * 
	 * @param tinkerforgeStackAgent
	 *          the stack that has disconnected
	 */
	public void stackDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent);

	
	
	/**
	 * Called by the Agent if a Device (Brick of Bricklet) has been connected.
	 * 
	 * @param tinkerforgeStackAgent
	 *          the agent on which the Device has connected
	 * @param device
	 *          the device that has connected
	 */
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device);

	
	/**
	 * Called prior to the {@link Device} removal. It is the last chance to act on
	 * the connected {@link Device}
	 * 
	 * @param tinkerforgeStackAgent
	 * @param device
	 */
	public void deviceIsDisconnecting(TinkerforgeStackAgent tinkerforgeStackAgent, Device device);

	
	/**
	 * Called by the Agent if a Device (Brick of Bricklet) has been disconnected.
	 * 
	 * @param tinkerforgeStackAgent
	 *          the agent on which the Device has disconnected
	 * @param device
	 *          the device that has disconnected
	 */
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device);

	

	
}
