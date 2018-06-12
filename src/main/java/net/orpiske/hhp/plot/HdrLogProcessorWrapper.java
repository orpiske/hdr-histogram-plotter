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
import org.apache.commons.io.FilenameUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The CustomHistogramLogProcessor does not _seem_ to provide an way to set its parameters. Therefore
 * just wrap it over its main method.
 */
public class HdrLogProcessorWrapper {
    public static final String DEFAULT_UNIT_RATE = "1";
    private final String unitRatio;

    public HdrLogProcessorWrapper(String unitRatio) {
        this.unitRatio = unitRatio;
    }

    public String convertLog(final String path) throws IOException {
        String args[] = {
                "-i", path,
                "-outputValueUnitRatio", unitRatio,
                "-csv"
        };

        String csvFile = FilenameUtils.removeExtension(path) + ".csv";
        PrintStream oldOut = System.out;

        try (FileOutputStream fileStream = new FileOutputStream(csvFile)) {
            PrintStream newOutStream = new PrintStream(fileStream);

            /*
             * By default it prints on stdout. Since it does not seem to provide an easy
             * way to save to a file via API, then just replace the stdout for saving the
             * CSV data.
             */

            System.setOut(newOutStream);

            CustomHistogramLogProcessor processor = new CustomHistogramLogProcessor(args);
            processor.run();

            /*
             * Restore it after use
             */

        } finally {
            System.setOut(oldOut);
        }


        return csvFile;
    }

    public String[] convertLog(final String path, final String knownCO) throws IOException {
        String uncorrectedCsv = convertLog(path);

        if (uncorrectedCsv == null) {
            throw new IOException("Converted uncorrected file was not found");
        }


        String args[] = {
                "-i", path,
                "-outputValueUnitRatio", unitRatio,
                "-correctLogWithKnownCoordinatedOmission", knownCO,
                "-csv"
        };

        String correctedCsv = FilenameUtils.removeExtension(path) + "-corrected.csv";
        PrintStream oldOut = System.out;

        try (FileOutputStream fileStream = new FileOutputStream(correctedCsv)) {
            PrintStream newOutStream = new PrintStream(fileStream);

            /*
             * By default it prints on stdout. Since it does not seem to provide an easy
             * way to save to a file via API, then just replace the stdout for saving the
             * CSV data.
             */

            System.setOut(newOutStream);

            CustomHistogramLogProcessor processor = new CustomHistogramLogProcessor(args);
            processor.run();

            /*
             * Restore it after use
             */

        } finally {
            System.setOut(oldOut);
        }


        return new String[] {uncorrectedCsv, correctedCsv};
    }
}
