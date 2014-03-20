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
				final Stage estimatedAltitudeStage = new Stage();
				final EstimatedAltitudeProfileView p = new EstimatedAltitudeProfileView(
						estimatedAltitudeStage);
				GUIApplication.stages.add(estimatedAltitudeStage);
				final Stage altitudeStage = new Stage();
				final AltitudeProfileView e = new AltitudeProfileView(
						altitudeStage);
				GUIApplication.stages.add(altitudeStage);
				final Stage velocityStage = new Stage();
				final VelocityProfileView v = new VelocityProfileView(
						velocityStage);
				GUIApplication.stages.add(velocityStage);
				final Stage accelerationStage = new Stage();
				final AccelerationProfileView a = new AccelerationProfileView(
						accelerationStage);
				GUIApplication.stages.add(accelerationStage);
				final Stage errorStage = new Stage();
				final ErrorProfileView b = new ErrorProfileView(errorStage);
				GUIApplication.stages.add(errorStage);

				for (final Stage stage : GUIApplication.stages) {
					stage.show();
				}
			}
		});

	}

	public static void finish() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (final Stage stage : GUIApplication.stages) {
					stage.close();
				}
			}
		});
	}

}
