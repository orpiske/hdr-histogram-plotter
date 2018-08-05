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
import org.apache.commons.io.FilenameUtils;

import java.io.*;

/**
 * The CustomHistogramLogProcessor does not _seem_ to provide an way to set its parameters. Therefore
 * just wrap it over its main method.
 */
public class HdrLogProcessorWrapper {
    public static final String DEFAULT_UNIT_RATE = "1";

    public HdrLogProcessorWrapper() {
    }


    public String convertLog(final Histogram histogram, final String path) throws IOException {
        final String csvFile = FilenameUtils.removeExtension(path) + ".csv";

        try (final PrintStream newOutStream = new PrintStream(new BufferedOutputStream(new FileOutputStream(csvFile)))) {

            /*
             * By default it prints on stdout. Since it does not seem to provide an easy
             * way to save to a file via API, then just replace the stdout for saving the
             * CSV data.
             */

            histogram.outputPercentileDistribution(newOutStream, 5, 1.0,
                    true);

        }

        return csvFile;
    }

    public String[] convertLog(final Histogram histogram, final String path, long knownCO) throws IOException {
        String uncorrectedCsv = convertLog(histogram, path);

        Histogram corrected = histogram.copyCorrectedForCoordinatedOmission(knownCO);
        String correctedCsv = convertLog(corrected, path);


        return new String[] {uncorrectedCsv, correctedCsv};
    }
}
