/*
 * Copyright 2017 Otavio Rodolfo Piske
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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


public class HdrPlotter {
    private static final String SERIES_NAME = "Percentiles range";
    private String baseName;

    private int outputWidth = 1200;
    private int outputHeight = 700;
    private boolean plotGridLinesVisible = true;

    public HdrPlotter(final String baseName) {
        this.baseName = baseName;
    }

    private XYChart buildCommonChart() {

        // Create Chart
        XYChart chart = new XYChartBuilder()
                .width(outputWidth)
                .height(outputHeight)
                .title("Latency by Percentile Distribution")
                .xAxisTitle("Percentiles")
                .yAxisTitle("Latency (milliseconds)")
                .build();

        chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.WHITE));
        chart.getStyler().setChartBackgroundColor(Color.WHITE);
        chart.getStyler().setChartTitleBoxBackgroundColor(new Color(0, 222, 0));

        chart.getStyler().setPlotGridLinesVisible(plotGridLinesVisible);

        chart.getStyler().setYAxisTickMarkSpacingHint(15);
        chart.getStyler().setXAxisTickMarkSpacingHint(10);

        chart.getStyler().setXAxisMax(100.0);
        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setAxisTickMarkLength(15);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.99);

        return chart;

    }

    private void plot99(List<Double> xData, List<Double> yData) throws IOException {
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

    private void plot90(List<Double> xData, List<Double> yData) throws IOException {
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

    private void plotAll(List<Double> xData, List<Double> yData) throws IOException {

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

    public void setOutputWidth(int outputWidth) {
        this.outputWidth = outputWidth;
    }

    public void setOutputHeight(int outputHeight) {
        this.outputHeight = outputHeight;
    }

    public void setPlotGridLinesVisible(boolean plotGridLinesVisible) {
        this.plotGridLinesVisible = plotGridLinesVisible;
    }
}
