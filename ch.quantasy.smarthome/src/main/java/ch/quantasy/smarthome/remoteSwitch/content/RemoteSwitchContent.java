/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.smarthome.remoteSwitch.content;

import ch.quantasy.smarthome.remoteSwitch.message.RemoteSwitch;
import ch.quantasy.tinkerbus.service.content.ValueContent;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RemoteSwitchContent extends ValueContent<RemoteSwitch> {

    public RemoteSwitchContent(RemoteSwitch value) {
	super(value);
    }

}
