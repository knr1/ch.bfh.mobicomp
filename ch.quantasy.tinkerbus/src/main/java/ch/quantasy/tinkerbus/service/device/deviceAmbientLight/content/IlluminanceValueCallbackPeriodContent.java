/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceAmbientLight.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class IlluminanceValueCallbackPeriodContent extends ValueContent<Long> {

    public IlluminanceValueCallbackPeriodContent(Long value) {
	super(value);
    }

}
