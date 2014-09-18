package ch.quantasy.tinkerforge.barometer.view;

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
public class AltitudeProfileView {

    private static final int MAX_DATA_POINTS = 1000;

    private Series<Number, Number> barometricAltitudeSeries;
    private int barometricAltitudeXSeriesDataPosition = 0;

    private static ConcurrentLinkedQueue<Number> dataBarometricAltitude = new ConcurrentLinkedQueue<Number>();

    private NumberAxis xAxis;
    private NumberAxis yAxis;

    private NumberAxis initXAxis() {
	final NumberAxis xAxis = new NumberAxis(0,
						AltitudeProfileView.MAX_DATA_POINTS,
						AltitudeProfileView.MAX_DATA_POINTS / 10);
	xAxis.setTickLabelFont(Font.font("Arial", FontWeight.MEDIUM, 18));
	xAxis.setForceZeroInRange(false);
	xAxis.setAutoRanging(false);
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
	yAxis.setLabel("Meters⁻²");
	yAxis.setForceZeroInRange(false);
	yAxis.setAnimated(true);
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
	lineChart.setId("Live Altitude Position");
	lineChart.setTitle("Barometric (Altitude)");
	return lineChart;
    }

    private void init(final Stage stage) {
	this.xAxis = this.initXAxis();
	this.yAxis = this.initYAxis();
	final LineChart<Number, Number> chart = this.initChart();

	// Chart Series
	this.barometricAltitudeSeries = new LineChart.Series<Number, Number>();
	this.barometricAltitudeSeries.setName("Barometric Altitude");
	chart.getData().add(this.barometricAltitudeSeries);

	stage.setScene(new Scene(chart));

    }

    public AltitudeProfileView(final Stage stage) {
	this.init(stage);
	this.prepareTimeline();
    }

    public static void addBarometricAltitudeData(final double data) {
	AltitudeProfileView.dataBarometricAltitude.add(data);
    }

    // Timeline gets called in the JavaFX Main thread
    private void prepareTimeline() {
	// Every frame to take any data from queue and add to chart
	new AnimationTimer() {
	    @Override
	    public void handle(final long now) {
		AltitudeProfileView.this.addDataToSeries();
	    }
	}.start();
    }

    private void addDataToSeries() {
	for (int i = 0; i < 50; i++) { // -- add some new samples to the plot
	    if (AltitudeProfileView.dataBarometricAltitude.isEmpty()) {
		break;
	    }
	    this.barometricAltitudeSeries
		    .getData()
		    .add(new LineChart.Data<Number, Number>(
				    this.barometricAltitudeXSeriesDataPosition++,
				    AltitudeProfileView.dataBarometricAltitude.remove()));
	}

	// remove points to keep us at no more than MAX_DATA_POINTS
	if (this.barometricAltitudeSeries.getData().size() > AltitudeProfileView.MAX_DATA_POINTS) {
	    this.barometricAltitudeSeries.getData().remove(
		    0,
		    this.barometricAltitudeSeries.getData().size()
		    - AltitudeProfileView.MAX_DATA_POINTS);
	}
	// update Axis
	this.xAxis.setLowerBound(this.barometricAltitudeXSeriesDataPosition
		- AltitudeProfileView.MAX_DATA_POINTS);
	this.xAxis.setUpperBound(this.barometricAltitudeXSeriesDataPosition - 1);
    }
}
