/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.light.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ColorContent extends ValueContent<Color> {

    public ColorContent(Color value) {
	super(value);
    }
    
}
