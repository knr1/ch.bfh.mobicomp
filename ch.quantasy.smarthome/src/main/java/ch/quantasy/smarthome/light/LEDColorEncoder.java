/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.light;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LEDColorEncoder implements Encoder.Text<LEDColor> {

    @Override
    public String encode(LEDColor ledColor) throws EncodeException {
	return ledColor.getJson().toString();
    }

    @Override
    public void init(EndpointConfig config) {
	System.out.println("init");
    }

    @Override
    public void destroy() {
	System.out.println("destroy");
    }

}
