package fridgeit;

import sensor.AmbientLightApplication;
import sensor.DistanceApplication;
import sensor.HumidityApplication;
import sensor.TemperatureApplication;
import viewer.FridgeViewer;
import ch.quantasy.tinkerforge.tinker.agency.implementation.TinkerforgeStackAgent;
import ch.quantasy.tinkerforge.tinker.application.implementation.AbstractTinkerforgeApplication;

import com.tinkerforge.Device;

/**
 * This class is used to fuse the {@link FridgeSensor} and the
 * {@link FridgeViewer}. * It assumes that the viewer-stack is can be accessed
 * by the server-name <i>'localhost'</i> . <br/>
 * It assumes that the sensor-stack can be accessed by the server-name
 * <i>'fridge-sensor'</i>
 * 
 * 
 * @author reto
 * 
 */
public class FridgeIt extends AbstractTinkerforgeApplication {
	// A heuristic value representing the illuminance-threshold above which
	// the light in the fridge is lit.
	// Why is it not 0? The sensor gives some noise!
	// Why is it not 0? Old cheese might be glowing in the dark ;-)
	public static final int LIGHT_IS_SWITCHED_ON = 8;
	public static final int LIGHT_IS_SWITCHED_OFF = 7;
	
	// A heuristic value representing the distance from the sensors to the door in mm
	public static final int DOOR_IS_CLOSED = 50;
	private static final int DOOR_IS_OPENED = 60;

	private final FridgeViewer fridgeViewer;

	private AmbientLightApplication ambientLight;
	private DistanceApplication distance;
	private HumidityApplication humidity;
	private TemperatureApplication temperature;

	/**
	 * The {@link FridgeSensor} and the {@link FridgeViewer} are instantiated
	 * and connected. The instance will only exist if everything went fine.
	 * 
	 * @throws Exception
	 *             if one of the
	 */
	public FridgeIt() {
		this.fridgeViewer = new FridgeViewer();
		this.ambientLight = new AmbientLightApplication(this);
		this.distance = new DistanceApplication(this);
		this.humidity = new HumidityApplication(this);
		this.temperature = new TemperatureApplication(this);
		super.addTinkerforgeApplication(fridgeViewer);
		super.addTinkerforgeApplication(ambientLight,distance,humidity,temperature);
		
		this.ambientLight.setAmbientHistereseMax(FridgeIt.LIGHT_IS_SWITCHED_ON);
		this.ambientLight.setAmbientHistereseMin(FridgeIt.LIGHT_IS_SWITCHED_OFF);
		
		this.distance.setDistanceHistereseMin(FridgeIt.DOOR_IS_CLOSED);
		this.distance.setDistanceHistereseMax(FridgeIt.DOOR_IS_OPENED);
	}

	public void setHumidity(int humidityInPercent) {
		this.fridgeViewer.setHumidity(humidityInPercent / 10.0);
	}

	public void setObjectIRTemperature(int temperature) {
		this.fridgeViewer.setObjectTemp(temperature);
	}

	public void setAmbientTemperature(int temperature) {
		this.fridgeViewer.setAmbientTemp(temperature);
	}

	/**
	 * Information if the interieur light of the fridge is lit or not. The info
	 * is passed to the {@link FridgeViewer}
	 * 
	 * The histerese-threshold must be set in the
	 * {@link AmbientLightApplication}.
	 */
	public void setAmbientDarkState(boolean latestAnswerIsItDark) {
		fridgeViewer.setLightStatus(!latestAnswerIsItDark);
	}

	/**
	 * This class is responsible for receiving, processing and delegating data
	 * about the door-state.
	 * 
	 * @author reto
	 * 
	 */
	public void setDoorShutState(boolean latestAnswerIsItClosed) {
		FridgeIt.this.fridgeViewer.setDoorStatus(!latestAnswerIsItClosed);
	}

	@Override
	public void deviceDisconnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		// We do not care here...

	}

	@Override
	public void deviceConnected(TinkerforgeStackAgent tinkerforgeStackAgent,
			Device device) {
		// We do not care here...

	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ambientLight == null) ? 0 : ambientLight.hashCode());
		result = prime * result
				+ ((distance == null) ? 0 : distance.hashCode());
		result = prime * result
				+ ((fridgeViewer == null) ? 0 : fridgeViewer.hashCode());
		result = prime * result
				+ ((humidity == null) ? 0 : humidity.hashCode());
		result = prime * result
				+ ((temperature == null) ? 0 : temperature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FridgeIt other = (FridgeIt) obj;
		if (ambientLight == null) {
			if (other.ambientLight != null)
				return false;
		} else if (!ambientLight.equals(other.ambientLight))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (fridgeViewer == null) {
			if (other.fridgeViewer != null)
				return false;
		} else if (!fridgeViewer.equals(other.fridgeViewer))
			return false;
		if (humidity == null) {
			if (other.humidity != null)
				return false;
		} else if (!humidity.equals(other.humidity))
			return false;
		if (temperature == null) {
			if (other.temperature != null)
				return false;
		} else if (!temperature.equals(other.temperature))
			return false;
		return true;
	}

	
}
