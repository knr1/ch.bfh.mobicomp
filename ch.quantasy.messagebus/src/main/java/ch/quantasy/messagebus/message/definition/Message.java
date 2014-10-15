/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.messagebus.message.definition;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public interface Message extends Serializable {

    public long getTimeStamp();

    public String getSenderID();

    public String getID();

    public boolean containsReceiverIDs();

    public void addReceiverIDs(String... receiverIDs);

    public boolean containsReceiverID(String receiverID);

    public boolean containsContent();

    public void addContents(Content... contents);

    public Collection<Content> getContents();

    public Content getContentByID(Class classname);

}
