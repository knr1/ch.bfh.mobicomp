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
    private Map<Class, Content> emitions;

    public TinkerforgeDeviceContent() {
	this.settings = new HashMap<>();
	this.emitions = new HashMap<>();
    }

    public TinkerforgeDeviceContent(Map<Class, Content> settings, Map<Class, Content> emitions) {
	this.settings = new HashMap<>(settings);
	this.emitions = new HashMap<>(emitions);
    }

    public Map getEmitions() {
	return emitions;
    }

    public Map getSettings() {
	return settings;
    }

    public Set<Content> updateSettings(TinkerforgeDeviceContent content) {
	Set<Content> changeSet = new HashSet<>(content.getSettings().entrySet());
	changeSet.removeAll(settings.entrySet());
	this.settings = content.getSettings();
	return changeSet;
    }

    public Set<Content> updateEmitions(TinkerforgeDeviceContent content) {
	Set<Content> changeSet = new HashSet<>(content.getEmitions().entrySet());
	changeSet.removeAll(emitions.entrySet());
	this.emitions = content.getEmitions();
	return changeSet;
    }

    public Content updateSetting(Content content) {
	if (content == null) {
	    return null;
	}
	return settings.put(content.getClass(), content);
    }

    public Content updateEmition(Content content) {
	if (content == null) {
	    return null;
	}
	return emitions.put(content.getClass(), content);
    }

    public Content getSettingContentByID(Class classname) {
	return settings.get(classname);
    }

    public Content getEmitionContentByID(Class classname) {
	return emitions.get(classname);
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
