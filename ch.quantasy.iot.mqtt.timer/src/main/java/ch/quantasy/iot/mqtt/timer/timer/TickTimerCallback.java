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
public interface TickTimerCallback {
    public void tick(LocalDateTime result);
}
