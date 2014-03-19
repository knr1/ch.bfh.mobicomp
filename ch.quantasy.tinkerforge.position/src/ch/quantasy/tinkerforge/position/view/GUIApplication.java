package ch.quantasy.tinkerforge.position.view;

import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 */
public class GUIApplication extends Application {
	private static List<Stage> stages = new LinkedList<>();

	public GUIApplication() {

	}

	@Override
	public void start(final Stage primaryStage) throws Exception {
		// primaryStage.show();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Stage altitudeStage = new Stage();
				AltitudeProfileView p = new AltitudeProfileView(altitudeStage);
				stages.add(altitudeStage);
				Stage velocityStage = new Stage();
				VelocityProfileView v = new VelocityProfileView(velocityStage);
				stages.add(velocityStage);
				Stage accelerationStage = new Stage();
				AccelerationProfileView a = new AccelerationProfileView(
						accelerationStage);
				stages.add(accelerationStage);
				Stage errorStage = new Stage();
				ErrorProfileView b = new ErrorProfileView(errorStage);
				stages.add(errorStage);

				for (Stage stage : stages) {
					stage.show();
				}
			}
		});

	}

	public static void finish() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Stage stage : stages) {
					stage.close();
				}
			}
		});
	}

}
