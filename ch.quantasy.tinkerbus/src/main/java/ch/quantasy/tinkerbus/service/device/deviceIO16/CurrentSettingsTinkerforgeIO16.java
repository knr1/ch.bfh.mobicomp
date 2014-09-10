/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.quantasy.tinkerbus.service.device.deviceIO16;

import com.tinkerforge.BrickletIO16;
import java.util.Map;

/**
 *
 * @author Reto E. Koenig <reto.koenig@bfh.ch>
 */
public class CurrentSettingsTinkerforgeIO16 {

    private Long debouncePeriod;
    private Map<Short, EdgeCounter> edgeCounters;

    static {
	BrickletIO16 device = new BrickletIO16(null, null);
	//device.set device
	//.getPortConfiguration(port);
	//device.getPortInterrupt(port);
	//device.getPortMonoflop(port, pin);

    }
}
