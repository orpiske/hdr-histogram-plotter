package net.orpiske.hhp.plot;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class HdrReader {

    public HdrData read(String filename) throws FileNotFoundException, IOException {
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

            value.add(Double.parseDouble(valueStr));
            percentile.add(Double.parseDouble(percentileStr) * 100);
        }

        return ret;
    }
}
