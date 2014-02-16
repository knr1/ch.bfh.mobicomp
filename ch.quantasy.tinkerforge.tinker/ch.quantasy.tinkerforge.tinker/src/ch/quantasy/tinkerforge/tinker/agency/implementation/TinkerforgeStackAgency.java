package ch.quantasy.tinkerforge.tinker.agency.implementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TinkerforgeStackAgency {
	private static TinkerforgeStackAgency instance;
	private HashMap<TinkerforgeStackAgentIdentifier,TinkerforgeStackAgent> tinkerforgeStackAgents;
	
	private TinkerforgeStackAgency(){
		this.tinkerforgeStackAgents=new HashMap<TinkerforgeStackAgentIdentifier, TinkerforgeStackAgent>();
	}
	
	public static TinkerforgeStackAgency getInstance() {
		if(instance==null)
			instance=new TinkerforgeStackAgency();
		return instance;
	}
	
	public TinkerforgeStackAgent getStackAgent(TinkerforgeStackAgentIdentifier identifier){
		if(!tinkerforgeStackAgents.containsKey(identifier)){
			tinkerforgeStackAgents.put(identifier, new TinkerforgeStackAgent(identifier));
		}
		return tinkerforgeStackAgents.get(identifier);
	}
	
	public Set<TinkerforgeStackAgentIdentifier>  getTinkerforgeStackAgentIdentifiers() {
		return new HashSet<TinkerforgeStackAgentIdentifier>(this.tinkerforgeStackAgents.keySet());
	}
	
}
