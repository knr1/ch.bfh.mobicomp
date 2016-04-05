/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.iot.mqtt.tutorial.step06a.timer;



/**
 *
 * @author reto
 */
public class TickTimerParameters {

    private TickTimerCallback timerCallback;
    private int periodInMilliSeconds;
    private boolean inUse;

    public TickTimerCallback getTimerCallback() {
        return timerCallback;
    }

    public void setTimerCallback(TickTimerCallback timerCallback) {
        if (isInUse()) {
            return;
        }
        this.timerCallback = timerCallback;
    }

    public int getPeriodInMilliSeconds() {
        return periodInMilliSeconds;
    }

    public void setPeriodInMilliSeconds(int periodInMilliSeconds) {
        if(isInUse()){
            return;
        }
        this.periodInMilliSeconds = periodInMilliSeconds;
    }

    public boolean isValid() {
        return this.timerCallback != null && periodInMilliSeconds > 10;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Used by Timer to indicate if a timer uses these parameters
     *
     * @return
     */
    public boolean isInUse() {
        return inUse;
    }

    /**
     * Used by Timer to indicate that a timer uses these parameters.
     *
     * @param inUse
     */
    protected void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
