package ch.quantasy.tinkerforge.tinker.application.implementation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import ch.quantasy.tinkerforge.tinker.agent.definition.Agent;
import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

import com.tinkerforge.Device;

public abstract class AbstractTinkerforgeApplication implements TinkerforgeApplication {
	private final Set<Agent<TinkerforgeApplication>> tinkerforgeStackAgents;
	private final Set<TinkerforgeApplication> tinkerforgeApplications;

	public AbstractTinkerforgeApplication() {
		this.tinkerforgeStackAgents = new HashSet<Agent<TinkerforgeApplication>>();
		this.tinkerforgeApplications = new HashSet<TinkerforgeApplication>();
	}

	public void addTinkerforgeApplication(final TinkerforgeApplication... applications) {
		if (applications == null) {
			return;
		}
		for (final TinkerforgeApplication application : applications) {
			if (application == null || application==this) {
				continue;
			}
			this.tinkerforgeApplications.add(application);
			for (final Agent<TinkerforgeApplication> agent : this.tinkerforgeStackAgents) {
				agent.addApplication(application);
			}
		}
	}

	public void removeTinkerforgeApplication(final TinkerforgeApplication... applications) {
		if (applications == null) {
			return;
		}
		for (final TinkerforgeApplication application : applications) {
			if (application == null) {
				continue;
			}
			this.tinkerforgeApplications.remove(application);
			for (final Agent<TinkerforgeApplication> agent : this.tinkerforgeStackAgents) {
				agent.removeApplication(application);
			}
		}
	}

	public Collection<TinkerforgeApplication> getTinkerforgeApplications() {
		return new CopyOnWriteArrayList<TinkerforgeApplication>(this.tinkerforgeApplications);
	}

	@Override
	public void deviceIsDisconnecting(final TinkerforgeStackAgent stackAgent, final Device device) {
		System.out
				.println(this.toString() + " is disconnecting from device " + device + " attached to agent " + stackAgent);

	}
	@Override
	public void connectedToAgent(Agent<TinkerforgeApplication> agent) {
		if (agent == null) {
			return;
		}
		this.tinkerforgeStackAgents.add(agent);
		for (TinkerforgeApplication application : this.tinkerforgeApplications) {
			agent.addApplication(application);
		}

	}
	@Override
	public void disconnectedFromAgent(Agent<TinkerforgeApplication> agent) {
		if (tinkerforgeStackAgents.remove(agent)) {
			System.out.println(this.toString() + " has disconnected from agent " + agent);
		}
	}

	@Override
	public void disconnectingFromAgent(Agent<TinkerforgeApplication> agent) {
		if (agent == null) {
			return;
		}
		System.out.println(this.toString() + " is disconnecting from agent " + agent);
		for (TinkerforgeApplication application : this.tinkerforgeApplications) {
			agent.removeApplication(application);
		}

	}

	@Override
	public void stackConnected(final TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " connected to agent " + stackAgent);
	}
	
	@Override
	public void stackDisconnected(TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " connected to agent " + stackAgent);		
	}


	@Override
	public String toString() {
		return this.getClass().getName();
	}

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

}
