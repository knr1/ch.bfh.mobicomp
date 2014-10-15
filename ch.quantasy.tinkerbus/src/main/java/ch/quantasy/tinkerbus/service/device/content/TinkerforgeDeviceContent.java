/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.content;

import ch.quantasy.messagebus.message.definition.Content;
import ch.quantasy.messagebus.message.implementation.AContent;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDeviceContent extends AContent implements Content {

    private Set<Content> settings;
    private Set<Content> emitions;

    private TinkerforgeDeviceContent() {

    }

    public TinkerforgeDeviceContent(Set<Content> settings, Set<Content> emitions) {
	this.settings = new HashSet<>(settings);
	this.emitions = new HashSet<>(emitions);
    }

    public Set<Content> getEmitions() {
	return emitions;
    }

    public Set<Content> getSettings() {
	return settings;
    }

    public Set<Content> getSettingsChange(TinkerforgeDeviceContent content) {
	Set<Content> changeSet = new HashSet<>(content.getSettings());
	changeSet.removeAll(settings);
	return changeSet;
    }

    public Set<Content> getEmitionsChange(TinkerforgeDeviceContent content) {
	Set<Content> changeSet = new HashSet<>(content.getEmitions());
	changeSet.removeAll(settings);
	return changeSet;
    }

    public void setSettings(Set<Content> settings) {
	this.settings = settings;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 13 * hash + Objects.hashCode(this.settings);
	hash = 13 * hash + Objects.hashCode(this.emitions);
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
	if (!Objects.equals(this.emitions, other.emitions)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "TinkerforgeDeviceContent{" + "id=" + super.getContentID() + ", settings=" + settings + ", emitions=" + emitions + '}';
    }

}
