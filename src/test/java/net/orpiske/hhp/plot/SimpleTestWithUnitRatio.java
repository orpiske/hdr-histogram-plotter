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
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleTestWithUnitRatio {

    private void plot(String fileName) throws Exception {
        // HDR Log Reader
        HdrLogProcessorWrapper processorWrapper = new HdrLogProcessorWrapper(10.0);

        Histogram histogram = Util.getAccumulated(new File(fileName));

        HdrData hdrData = processorWrapper.convertLog(histogram);

        HdrPlotter plotter = new HdrPlotter(FilenameUtils.removeExtension(fileName));

        plotter.plot(hdrData);

        HdrPropertyWriter hdrPropertyWriter = new HdrPropertyWriter();

        hdrPropertyWriter.postProcess(histogram, fileName, new HdrPropertyWriter.DefaultHistogramHandler(10.0));

    }


    @Test
    public void testPlot() throws Exception {
        String fileName = this.getClass().getResource("file-01.hdr").getPath();
        plot(fileName);

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


        File propertiesFile = new File(pngFileAll.getParentFile(), "latency.properties");
        assertTrue(propertiesFile.exists());
        assertTrue(propertiesFile.isFile());

        Properties ps = new Properties();

        ps.load(new FileInputStream(propertiesFile));

        assertEquals("2", ps.getProperty("latency99th"));
        assertEquals("6", ps.getProperty("latency9999th"));
        assertEquals("1", ps.getProperty("latency50th"));
        assertEquals("9916", ps.getProperty("latencyTotalCount"));
        assertEquals("6.1", ps.getProperty("latencyMaxValue"));
    }
}
