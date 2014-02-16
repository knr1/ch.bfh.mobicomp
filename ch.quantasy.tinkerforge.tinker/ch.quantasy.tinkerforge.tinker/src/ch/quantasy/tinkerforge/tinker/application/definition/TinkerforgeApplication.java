package ch.quantasy.tinkerforge.tinker.application.definition;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;

import com.tinkerforge.Device;

public interface TinkerforgeApplication {
	/**
	 * Called by the Agent if the stack has been disconnected
	 * 
	 * @param tinkerforgeStackAgent
	 *          the agent that has disconnected
	 */
	public void stackDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent);

	/**
	 * Called by the Agent if the stack has been connected.
	 * 
	 * @param tinkerforgeSatckAgent
	 *          the agent that has connected
	 */
	public void stackConnected(TinkerforgeStackAgent tinkerforgeSatckAgent);

	/**
	 * Called by the Agent if a Device (Brick of Bricklet) has been disconnected.
	 * 
	 * @param tinkerforgeStackAgent
	 *          the agent on which the Device has disconnected
	 * @param device
	 *          the device that has disconnected
	 */
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent, Device device);

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
	 * Called in order to request a removal from a TinkerforgeStackAgent. An
	 * application is hence requested to remove itself from the agent. This way,
	 * the agent will eventually disconnect from the stack.
	 * 
	 * @param tinkerforgeStackAgent
	 *          The tinkerforgeStackAgent to remove from
	 */
	public void removeTinkerforgeStackAgent(TinkerforgeStackAgent... tinkerforgeStackAgent);

	/**
	 * Called in order to request an attaching to a TinkerforgeStackAgent. An
	 * application is hence requested to attach itself to the agent. This way, the
	 * agent will connect to the stack.
	 * 
	 * @param tinkerforgeStackAgent
	 *          The tinkerforgeStackAgent to attach to
	 */
	public void addTinkerforgeStackAgent(TinkerforgeStackAgent... tinkerforgeStackAgent);

	/**
	 * Called in order to request a removal from a TinkerforgeStackAgent. An
	 * application is hence requested to remove itself from the agent. This way,
	 * the agent will eventually disconnect from the stack.
	 * 
	 * @param tinkerforgeStackAgentIdentifier
	 *          The tinkerforgeStackAgent to remove from
	 */
	public void removeTinkerforgeStackAgent(TinkerforgeStackAgentIdentifier... tinkerforgeStackAgentIdentifier);

	/**
	 * Called in order to request an attaching to a TinkerforgeStackAgent. An
	 * application is hence requested to attach itself to the agent. This way, the
	 * agent will connect to the stack.
	 * 
	 * @param tinkerforgeStackAgentIdentifier
	 *          The tinkerforgeStackAgent to attach to
	 */
	public void addTinkerforgeStackAgent(TinkerforgeStackAgentIdentifier... tinkerforgeStackAgentIdentifier);

	/**
	 * Called in order to aggregate a {@link TinkerforgeApplication} with this
	 * {@link TinkerforgeApplication}.
	 * 
	 * @param application
	 */
	public void addTinkerforgeApplication(TinkerforgeApplication... application);

	/**
	 * Called in order to remove a {@link TinkerforgeApplication} from this
	 * {@link TinkerforgeApplication}.
	 * 
	 * @param application
	 */
	public void removeTinkerforgeApplication(TinkerforgeApplication... application);

	/**
	 * Called prior to the {@link TinkerforgeStackAgent} removal. It is the last
	 * chance to act on the connected {@link TinkerforgeStackAgent}
	 * 
	 * @param tinkerforgeStackAgent
	 */
	public void stackAgentIsDisconnecting(TinkerforgeStackAgent tinkerforgeStackAgent);

	/**
	 * Called prior to the {@link Device} removal. It is the last chance to act on
	 * the connected {@link Device}
	 * 
	 * @param tinkerforgeStackAgent
	 * @param device
	 */
	public void deviceIsDisconnecting(TinkerforgeStackAgent tinkerforgeStackAgent, Device device);

}
