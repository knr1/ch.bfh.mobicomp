package ch.quantasy.tinkerforge.tinker.application.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgency;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgentIdentifier;
import ch.quantasy.tinkerforge.tinker.application.definition.TinkerforgeApplication;

import com.tinkerforge.Device;

public abstract class AbstractTinkerforgeApplication implements TinkerforgeApplication {
	private final Set<TinkerforgeStackAgentIdentifier> tinkerforgeStackAgentIdentifiers;
	private final Set<TinkerforgeApplication> tinkerforgeApplications;

	public AbstractTinkerforgeApplication() {
		this.tinkerforgeStackAgentIdentifiers = new HashSet<TinkerforgeStackAgentIdentifier>();
		this.tinkerforgeApplications = new HashSet<TinkerforgeApplication>();
		this.addTinkerforgeApplication(this);
	}

	@Override
	public void addTinkerforgeApplication(final TinkerforgeApplication... applications) {
		if (applications == null) {
			return;
		}
		for (final TinkerforgeApplication application : applications) {
			if (application == null) {
				continue;
			}
			this.tinkerforgeApplications.add(application);
			for (final TinkerforgeStackAgentIdentifier identifier : this.tinkerforgeStackAgentIdentifiers) {
				application.addTinkerforgeStackAgent(identifier);
			}
		}
	}

	@Override
	public void removeTinkerforgeApplication(final TinkerforgeApplication... applications) {
		if (applications == null) {
			return;
		}
		for (final TinkerforgeApplication application : applications) {
			if (application == null) {
				continue;
			}
			this.tinkerforgeApplications.remove(application);
			for (final TinkerforgeStackAgentIdentifier identifier : this.tinkerforgeStackAgentIdentifiers) {
				application.removeTinkerforgeStackAgent(identifier);
			}
		}
	}

	public List<TinkerforgeApplication> getTinkerforgeApplications() {
		return new CopyOnWriteArrayList<TinkerforgeApplication>(this.tinkerforgeApplications);
	}

	@Override
	public void deviceIsDisconnecting(final TinkerforgeStackAgent stackAgent, final Device device) {
		System.out
				.println(this.toString() + " is disconnecting from device " + device + " attached to agent " + stackAgent);

	}

	@Override
	public void stackAgentIsDisconnecting(final TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " is disconnecting from agent " + stackAgent);
	}

	@Override
	public void stackDisconnected(final TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " disconnected from agent " + stackAgent);
	}

	@Override
	public void stackConnected(final TinkerforgeStackAgent stackAgent) {
		System.out.println(this.toString() + " connected to agent " + stackAgent);
	}

	@Override
	public void addTinkerforgeStackAgent(final TinkerforgeStackAgentIdentifier... tinkerforgeStackAgentIdentifiers) {
		if (tinkerforgeStackAgentIdentifiers == null) {
			return;
		}
		for (final TinkerforgeStackAgentIdentifier identifier : tinkerforgeStackAgentIdentifiers) {
			if (identifier == null) {
				continue;
			}
			this.addTinkerforgeStackAgent(TinkerforgeStackAgency.getInstance().getStackAgent(identifier));
		}
	}

	@Override
	public void removeTinkerforgeStackAgent(final TinkerforgeStackAgentIdentifier... tinkerforgeStackAgentIdentifiers) {
		if (tinkerforgeStackAgentIdentifiers == null) {
			return;
		}
		for (final TinkerforgeStackAgentIdentifier identifier : tinkerforgeStackAgentIdentifiers) {
			if (identifier == null) {
				continue;
			}
			this.removeTinkerforgeStackAgent(TinkerforgeStackAgency.getInstance().getStackAgent(identifier));
		}
	}

	@Override
	public void removeTinkerforgeStackAgent(final TinkerforgeStackAgent... tinkerforgeStackAgents) {
		if (tinkerforgeStackAgents == null) {
			return;
		}
		for (final TinkerforgeStackAgent agent : tinkerforgeStackAgents) {
			if (agent == null) {
				continue;
			}
			for (final TinkerforgeApplication application : this.tinkerforgeApplications) {
				if (agent.getTinkerforgeApplications().contains(application)) {
					if (application != this) {
						application.removeTinkerforgeStackAgent(agent);
					}
					agent.removeTinkerforgeApplication(application);
				}
			}
		}
	}

	@Override
	public void addTinkerforgeStackAgent(final TinkerforgeStackAgent... tinkerforgeStackAgents) {
		if (tinkerforgeStackAgents == null) {
			return;
		}
		for (final TinkerforgeStackAgent agent : tinkerforgeStackAgents) {
			if (agent == null) {
				continue;
			}
			for (final TinkerforgeApplication application : this.tinkerforgeApplications) {
				if (!agent.getTinkerforgeApplications().contains(application)) {
					if (application != this) {
						application.addTinkerforgeStackAgent(agent);
					}
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
