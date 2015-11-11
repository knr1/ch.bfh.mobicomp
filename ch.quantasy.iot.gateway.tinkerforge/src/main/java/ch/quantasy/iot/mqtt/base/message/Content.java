/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.base.message;

import ch.quantasy.iot.mqtt.base.MessageDescription;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Content {

    public static Gson gson;
    public MessageDescription description;
    public byte[] rawValue;

    static {
	gson = new Gson();
    }

    public Content(MessageDescription description) {
	this.description = description;
	rawValue = new byte[0];
    }

    public boolean updateContent(Object object) {
	String json = gson.toJson(object);
	return updateContent(json.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public boolean updateContent(byte[] content) {
	if (Objects.deepEquals(this.rawValue, content)) {
	    return false;
	}
	this.rawValue = Arrays.copyOf(content, content.length);
	return true;
    }

    public String getProperty() {
	return description.messagePropertyTopic.substring(1);
    }

    public <T> T getValue(Class<T> classOfT) {
	return gson.fromJson(new InputStreamReader(new ByteArrayInputStream(rawValue)), classOfT);
    }
}
