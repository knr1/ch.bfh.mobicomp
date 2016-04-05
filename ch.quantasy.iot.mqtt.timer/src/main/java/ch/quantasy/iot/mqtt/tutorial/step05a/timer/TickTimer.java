/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tutorial.step05a.timer;

import java.time.LocalDateTime;

/**
 *
 * @author reto
 */
public class TickTimer {

    private Thread thread;
    private TickTimerParameters parameters;
    private boolean isPaused;

    public TickTimer() {
        thread = new Thread(new TimerRunner());

    }

    public void tick(TickTimerParameters parameters) {
        if (parameters == null || !parameters.isValid()) {
            return;
        }
        parameters.setInUse(true);
        this.parameters = parameters;
        thread.start();
    }

    public synchronized void setPause(boolean pause) {
        if (this.isPaused == pause) {
            return;
        }
        this.isPaused = pause;
        this.notifyAll();
        thread.interrupt();
    }

    public synchronized boolean isPaused() {
        return isPaused;
    }
    
    class TimerRunner implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (this) {
                        while (isPaused) {
                            this.wait();
                        }
                    }
                    Thread.sleep(parameters.getPeriodInMilliSeconds());
                    parameters.getTimerCallback().tick(LocalDateTime.now());
                } catch (InterruptedException ex) {
                    //Interrupted.
                }
            }
        }

    }
}
