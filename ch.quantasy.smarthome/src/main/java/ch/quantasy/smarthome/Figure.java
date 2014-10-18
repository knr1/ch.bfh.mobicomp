/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome;

import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class Figure {

    private JsonObject json;
    private Color color;

    public Figure(JsonObject json) {
	this.json = json;
	JsonObject colorObject = (JsonObject) (this.json.get("color"));
	if (colorObject != null) {
	    JsonNumber red = (JsonNumber) colorObject.get("r");
	    JsonNumber green = (JsonNumber) colorObject.get("g");
	    JsonNumber blue = (JsonNumber) colorObject.get("b");
	    color = new Color((float) red.doubleValue(), (float) green.doubleValue(), (float) blue.doubleValue());
	    System.out.println("Color: " + color);
	}
    }

    public Color getColor() {
	return color;
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
