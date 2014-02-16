package ch.quantasy.tinkerforge.tinker.agency.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TinkerforgeStackAgency {
	private static TinkerforgeStackAgency instance;
	private final HashMap<TinkerforgeStackAgentIdentifier, TinkerforgeStackAgent> tinkerforgeStackAgents;

	private TinkerforgeStackAgency() {
		this.tinkerforgeStackAgents = new HashMap<TinkerforgeStackAgentIdentifier, TinkerforgeStackAgent>();
	}

	public static TinkerforgeStackAgency getInstance() {
		if (TinkerforgeStackAgency.instance == null) {
			TinkerforgeStackAgency.instance = new TinkerforgeStackAgency();
		}
		return TinkerforgeStackAgency.instance;
	}

	public TinkerforgeStackAgent getStackAgent(final TinkerforgeStackAgentIdentifier identifier) {
		if (!this.tinkerforgeStackAgents.containsKey(identifier)) {
			this.tinkerforgeStackAgents.put(identifier, new TinkerforgeStackAgent(identifier));
		}
		return this.tinkerforgeStackAgents.get(identifier);
	}

	public Set<TinkerforgeStackAgentIdentifier> getTinkerforgeStackAgentIdentifiers() {
		return new HashSet<TinkerforgeStackAgentIdentifier>(this.tinkerforgeStackAgents.keySet());
	}

}
