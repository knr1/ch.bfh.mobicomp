/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.event;

import ch.quantasy.iot.gateway.tinkerforge.base.AHandler;
import ch.quantasy.iot.gateway.tinkerforge.base.message.AnEvent;
import ch.quantasy.iot.gateway.tinkerforge.handler.deviceHandler.gps.GPS;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CoordinatesEvent extends AnEvent {

    public CoordinatesEvent(AHandler deviceHandler, String eventTopic, MqttAsyncClient mqttClient) {
	super(deviceHandler, eventTopic, "coordinates", mqttClient);
	super.addDescription(GPS.LATITUDE, Long.class, "JSON", "" + Long.MIN_VALUE, "...", "" + Long.MAX_VALUE);
	super.addDescription(GPS.NS, Character.class, "JSON", "N", "S");
	super.addDescription(GPS.LONGITUDE, Long.class, "JSON", "" + Long.MIN_VALUE, "...", "" + Long.MAX_VALUE);
	super.addDescription(GPS.EW, Character.class, "JSON", "E", "W");
	super.addDescription(GPS.PDOP, Integer.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
	super.addDescription(GPS.HDOP, Integer.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
	super.addDescription(GPS.VDOP, Integer.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
	super.addDescription(GPS.EPE, Integer.class, "JSON", "" + Integer.MIN_VALUE, "...", "" + Integer.MAX_VALUE);
    }

}
