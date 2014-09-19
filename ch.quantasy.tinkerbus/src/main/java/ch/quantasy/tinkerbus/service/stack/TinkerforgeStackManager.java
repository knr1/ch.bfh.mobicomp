/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.stack;

import ch.quantasy.tinkerforge.tinker.agent.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.core.implementation.AbstractTinkerforgeStackManager;
import ch.quantasy.tinkerforge.tinker.core.implementation.TinkerforgeStackAddress;
import com.tinkerforge.Device;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class TinkerforgeStackManager extends AbstractTinkerforgeStackManager {

    private TinkerforgeStackService stackService;
    private Timer timer;
    private Exception actualConnectionException;
    public static final int DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS = 1000 * 60;
    private int connectionTimeoutInMilliseconds;

    public TinkerforgeStackManager(TinkerforgeStackAddress address) {
	super(address);
	this.connectionTimeoutInMilliseconds = TinkerforgeStackAgent.DEFAULT_CONNECTION_TIMEOUT_IN_MILLISECONDS;

    }

    public void setStackService(TinkerforgeStackService stackService) {
	this.stackService = stackService;
    }

    public TinkerforgeStackService getStackService() {
	return stackService;
    }

    @Override
    public synchronized void connect() {
	if (this.timer != null) {
	    return;
	}
	this.timer = new Timer(true);
	this.timer.schedule(new TimerTask() {

	    @Override
	    public void run() {
		try {
		    TinkerforgeStackManager.super.connect();
		    TinkerforgeStackManager.this.actualConnectionException = null;
		    TinkerforgeStackManager.this.timer.cancel();
		} catch (final UnknownHostException e) {
		    TinkerforgeStackManager.this.actualConnectionException = e;
		} catch (final IOException e) {
		    TinkerforgeStackManager.this.actualConnectionException = e;
		}
	    }
	}, 0, TinkerforgeStackManager.this.connectionTimeoutInMilliseconds);

    }

    @Override
    public void disconnect() {
	super.disconnect();
    }

    public int getConnectionTimeoutInMilliseconds() {
	return connectionTimeoutInMilliseconds;
    }

    public void setConnectionTimeoutInMilliseconds(int connectionTimeoutInMilliseconds) {
	this.connectionTimeoutInMilliseconds = connectionTimeoutInMilliseconds;
    }

    public Exception getActualConnectionException() {
	return actualConnectionException;
    }

    @Override
    protected void connected() {
	stackService.connected();
    }

    @Override
    protected void disconnected() {
	stackService.disconnected();
    }

    @Override
    protected void deviceConnected(Device device) {
	stackService.deviceConnected(device);
    }

    @Override
    protected void deviceReConnected(Device device) {
	stackService.deviceReConnected(device);
    }

    @Override
    protected void deviceDisconnected(Device device) {
	stackService.deviceDisconnected(device);
    }

}
