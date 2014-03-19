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
public class AltitudeProfileView {

	private static final int MAX_DATA_POINTS = 1000;

	private Series<Number, Number> estimatedAltitudeSeries;
	private Series<Number, Number> barometricAltitudeSeries;
	private int estimatedAltitudeXSeriesDataPosition = 0;
	private int barometricAltitudeXSeriesDataPosition = 0;
	private static ConcurrentLinkedQueue<Number> dataEstimatedAltitude = new ConcurrentLinkedQueue<Number>();
	private static ConcurrentLinkedQueue<Number> dataBarometricAltitude = new ConcurrentLinkedQueue<Number>();

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
		yAxis.setAutoRanging(false);
		yAxis.setLabel("Meters");
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
		lineChart.setId("Live Altitude Position");
		lineChart.setTitle("Sensor-Fusion (Altitude)");
		return lineChart;
	}

	private void init(Stage stage) {
		xAxis = initXAxis();
		yAxis = initYAxis();
		LineChart<Number, Number> chart = initChart();

		// Chart Series
		barometricAltitudeSeries = new LineChart.Series<Number, Number>();
		barometricAltitudeSeries.setName("Barometric Altitude");
		chart.getData().add(barometricAltitudeSeries);

		estimatedAltitudeSeries = new LineChart.Series<Number, Number>();
		estimatedAltitudeSeries.setName("Estimated Altitude");
		chart.getData().add(estimatedAltitudeSeries);

		stage.setScene(new Scene(chart));

	}

	public AltitudeProfileView(Stage stage){
		init(stage);
		prepareTimeline();
	}

	public static void addEstimatedAltitudeData(final double data) {
		dataEstimatedAltitude.add(data);
	}

	public static void addBarometricAltitudeData(final double data) {
		dataBarometricAltitude.add(data);
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
			if (dataEstimatedAltitude.isEmpty())
				break;
			estimatedAltitudeSeries.getData().add(
					new LineChart.Data<Number, Number>(estimatedAltitudeXSeriesDataPosition++,
							dataEstimatedAltitude.remove()));
		}
		for (int i = 0; i < 50; i++) { // -- add some new samples to the plot
			if (dataBarometricAltitude.isEmpty())
				break;
			barometricAltitudeSeries.getData().add(
					new LineChart.Data<Number, Number>(barometricAltitudeXSeriesDataPosition++,
							dataBarometricAltitude.remove()));
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (estimatedAltitudeSeries.getData().size() > MAX_DATA_POINTS) {
			estimatedAltitudeSeries.getData().remove(0,
					estimatedAltitudeSeries.getData().size() - MAX_DATA_POINTS);
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (barometricAltitudeSeries.getData().size() > MAX_DATA_POINTS) {
			barometricAltitudeSeries.getData().remove(0,
					barometricAltitudeSeries.getData().size() - MAX_DATA_POINTS);
		}
		// update Axis
		xAxis.setLowerBound(estimatedAltitudeXSeriesDataPosition - MAX_DATA_POINTS);
		xAxis.setUpperBound(estimatedAltitudeXSeriesDataPosition - 1);
		if (estimatedAltitudeSeries.getData().size() == 0)
			return;
		Number number = estimatedAltitudeSeries.getData()
				.get(estimatedAltitudeSeries.getData().size() - 1).getYValue();
		// yAxis.setLowerBound((int)(number.doubleValue()+0.5 - 1));
		// yAxis.setUpperBound((int)(number.doubleValue()+0.5 + 1));
		yAxis.setLowerBound((int) (number.doubleValue() * 100+0.5) / 100.0 - 1);
		yAxis.setUpperBound((int) (number.doubleValue() * 100+0.5) / 100.0 + 1);

	}
}
