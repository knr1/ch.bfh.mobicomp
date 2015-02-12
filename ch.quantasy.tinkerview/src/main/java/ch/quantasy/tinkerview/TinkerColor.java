/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerview;

import java.io.Serializable;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
class TinkerColor implements Serializable {

    private final long serialVersionUID = 1L;

    public final int blue;
    public final int red;
    public final int green;

    public TinkerColor(int blue, int red, int green) {
	this.blue = blue;
	this.red = red;
	this.green = green;
    }

    @Override
    public String toString() {
	return "TinkerColor{" + "serialVersionUID=" + serialVersionUID + ", blue=" + blue + ", red=" + red + ", green=" + green + '}';
    }

}
