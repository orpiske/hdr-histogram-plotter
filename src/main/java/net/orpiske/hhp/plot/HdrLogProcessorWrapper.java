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

    private void addHdr(HdrData hdrData, double percentile, double value) {
        hdrData.add(new HdrRecord(percentile, value));
    }


    public HdrData convertLog(final Histogram histogram) {
        HdrData ret = new HdrData();

        histogram.recordedValues().forEach(value -> addHdr(ret, value.getPercentile(),
                value.getDoubleValueIteratedTo()));

        return ret;
    }

    public HdrData[] convertLog(final Histogram histogram, long knownCO) {
        HdrData uncorrectedCsv = convertLog(histogram);

        Histogram corrected = histogram.copyCorrectedForCoordinatedOmission(knownCO);
        HdrData correctedCsv = convertLog(corrected);

        return new HdrData[] {uncorrectedCsv, correctedCsv};
    }
}
