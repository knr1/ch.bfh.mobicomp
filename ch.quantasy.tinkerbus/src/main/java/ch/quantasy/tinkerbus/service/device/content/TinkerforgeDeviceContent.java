/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.content;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.implementation.AContent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDeviceContent extends AContent implements Content {

    private Map<Class, Content> settings;
    private Map<Class, Content> emissions;

    public TinkerforgeDeviceContent() {
	this.settings = new HashMap<>();
	this.emissions = new HashMap<>();
    }

    public TinkerforgeDeviceContent(Set<Content> settings, Set<Content> emissions) {
	this();
	for (Content content : settings) {
	    this.settings.put(content.getClass(), content);
	}
	for (Content content : emissions) {
	    this.emissions.put(content.getClass(), content);
	}
    }

    public TinkerforgeDeviceContent(Map<Class, Content> settings, Map<Class, Content> emissions) {
	this.settings = new HashMap<>(settings);
	this.emissions = new HashMap<>(emissions);
    }

    public Map<Class, Content> getEmissions() {
	return new HashMap(this.emissions);
    }

    public Map<Class, Content> getSettings() {
	return new HashMap(this.settings);
    }

    /**
     * Enriches the settings stored with the settings stored in content. This is done by adding new settings and by
     * replacing existent settings with different values.
     *
     * @param content The content carrying potentially new settings...
     * @return The values that were updated
     */
    public Set<Content> updateSettings(TinkerforgeDeviceContent content) {
	if (content == null) {
	    return new HashSet<>();
	}
	Set<Content> changeSet = new HashSet<>(content.settings.values());
	changeSet.removeAll(settings.entrySet());
	this.settings = content.getSettings();
	return changeSet;
    }

    /**
     * Enriches the emissions stored with the emissions stored in content. This is done by adding new emissions and by
     * replacing existent emissions with different values.
     *
     * @param content The content carrying potentially new emissions...
     * @return The values that were updated
     */
    public Set<Content> updateEmissions(TinkerforgeDeviceContent content) {
	if (content == null) {
	    return new HashSet<>();
	}
	Set<Content> changeSet = new HashSet<>(content.emissions.values());
	changeSet.removeAll(emissions.entrySet());
	this.emissions = content.getEmissions();
	return changeSet;
    }

    /**
     * Updates a single setting with the one provided by content.
     *
     * @param content
     * @return If the value changed, this method returns the new value else it returns null
     */
    public Content updateSetting(Content content) {
	if (content != null && settings.put(content.getClass(), content) != null) {
	    return content;
	}
	return null;
    }

    /**
     * Updates a single emission with the one provided by content.
     *
     * @param content
     * @return If the value changed, this method returns the new value else it returns null
     */
    public Content updateEmission(Content content) {

	if (content != null && emissions.put(content.getClass(), content) != null) {
	    return content;
	}
	return null;
    }

    public Content getSettingContentByID(Class classname) {
	return settings.get(classname);
    }

    public Content getEmissionContentByID(Class classname) {
	return emissions.get(classname);
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 13 * hash + Objects.hashCode(this.settings);
	hash = 13 * hash + Objects.hashCode(this.emissions);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final TinkerforgeDeviceContent other = (TinkerforgeDeviceContent) obj;
	if (!Objects.equals(this.settings, other.settings)) {
	    return false;
	}
	if (!Objects.equals(this.emissions, other.emissions)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "TinkerforgeDeviceContent{" + "id=" + super.getContentID() + ", settings=" + settings + ", emissions=" + emissions + '}';
    }

}
