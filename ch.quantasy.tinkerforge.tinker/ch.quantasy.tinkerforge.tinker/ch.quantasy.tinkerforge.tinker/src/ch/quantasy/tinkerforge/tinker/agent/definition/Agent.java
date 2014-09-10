package ch.quantasy.tinkerforge.tinker.agent.definition;

import ch.quantasy.tinkerforge.tinker.application.definition.Application;

public interface Agent<E extends Application<E>> {
	public void addApplication(E application);
	public void removeApplication(E application);
}
