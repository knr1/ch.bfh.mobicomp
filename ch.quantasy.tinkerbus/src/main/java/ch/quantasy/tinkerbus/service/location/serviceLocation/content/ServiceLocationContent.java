/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.location.serviceLocation.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;
import ch.quantasy.tinkerbus.service.location.serviceLocation.ServiceLocation;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ServiceLocationContent extends ValueContent<Set<ServiceLocation>> {

    public ServiceLocationContent(Set<ServiceLocation> value) {
	super(value);
    }

}
