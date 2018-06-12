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


import net.orpiske.hhp.plot.exceptions.HdrEmptyDataSet;
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


/**
 * The plotter for HDR histograms
 */
@SuppressWarnings("unused")
public class HdrPlotter extends AbstractHdrPlotter {
    private final String baseName;

    public HdrPlotter(final String baseName) {
        this.baseName = baseName;

        getChartProperties().setyTitle("microseconds");
    }

    public HdrPlotter(final String baseName, final String timeUnit) {
        this.baseName = baseName;

        getChartProperties().setyTitle(timeUnit);
    }


    protected void plot99(List<Double> xData, List<Double> yData) throws IOException {
        XYChart chart = buildCommonChart();

        /*
         * This shows only the > 90 percentile, so set te minimum
         * accordingly.
         */

        chart.getStyler().setXAxisMin(99.0);

        // Series
        XYSeries series = chart.addSeries(getChartProperties().getSeriesName(), xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_99.png", BitmapEncoder.BitmapFormat.PNG);
    }

    protected void plot90(List<Double> xData, List<Double> yData) throws IOException {
        XYChart chart = buildCommonChart();

        /*
         * This shows only the > 90 percentile, so set te minimum
         * accordingly.
         */

        chart.getStyler().setXAxisMin(90.0);

        // Series
        XYSeries series = chart.addSeries(getChartProperties().getSeriesName(), xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_90.png", BitmapEncoder.BitmapFormat.PNG);
    }

    protected void plotAll(List<Double> xData, List<Double> yData) throws IOException {
        // Create Chart
        XYChart chart = buildCommonChart();

         /*
         * This shows almost everything so set te minimum
         * accordingly.
         */
        chart.getStyler().setXAxisMin(5.0);

        // Series
        XYSeries series = chart.addSeries(getChartProperties().getSeriesName(), xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_all.png", BitmapEncoder.BitmapFormat.PNG);
    }

    protected void plotAll(HdrDataCO data) throws IOException {
        // Create Chart
        XYChart chart = buildCommonChart();

        /*
         * This shows almost everything so set te minimum
         * accordingly.
         */
        chart.getStyler().setXAxisMin(5.0);

        // Series
        XYSeries serviceTime = chart.addSeries("Service time " + getChartProperties().getSeriesName(),
                data.getPercentile(), data.getValue());

        serviceTime.setLineColor(XChartSeriesColors.BLUE);
        serviceTime.setMarkerColor(Color.LIGHT_GRAY);
        serviceTime.setMarker(SeriesMarkers.NONE);
        serviceTime.setLineStyle(SeriesLines.SOLID);

        // Series
        XYSeries responseTime = chart.addSeries("Response time " + getChartProperties().getSeriesName(),
                data.getCorrected().getPercentile(), data.getCorrected().getValue());

        responseTime.setLineColor(XChartSeriesColors.BLUE);
        responseTime.setMarkerColor(Color.LIGHT_GRAY);
        responseTime.setMarker(SeriesMarkers.NONE);
        serviceTime.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, baseName + "_all.png", BitmapEncoder.BitmapFormat.PNG);
    }


    /**
     * Plots the HDR histogram
     * @param xData the data for the x axis
     * @param yData the data for tye y axis
     * @throws IOException if unable to save the bitmap file
     * @throws HdrEmptyDataSet if the data set is empty
     */
    private void plot(List<Double> xData, List<Double> yData) throws IOException, HdrEmptyDataSet {
        if (xData == null || xData.size() == 0) {
            throw new HdrEmptyDataSet("The 'X' column data set is empty");
        }

        if (yData == null || yData.size() == 0) {
            throw new HdrEmptyDataSet("The 'Y' column data set is empty");
        }

        plotAll(xData, yData);
        plot90(xData, yData);
        plot99(xData, yData);
    }

    @Override
    public void plot(final HdrData hdrData) throws IOException, HdrEmptyDataSet {
        if (hdrData instanceof HdrDataCO) {
            plotAll((HdrDataCO) hdrData);
        }
        else {
            plot(hdrData.getPercentile(), hdrData.getValue());
        }
    }
}
