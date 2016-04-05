/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tutorial.step03a.timer;

import java.time.LocalDateTime;

/**
 *
 * @author reto
 */
public class TickTimer {

private  Thread thread;
private TickTimerParameters parameters;
    public TickTimer() {
        thread = new Thread(new TimerRunner());
       
    }
    
    public void tick(TickTimerParameters parameters){
        if (parameters == null || !parameters.isValid()) {
            return;
        }
        parameters.setInUse(true);
        this.parameters=parameters;
        thread.start();
    }

    class TimerRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(parameters.getPeriodInMilliSeconds());
                    parameters.getTimerCallback().tick(LocalDateTime.now());
                } catch (InterruptedException ex) {
                    //Interrupted.
                }
            }
        }

    }
}
