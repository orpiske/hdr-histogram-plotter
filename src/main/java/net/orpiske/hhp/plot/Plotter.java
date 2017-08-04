package net.orpiske.hhp.plot;


import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.awt.*;
import java.io.IOException;
import java.util.List;


public class Plotter {
    public static XYChart plot90(List<Double> xData, List<Double> yData) throws IOException {

        // Create Chart
        XYChart chart = new XYChartBuilder()
                    .width(1200)
                    .height(700)
                    .title("Latency by Percentile Distribution")
                    .xAxisTitle("Percentiles").yAxisTitle("Latency (microseconds)").build();

//        // Customize Chart
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

        // test
        chart.getStyler().setPlotGridLinesVisible(true);

        // Ok -
        chart.getStyler().setYAxisTickMarkSpacingHint(15);
        chart.getStyler().setXAxisTickMarkSpacingHint(10);
        chart.getStyler().setXAxisMin(90.0);
        chart.getStyler().setXAxisMax(100.0);
        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setAxisTickMarkLength(15);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.99);


        // Series
        XYSeries series = chart.addSeries("Fake Data", xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.ORANGE);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setLineStyle(SeriesLines.SOLID);

        // Bitmap
        chart.setTitle("Latency by Percentile Distribution");
//        chart.setYAxisTitle("Latency (microseconds)");
//        chart.setXAxisTitle("Percentiles");

        //XYSeries series = chart.addSeries("y(x)", null, yData);
        series.setMarker(SeriesMarkers.CIRCLE);

        BitmapEncoder.saveBitmap(chart, "./Sample_Chart_90", BitmapEncoder.BitmapFormat.PNG);


        return chart;
    }

    public static XYChart plotAll(List<Double> xData, List<Double> yData) throws IOException {

        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(1200)
                .height(700)
                .title("Latency by Percentile Distribution")
                .xAxisTitle("Percentiles").yAxisTitle("Latency (microseconds)").build();

//        // Customize Chart
        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

        // test
        chart.getStyler().setPlotGridLinesVisible(false);

        // Ok -
        chart.getStyler().setYAxisTickMarkSpacingHint(15);
        chart.getStyler().setXAxisTickMarkSpacingHint(10);
        chart.getStyler().setXAxisMin(5.0);
        chart.getStyler().setXAxisMax(100.0);
        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setAxisTickMarkLength(15);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.99);


        // Series
        XYSeries series = chart.addSeries("Fake Data", xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.ORANGE);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setLineStyle(SeriesLines.SOLID);

        // Bitmap
        chart.setTitle("Latency by Percentile Distribution");
//        chart.setYAxisTitle("Latency (microseconds)");
//        chart.setXAxisTitle("Percentiles");

        //XYSeries series = chart.addSeries("y(x)", null, yData);
        series.setMarker(SeriesMarkers.CIRCLE);

        BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);


        return chart;
    }


    public static void plot(List<Double> xData, List<Double> yData) throws IOException {
        plotAll(xData, yData);
        plot90(xData, yData);

    }
}
