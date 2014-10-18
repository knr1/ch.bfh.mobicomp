/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message.implementation;

import ch.quantasy.messagebus.message.definition.Content;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class AContent implements Content {

    @Override
    public String getContentID() {
	return this.getClass().getName();
    }

}
