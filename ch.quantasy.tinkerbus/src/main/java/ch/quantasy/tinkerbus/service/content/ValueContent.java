/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.content;

import ch.quantasy.messagebus.message.implementation.AContent;
import java.util.Objects;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class ValueContent<E extends Object> extends AContent {

    private final E value;

    public ValueContent(E value) {
	this.value = value;
    }

    public E getValue() {
	return value;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 17 * hash + Objects.hashCode(this.value);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final ValueContent<?> other = (ValueContent<?>) obj;
	if (!Objects.equals(this.value, other.value)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "ValueContent{" + "id=" + getContentID() + ", value=" + value + '}';
    }

}
