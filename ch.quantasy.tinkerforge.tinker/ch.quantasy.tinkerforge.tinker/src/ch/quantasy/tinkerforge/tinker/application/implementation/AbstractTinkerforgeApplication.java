package ch.quantasy.tinkerforge.tinker.application.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tinkerforge.Device;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

public abstract class AbstractTinkerforgeApplication implements TinkerforgeApplication {
	private Set<TinkerforgeStackAgentIdentifier> tinkerforgeStackAgentIdentifiers;
	private Set<TinkerforgeApplication> tinkerforgeApplications;

	public AbstractTinkerforgeApplication() {
		this.tinkerforgeStackAgentIdentifiers = new HashSet<TinkerforgeStackAgentIdentifier>();
		this.tinkerforgeApplications = new HashSet<TinkerforgeApplication>();
		this.addTinkerforgeApplication(this);
	}

	@Override
	public void addTinkerforgeApplication(TinkerforgeApplication... applications) {
		if (applications == null)
			return;
		for (TinkerforgeApplication application : applications) {
			if (application == null)
				continue;
			this.tinkerforgeApplications.add(application);
			for (TinkerforgeStackAgentIdentifier identifier : tinkerforgeStackAgentIdentifiers) {
				application.addTinkerforgeStackAgent(identifier);
			}
		}
	}

	@Override
	public void removeTinkerforgeApplication(TinkerforgeApplication... applications) {
		if (applications == null)
			return;
		for (TinkerforgeApplication application : applications) {
			if (application == null)
				continue;
			this.tinkerforgeApplications.remove(application);
			for (TinkerforgeStackAgentIdentifier identifier : tinkerforgeStackAgentIdentifiers) {
				application.removeTinkerforgeStackAgent(identifier);
			}
		}
	}

	public List<TinkerforgeApplication> getTinkerforgeApplications() {
		return new CopyOnWriteArrayList<TinkerforgeApplication>(this.tinkerforgeApplications);
	}

	@Override
	public void deviceIsDisconnecting(TinkerforgeStackAgent stackAgent, Device device) {
		System.out.println(this.toString() + " is disconnecting from device " + device +" attached to agent "+ stackAgent);
				
	}
	@Override
	public void stackAgentIsDisconnecting(TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " is disconnecting from agent " + stackAgent);
	}
	@Override
	public void stackDisconnected(TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " disconnected from agent " + stackAgent);
	}

	@Override
	public void stackConnected(TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " connected to agent " + stackAgent);
	}

	@Override
	public void addTinkerforgeStackAgent(TinkerforgeStackAgentIdentifier... tinkerforgeStackAgentIdentifiers) {
		if (tinkerforgeStackAgentIdentifiers == null)
			return;
		for (TinkerforgeStackAgentIdentifier identifier : tinkerforgeStackAgentIdentifiers) {
			if (identifier == null)
				continue;
			this.addTinkerforgeStackAgent(TinkerforgeStackAgency.getInstance().getStackAgent(identifier));
		}
	}

	@Override
	public void removeTinkerforgeStackAgent(TinkerforgeStackAgentIdentifier... tinkerforgeStackAgentIdentifiers) {
		if (tinkerforgeStackAgentIdentifiers == null)
			return;
		for (TinkerforgeStackAgentIdentifier identifier : tinkerforgeStackAgentIdentifiers) {
			if (identifier == null)
				continue;
			this.removeTinkerforgeStackAgent(TinkerforgeStackAgency.getInstance().getStackAgent(identifier));
		}
	}

	@Override
	public void removeTinkerforgeStackAgent(TinkerforgeStackAgent... tinkerforgeStackAgents) {
		if (tinkerforgeStackAgents == null)
			return;
		for (TinkerforgeStackAgent agent : tinkerforgeStackAgents) {
			if (agent == null)
				continue;
			for (TinkerforgeApplication application : tinkerforgeApplications) {
				if (agent.getTinkerforgeApplications().contains(application)) {
					if(application!=this)
					application.removeTinkerforgeStackAgent(agent);
					agent.removeTinkerforgeApplication(application);
				}
			}
		}
	}

	@Override
	public void addTinkerforgeStackAgent(TinkerforgeStackAgent... tinkerforgeStackAgents) {
		if (tinkerforgeStackAgents == null)
			return;
		for (TinkerforgeStackAgent agent : tinkerforgeStackAgents) {
			if (agent == null)
				continue;
			for (TinkerforgeApplication application : tinkerforgeApplications) {
				if(!agent.getTinkerforgeApplications().contains(application))
				{
					if(application!=this)
						application.addTinkerforgeStackAgent(agent);
					agent.addTinkerforgeApplication(application);
				}
			}
		}
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
