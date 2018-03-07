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

import net.orpiske.hhp.utils.ConsoleHijacker;
import org.HdrHistogram.HistogramLogProcessor;
import org.apache.commons.io.FilenameUtils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The HistogramLogProcessor does not _seem_ to provide an way to set its parameters. Therefore
 * just wrap it over its main method.
 */
public class HdrLogProcessorWrapper {
    public static final String DEFAULT_UNIT_RATE = "1";
    private final String unitRatio;

    public HdrLogProcessorWrapper(String unitRatio) {
        this.unitRatio = unitRatio;
    }

    public String convertLog(String path) throws IOException {
        String args[] = {
                "-i", path,
                "-outputValueUnitRatio", unitRatio,
                "-csv"
        };

        String csvFile = FilenameUtils.removeExtension(path) + ".csv";

        try (FileOutputStream fileStream = new FileOutputStream(csvFile)) {
            ConsoleHijacker.getInstance().start(fileStream);

            HistogramLogProcessor processor = new HistogramLogProcessor(args);

            processor.run();
        } finally {
            /*
             * Restore it after use
             */
            ConsoleHijacker.getInstance().stop();
        }

        return csvFile;
    }
}
