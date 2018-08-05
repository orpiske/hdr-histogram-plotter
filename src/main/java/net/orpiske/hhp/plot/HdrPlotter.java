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
import org.knowm.xchart.XYSeries;
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


    private void plotSingleAt(final List<Double> xData, final List<Double> yData, final Double min, final String fileName)
            throws IOException {
        XYChart chart = buildCommonChart();

        /*
         * This shows only the > 90 percentile, so set te minimum
         * accordingly.
         */

        chart.getStyler().setXAxisMin(min);

        // Series
        XYSeries series = chart.addSeries(getChartProperties().getSeriesName(), xData, yData);

        series.setLineColor(XChartSeriesColors.BLUE);
        series.setMarkerColor(Color.LIGHT_GRAY);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
    }

    protected void plot99(final List<Double> xData, final List<Double> yData) throws IOException {
        plotSingleAt(xData, yData, 99.0, baseName + "_99.png");
    }

    protected void plot90(final List<Double> xData, final List<Double> yData) throws IOException {
        plotSingleAt(xData, yData, 90.0, baseName + "_90.png");
    }

    protected void plotAll(final List<Double> xData, final List<Double> yData) throws IOException {
        plotSingleAt(xData, yData, 5.0, baseName + "_all.png");
    }

    protected void plotAllCo(HdrDataCO data, final Double min, final String fileName) throws IOException {
        // Create Chart
        XYChart chart = buildCommonChart();

        /*
         * This shows almost everything so set te minimum
         * accordingly.
         */
        chart.getStyler().setXAxisMin(min);

        // Series
        XYSeries serviceTime = chart.addSeries("Uncorrected " + getChartProperties().getSeriesName().toLowerCase(),
                data.getPercentile(), data.getValue());

        serviceTime.setLineColor(XChartSeriesColors.RED);
        serviceTime.setMarkerColor(Color.LIGHT_GRAY);
        serviceTime.setMarker(SeriesMarkers.NONE);
        serviceTime.setLineStyle(SeriesLines.SOLID);

        // Series
        XYSeries responseTime = chart.addSeries("Corrected " + getChartProperties().getSeriesName().toLowerCase(),
                data.getCorrected().getPercentile(), data.getCorrected().getValue());

        responseTime.setLineColor(XChartSeriesColors.BLUE);
        responseTime.setMarkerColor(Color.LIGHT_GRAY);
        responseTime.setMarker(SeriesMarkers.NONE);
        responseTime.setLineStyle(SeriesLines.SOLID);

        BitmapEncoder.saveBitmap(chart, fileName, BitmapEncoder.BitmapFormat.PNG);
    }

    protected void plotAll(HdrDataCO data) throws IOException {
        plotAllCo(data, 5.0, baseName + "_all.png");
    }

    protected void plot90(HdrDataCO data) throws IOException {
        plotAllCo(data, 90.0, baseName + "_90.png");
    }

    protected void plot99(HdrDataCO data) throws IOException {
        plotAllCo(data, 99.0, baseName + "_99.png");
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
            plot90((HdrDataCO) hdrData);
            plot99((HdrDataCO) hdrData);
        }
        else {
            plot(hdrData.getPercentile(), hdrData.getValue());
        }
    }
}
