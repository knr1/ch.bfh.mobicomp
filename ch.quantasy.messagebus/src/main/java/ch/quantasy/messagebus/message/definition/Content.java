/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message.definition;

import java.io.Serializable;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public interface Content extends Serializable {

    public String getContentID();

}
