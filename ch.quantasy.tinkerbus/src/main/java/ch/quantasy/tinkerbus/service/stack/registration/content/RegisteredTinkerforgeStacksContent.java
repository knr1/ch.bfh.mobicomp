/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack.registration.content;

import ch.quantasy.tinkerbus.service.content.ValueContent;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import java.util.Set;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class RegisteredTinkerforgeStacksContent extends ValueContent<Set<TinkerforgeStackAddress>> {

    public RegisteredTinkerforgeStacksContent(Set<TinkerforgeStackAddress> value) {
	super(value);
    }
}
