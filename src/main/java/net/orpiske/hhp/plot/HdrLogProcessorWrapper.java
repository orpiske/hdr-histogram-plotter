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

import org.HdrHistogram.Histogram;

import java.io.*;


public class HdrLogProcessorWrapper {
    public static final String DEFAULT_UNIT_RATE = "1";

    public HdrLogProcessorWrapper() {
    }

    private void addHdr(HdrData hdrData, Double percentile, Double value) {
        hdrData.getPercentile().add(percentile);
        hdrData.getValue().add(value);
    }


    public HdrData convertLog(final Histogram histogram, final String path, double scalingRatio) throws IOException {
        HdrData ret = new HdrData();

        histogram.recordedValues().forEach(value -> addHdr(ret, value.getPercentile(),
                value.getDoubleValueIteratedTo()));

        return ret;
    }

    public HdrData convertLog(final Histogram histogram, final String path) throws IOException {
        return convertLog(histogram, path, 1.0);

    }

    public HdrData[] convertLog(final Histogram histogram, final String path, long knownCO, double scalingRatio) throws IOException {
        HdrData uncorrectedCsv = convertLog(histogram, path);

        Histogram corrected = histogram.copyCorrectedForCoordinatedOmission(knownCO);
        HdrData correctedCsv = convertLog(corrected, path, scalingRatio);


        return new HdrData[] {uncorrectedCsv, correctedCsv};
    }

    public HdrData[] convertLog(final Histogram histogram, final String path, long knownCO) throws IOException {
        return convertLog(histogram, path, knownCO, 1.0);
    }


}
