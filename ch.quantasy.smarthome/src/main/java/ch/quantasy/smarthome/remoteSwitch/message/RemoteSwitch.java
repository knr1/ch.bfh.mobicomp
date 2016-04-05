/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.remoteSwitch.message;

import ch.quantasy.smarthome.remoteSwitch.content.Floor;
import ch.quantasy.smarthome.remoteSwitch.content.SwitchType;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitch {

    private JsonObject json;
    private Floor floor;
    private SwitchType type;
    private boolean switchOn;
    private int dimValue;
    private int address;
    private int unit;

    public RemoteSwitch(JsonObject json) {
	this.json = json;
	if (this.json != null) {
	    try {
		type = SwitchType.valueOf(this.json.getString("type"));
		floor = Floor.valueOf(this.json.getString("floor"));
		address = this.json.getInt("address");
		unit = this.json.getInt("unit");
		switchOn = this.json.getBoolean("switchTo");
		dimValue = this.json.getInt("dimValue", -1);
		System.out.println("Type: " + type + " Floor: " + floor + " Address: " + address + " Unit: " + unit + " SwitchOn: " + switchOn + " Dim: " + dimValue);
	    } catch (Exception ex) {
		throw new IllegalArgumentException();
	    }
	}
    }

    public Floor getFloor() {
	return floor;
    }

    public SwitchType getType() {
	return type;
    }

    public boolean isSwitchOn() {
	return switchOn;
    }

    public int getDimValue() {
	return dimValue;
    }

    public int getAddress() {
	return address;
    }

    public int getUnit() {
	return unit;
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
