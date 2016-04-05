/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.timer.timer;

import java.time.LocalDateTime;

/**
 *
 * @author reto
 */
public class TickTimer {

    private Thread thread;
    private TickTimerParameters parameters;

    public TickTimer() {

    }

    public LocalDateTime tick(TickTimerParameters parameters) {
        if (parameters == null || !parameters.isValid()) {
            return null;
        }
        parameters.setInUse(true);
        this.parameters = parameters;
        try {
            Thread.sleep(parameters.getPeriodInMilliSeconds());
            return LocalDateTime.now();
        } catch (InterruptedException ex) {
            //Interrupted
            return null;
        }
        finally{
            parameters.setInUse(false);
        }
    }
}
