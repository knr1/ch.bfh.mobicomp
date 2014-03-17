package ch.quantasy.tinkerforge.position;

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
public class View extends Application {

	private static final int MAX_DATA_POINTS = 1000;

	private Series<Number, Number> estimatedHightSeries;
	private Series<Number, Number> barometricHightSeries;
	private int xSeriesData = 0;
	private static ConcurrentLinkedQueue<Number> dataEstimatedHight = new ConcurrentLinkedQueue<Number>();
	private static ConcurrentLinkedQueue<Number> dataBarometricHight = new ConcurrentLinkedQueue<Number>();

	private NumberAxis xAxis;
	private NumberAxis yAxis;

	private void init(Stage primaryStage) {
		xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
		xAxis.setTickLabelFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);

		yAxis = new NumberAxis(8000, 8002, 0.1);
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
		lineChart.setId("liveAreaChart");
		lineChart.setTitle("Sensor-Fusion (Hight)");

		//Chart Series
		barometricHightSeries = new LineChart.Series<Number, Number>();
		barometricHightSeries.setName("Barometric Hight");
		
		lineChart.getData().add(barometricHightSeries);
		

		estimatedHightSeries = new LineChart.Series<Number, Number>();
		estimatedHightSeries.setName("Estimated Hight");
		lineChart.getData().add(estimatedHightSeries);


		primaryStage.setScene(new Scene(lineChart));
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();

		//Prepare Timeline
		prepareTimeline();

	}

	public static void addEstimatedHightData(final double data) {
		dataEstimatedHight.add(data);
	}

	public static void addBarometricHightData(final double data) {
		dataBarometricHight.add(data);
	}

	//Timeline gets called in the JavaFX Main thread
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
		for (int i = 0; i < 50; i++) { // -- add 20 numbers to the plot+
			if (dataEstimatedHight.isEmpty())
				break;
			estimatedHightSeries.getData().add(
					new LineChart.Data<Number, Number>(xSeriesData++,
							dataEstimatedHight.remove()));
		}
		for (int i = 0; i < 50; i++) { // -- add 20 numbers to the plot+
			if (dataBarometricHight.isEmpty())
				break;
			barometricHightSeries.getData().add(
					new LineChart.Data<Number, Number>(xSeriesData++,
							dataBarometricHight.remove()));
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (estimatedHightSeries.getData().size() > MAX_DATA_POINTS) {
			estimatedHightSeries.getData().remove(0,
					estimatedHightSeries.getData().size() - MAX_DATA_POINTS);
		}
		// remove points to keep us at no more than MAX_DATA_POINTS
		if (barometricHightSeries.getData().size() > MAX_DATA_POINTS) {
			barometricHightSeries.getData().remove(0,
					barometricHightSeries.getData().size() - MAX_DATA_POINTS);
		}
		// update
		xAxis.setLowerBound(xSeriesData - MAX_DATA_POINTS);
		xAxis.setUpperBound(xSeriesData - 1);
		Number number = estimatedHightSeries.getData()
				.get(estimatedHightSeries.getData().size() - 1).getYValue();
		// yAxis.setLowerBound((int)(number.doubleValue()+0.5 - 1));
		// yAxis.setUpperBound((int)(number.doubleValue()+0.5 + 1));
		yAxis.setLowerBound((int) (number.doubleValue() * 100) / 100.0 - 1);
		yAxis.setUpperBound((int) (number.doubleValue() * 100) / 100.0 + 1);

	}
}
