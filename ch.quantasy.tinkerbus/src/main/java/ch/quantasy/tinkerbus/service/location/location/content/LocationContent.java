/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.location.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;
import ch.quantasy.tinkerbus.service.location.location.Location;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class LocationContent extends ValueContent<Set<Location>> {

    public LocationContent(Set<Location> value) {
	super(value);
    }

}
