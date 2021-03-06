package ch.quantasy.tinkerforge.position.view;

import java.util.concurrent.ConcurrentLinkedQueue;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 */
public class VelocityProfileView {

	private static final int MAX_DATA_POINTS = 1000;

	private Series<Number, Number> estimatedVelocitySeries;
	private int xSeriesData = 0;
	private static ConcurrentLinkedQueue<Number> dataEstimatedVelocity = new ConcurrentLinkedQueue<Number>();

	private NumberAxis xAxis;
	private NumberAxis yAxis;

	private NumberAxis initXAxis() {
		final NumberAxis xAxis = new NumberAxis(0,
				VelocityProfileView.MAX_DATA_POINTS,
				VelocityProfileView.MAX_DATA_POINTS / 10);
		xAxis.setTickLabelFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		xAxis.setForceZeroInRange(false);
		xAxis.setAutoRanging(false);
		xAxis.setAnimated(false);
		return xAxis;
	}

	private NumberAxis initYAxis() {
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
			@Override
			public String toString(final Number object) {
				return String.format("%6.2f", object);
			}
		});
		yAxis.setTickLabelFont(Font.font("Arial", FontWeight.MEDIUM, 18));
		yAxis.setPrefWidth(120);
		yAxis.setAutoRanging(true);
		yAxis.setLabel("m/s");
		yAxis.setForceZeroInRange(false);
		yAxis.setAnimated(false);
		return yAxis;
	}

	private LineChart<Number, Number> initChart() {
		// -- Chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(
				this.xAxis, this.yAxis) {
			// Override to remove symbols on each data point
			@Override
			protected void dataItemAdded(final Series<Number, Number> series,
					final int itemIndex, final Data<Number, Number> item) {
			}
		};
		lineChart.setAnimated(false);
		lineChart.setId("live dynamic velocity");
		lineChart.setTitle("Sensor-Fusion (Dynamic Velocity)");
		return lineChart;
	}

	private void init(final Stage stage) {
		this.xAxis = this.initXAxis();
		this.yAxis = this.initYAxis();
		final LineChart<Number, Number> chart = this.initChart();

		// Chart Series

		this.estimatedVelocitySeries = new LineChart.Series<Number, Number>();
		this.estimatedVelocitySeries.setName("Estimated velocity");
		chart.getData().add(this.estimatedVelocitySeries);

		stage.setScene(new Scene(chart));

	}

	public VelocityProfileView(final Stage stage) {
		this.init(stage);
		this.prepareTimeline();
	}

	public static void addEstimatedVelocityData(final double data) {
		VelocityProfileView.dataEstimatedVelocity.add(data);
	}

	// Timeline gets called in the JavaFX Main thread
	private void prepareTimeline() {
		// Every frame to take any data from queue and add to chart
		new AnimationTimer() {
			@Override
			public void handle(final long now) {
				VelocityProfileView.this.addDataToSeries();
			}
		}.start();
	}

	private void addDataToSeries() {
		for (int i = 0; i < 50; i++) { // -- add some new samples to the plot
			if (VelocityProfileView.dataEstimatedVelocity.isEmpty()) {
				break;
			}
			this.estimatedVelocitySeries
					.getData()
					.add(new LineChart.Data<Number, Number>(this.xSeriesData++,
							VelocityProfileView.dataEstimatedVelocity.remove()));
		}

		// remove points to keep us at no more than MAX_DATA_POINTS
		if (this.estimatedVelocitySeries.getData().size() > VelocityProfileView.MAX_DATA_POINTS) {
			this.estimatedVelocitySeries.getData().remove(
					0,
					this.estimatedVelocitySeries.getData().size()
							- VelocityProfileView.MAX_DATA_POINTS);
		}

		// update Axis
		this.xAxis.setLowerBound(this.xSeriesData
				- VelocityProfileView.MAX_DATA_POINTS);
		this.xAxis.setUpperBound(this.xSeriesData - 1);
	}
}
