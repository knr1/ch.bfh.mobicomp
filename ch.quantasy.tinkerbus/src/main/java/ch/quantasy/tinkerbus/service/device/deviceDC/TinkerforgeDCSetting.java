/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceDC;

import ch.quantasy.tinkerbus.service.device.core.TinkerforgeDeviceSetting;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeDCSetting extends TinkerforgeDeviceSetting<TinkerforgeDCSetting> {

    private Boolean isEnabled;
    private Boolean fullBrake;
    private Integer acceleration;
    private Integer currentVelocityPeriod;
    private Short velocity;
    private Short driveMode;
    private Integer minimumVoltage;
    private Integer pwmFrequency;

    public void setPWMFrequency(Integer pwmFrequency) {
	this.pwmFrequency = pwmFrequency;
    }

    public Integer getPWMFrequency() {
	return pwmFrequency;
    }

    public Integer getMinimumVoltage() {
	return minimumVoltage;
    }

    public void setMinimumVoltage(Integer minimumVoltage) {
	this.minimumVoltage = minimumVoltage;
    }

    public void setDriveMode(Short driveMode) {
	this.driveMode = driveMode;
    }

    public Short getDriveMode() {
	return driveMode;
    }

    public Boolean isEnabled() {
	return isEnabled;
    }

    public void setEnabled(Boolean isEnabled) {
	this.isEnabled = isEnabled;
    }

    public Boolean isFullBrake() {
	return fullBrake;
    }

    public void setFullBrake(Boolean fullBrake) {
	this.fullBrake = fullBrake;
    }

    public Integer getAcceleration() {
	return acceleration;
    }

    public void setAcceleration(Integer acceleration) {
	this.acceleration = acceleration;
    }

    public Integer getCurrentVelocityPeriod() {
	return currentVelocityPeriod;
    }

    public void setCurrentVelocityPeriod(Integer currentVelocityPeriod) {
	this.currentVelocityPeriod = currentVelocityPeriod;
    }

    public Short getVelocity() {
	return velocity;
    }

    public void setVelocity(Short velocity) {
	this.velocity = velocity;
    }

    @Override
    public String toString() {
	return "TinkerforgeDCSetting{" + "isEnabled=" + isEnabled + ", fullBrake=" + fullBrake + ", acceleration=" + acceleration + ", currentVelocityPeriod=" + currentVelocityPeriod + ", velocity=" + velocity + '}';
    }

}
