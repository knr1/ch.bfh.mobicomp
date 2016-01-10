package ch.quantasy.tinkerforge.tinker.agency.implementation;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TinkerforgeStackAgency {

    private static TinkerforgeStackAgency instance;
    private final HashMap<TinkerforgeStackAddress, TinkerforgeStackAgent> tinkerforgeStackAgents;

    private TinkerforgeStackAgency() {
	this.tinkerforgeStackAgents = new HashMap<>();
    }

    public static TinkerforgeStackAgency getInstance() {
	if (TinkerforgeStackAgency.instance == null) {
	    TinkerforgeStackAgency.instance = new TinkerforgeStackAgency();
	}
	return TinkerforgeStackAgency.instance;
    }

    public TinkerforgeStackAgent getStackAgent(final TinkerforgeStackAddress address) {
	if (!this.tinkerforgeStackAgents.containsKey(address)) {
	    this.tinkerforgeStackAgents.put(address, new TinkerforgeStackAgent(address));
	}
	return this.tinkerforgeStackAgents.get(address);
    }

    public boolean containsStackAgent(final TinkerforgeStackAddress address) {
	return (this.tinkerforgeStackAgents.containsKey(address));
    }

    public Set<TinkerforgeStackAddress> getTinkerforgeStackAddress() {
	return new HashSet<>(this.tinkerforgeStackAgents.keySet());
    }

    public Set<TinkerforgeStackAgent> getTinkerforgeStackAgents() {
	return new HashSet<>(this.tinkerforgeStackAgents.values());
    }

}
