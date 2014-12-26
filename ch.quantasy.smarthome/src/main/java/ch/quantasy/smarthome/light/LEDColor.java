/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.light;

import ch.quantasy.smarthome.light.content.Color;
import ch.quantasy.smarthome.light.content.Type;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDColor {

    private JsonObject json;
    private Color color;
    private Type type;

    public LEDColor(JsonObject json) {
	this.json = json;
	if (json != null) {
	    try {
		type = Type.valueOf(json.getString("type"));
	    } catch (IllegalArgumentException ex) {
		type = Type.ambient;
	    }
	}
	JsonObject colorObject = (JsonObject) (this.json.get("color"));
	if (colorObject != null) {
	    JsonNumber red = (JsonNumber) colorObject.get("r");
	    JsonNumber green = (JsonNumber) colorObject.get("g");
	    JsonNumber blue = (JsonNumber) colorObject.get("b");
	    color = new Color((float) red.doubleValue(), (float) green.doubleValue(), (float) blue.doubleValue());

	}
	System.out.println("Type: " + type + "Color: " + color);

    }

    public Color getColor() {
	return color;
    }

    public Type getType() {
	return type;
    }

    public JsonObject getJson() {
	return json;
    }

    public void setJson(JsonObject json) {
	this.json = json;
    }

    @Override
    public String toString() {

	StringWriter writer = new StringWriter();
	Json.createWriter(writer).write(json);
	return this.json.toString();
    }

}
