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


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class HdrReader {
    private static final Logger logger = LoggerFactory.getLogger(HdrReader.class);

    public HdrData read(String filename) throws IOException {
        Reader in = new FileReader(filename);

        HdrData ret = new HdrData();
        List<Double> value = ret.getValue();
        List<Double> percentile = ret.getPercentile();

        Iterable<CSVRecord> records = CSVFormat.RFC4180
                .withCommentMarker('#')
                .withFirstRecordAsHeader()
                .parse(in);


        for (CSVRecord record : records) {
            String valueStr = record.get(0);
            String percentileStr = record.get(1);

            logger.debug("Value: {}", valueStr);
            logger.debug("Percentile: {}", percentileStr);

            value.add(Double.parseDouble(valueStr));
            percentile.add(Double.parseDouble(percentileStr) * 100);
        }

        return ret;
    }
}
