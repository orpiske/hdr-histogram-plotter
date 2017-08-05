package net.orpiske.hhp.plot;


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.IOException;
import java.util.List;


public class Plotter {
    private static final String SERIES_NAME = "Percentiles range";
    private String baseName;

    public Plotter(final String baseName) {
        this.baseName = baseName;
    }

    private XYChart buildCommonChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(1200)
                .height(700)
                .title("Latency by Percentile Distribution")
                .xAxisTitle("Percentiles")
                .yAxisTitle("Latency (milliseconds)")
                .build();

        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

        chart.getStyler().setPlotGridLinesVisible(true);

        chart.getStyler().setYAxisTickMarkSpacingHint(15);
        chart.getStyler().setXAxisTickMarkSpacingHint(10);

        chart.getStyler().setXAxisMax(100.0);
        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setAxisTickMarkLength(15);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.99);

        return chart;

    }

    public void plot99(List<Double> xData, List<Double> yData) throws IOException {
        XYChart chart = buildCommonChart();

        /*
         * This shows only the > 90 percentile, so set te minimum
         * accordingly.
         */

        chart.getStyler().setXAxisMin(99.0);

        // Series
        XYSeries series = chart.addSeries(SERIES_NAME, xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.DIAMOND);
        series.setLineStyle(SeriesLines.SOLID);



        BitmapEncoder.saveBitmap(chart, baseName + "_99.png", BitmapEncoder.BitmapFormat.PNG);
    }

    public void plot90(List<Double> xData, List<Double> yData) throws IOException {
        XYChart chart = buildCommonChart();

        /*
         * This shows only the > 90 percentile, so set te minimum
         * accordingly.
         */

        chart.getStyler().setXAxisMin(90.0);

        // Series
        XYSeries series = chart.addSeries(SERIES_NAME, xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.DIAMOND);
        series.setLineStyle(SeriesLines.SOLID);



        BitmapEncoder.saveBitmap(chart, baseName + "_90.png", BitmapEncoder.BitmapFormat.PNG);
    }

    public void plotAll(List<Double> xData, List<Double> yData) throws IOException {

        // Create Chart
        XYChart chart = buildCommonChart();


         /*
         * This shows almost everything so set te minimum
         * accordingly.
         */
        chart.getStyler().setXAxisMin(5.0);

        // Series
        XYSeries series = chart.addSeries(SERIES_NAME, xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.DIAMOND);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_all.png", BitmapEncoder.BitmapFormat.PNG);

    }


    public void plot(List<Double> xData, List<Double> yData) throws IOException {
        plotAll(xData, yData);
        plot90(xData, yData);
        plot99(xData, yData);

    }
}
