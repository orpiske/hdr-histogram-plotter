package net.orpiske.hhp.plot;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class HdrReader {

    public HdrData read(String filename) throws FileNotFoundException, IOException {
        CSVReader reader = new CSVReader(new FileReader(filename), ',');

        HdrData ret = new HdrData();
        List<Double> value = ret.getValue();
        List<Double> percentile = ret.getPercentile();


        reader.readNext();
        reader.readNext();

        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            // System.out.println("0: " + nextLine[0] + "->" + nextLine[1]);
            value.add(Double.parseDouble(nextLine[0]));
            percentile.add(Double.parseDouble(nextLine[1]) * 100);
        }

        return ret;
    }
}
