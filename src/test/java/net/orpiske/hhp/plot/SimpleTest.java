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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.File;

public class SimpleTest {

    private void plot(String fileName) throws Exception {
        // HDR Converter
        HdrLogProcessorWrapper processorWrapper = new HdrLogProcessorWrapper();


        String csvFile = processorWrapper.convertLog(fileName);

        // CSV Reader
        HdrReader reader = new HdrReader();

        HdrData hdrData = reader.read(csvFile);

        // HdrPlotter
        HdrPlotter plotter = new HdrPlotter(FilenameUtils.removeExtension(fileName));
        plotter.plot(hdrData.getPercentile(), hdrData.getValue());

        String csvFilename = FilenameUtils.removeExtension(fileName) + ".csv";
        String pngFilename = FilenameUtils.removeExtension(fileName) + ".png";
    }


    @Test
    public void name() throws Exception {
        String fileName = this.getClass().getResource("file-01.hdr").getPath();
        plot(fileName);

        String csvFilename = FilenameUtils.removeExtension(fileName) + ".csv";

        File csvFile = new File(csvFilename);
        assertTrue(csvFile.exists());
        assertTrue(csvFile.isFile());

        String pngFilename99 = FilenameUtils.removeExtension(fileName) + "_99.png";

        File pngFile99 = new File(pngFilename99);
        assertTrue(pngFile99.exists());
        assertTrue(pngFile99.isFile());

        String pngFilename90 = FilenameUtils.removeExtension(fileName) + "_90.png";

        File pngFile90 = new File(pngFilename90);
        assertTrue(pngFile90.exists());
        assertTrue(pngFile90.isFile());

        String pngFilenameAll = FilenameUtils.removeExtension(fileName) + "_all.png";

        File pngFileAll = new File(pngFilenameAll);
        assertTrue(pngFileAll.exists());
        assertTrue(pngFileAll.isFile());

    }
}
