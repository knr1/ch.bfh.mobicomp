package ch.quantasy.tinkerforge.position.view;

import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 */
public class AccelerationProfileView {

	private static final int MAX_DATA_POINTS = 1000;

	private Series<Number, Number> accelerationSeries;
	private int xSeriesData = 0;
	private static ConcurrentLinkedQueue<Number> dataAcceleration = new ConcurrentLinkedQueue<Number>();
	
	private NumberAxis xAxis;
	private NumberAxis yAxis;

	private NumberAxis initXAxis() {
		NumberAxis xAxis = new NumberAxis(0, MAX_DATA_POINTS,
				MAX_DATA_POINTS / 10);
		xAxis.setTickLabelFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);
		return xAxis;

	}

	private NumberAxis initYAxis() {
		NumberAxis yAxis = new NumberAxis(800, 802, 0.1);
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
			@Override
			public String toString(Number object) {
				return String.format("%6.2f", object);
			}
		});
		yAxis.setTickLabelFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		yAxis.setPrefWidth(120);
		yAxis.setAutoRanging(true);
		yAxis.setLabel("m/s^2");
		yAxis.setForceZeroInRange(false);
		yAxis.setAnimated(true);
		return yAxis;
	}

	private LineChart<Number, Number> initChart() {
		// -- Chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(
				xAxis, yAxis) {
			// Override to remove symbols on each data point
			@Override
			protected void dataItemAdded(Series<Number, Number> series,
					int itemIndex, Data<Number, Number> item) {
			}
		};
		lineChart.setAnimated(false);
		lineChart.setId("live acceleration");
		lineChart.setTitle("Sensor-Fusion (Dynamic Acceleration)");
		return lineChart;
	}

	private void init(Stage stage) {
		xAxis = initXAxis();
		yAxis = initYAxis();
		LineChart<Number, Number> chart = initChart();

		// Chart Series
		
		accelerationSeries = new LineChart.Series<Number, Number>();
		accelerationSeries.setName("Acceleration");
		chart.getData().add(accelerationSeries);

		stage.setScene(new Scene(chart));

	}


	public AccelerationProfileView(Stage stage){
		init(stage);
		prepareTimeline();
	}

	public static void addAccelerationData(final double data) {
		dataAcceleration.add(data);
	}

	

	// Timeline gets called in the JavaFX Main thread
	private void prepareTimeline() {
		// Every frame to take any data from queue and add to chart
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				addDataToSeries();
			}
		}.start();
	}

	private void addDataToSeries() {
		for (int i = 0; i < 50; i++) { // -- add some new samples to the plot
			if (dataAcceleration.isEmpty())
				break;
			accelerationSeries.getData().add(
					new LineChart.Data<Number, Number>(xSeriesData++,
							dataAcceleration.remove()));
		}
		
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (accelerationSeries.getData().size() > MAX_DATA_POINTS) {
			accelerationSeries.getData().remove(0,
					accelerationSeries.getData().size() - MAX_DATA_POINTS);
		}
		
		// update Axis
		xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
		xAxis.setUpperBound(xSeriesData - 1);
		
	}
}
