package net.orpiske.hhp.plot;

import org.HdrHistogram.HistogramLogProcessor;
import org.apache.commons.io.FilenameUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The HistogramLogProcessor does not _seem_ to provide an way to set its parameters. Therefore
 * just wrap it over its main method.
 */
public class HdrLogProcessorWrapper {

    public String convertLog(String path) throws IOException {
        String args[] = {
                "-i", path,
                "-outputValueUnitRatio", "1",
                "-csv"
        };

        String csvFile = FilenameUtils.removeExtension(path) + ".csv";
        PrintStream oldOut = System.out;

        try (FileOutputStream fileStream = new FileOutputStream(csvFile)) {
            PrintStream newOutStream = new PrintStream(fileStream);

            /*
             * By default it prints on stdout. Since it does not seem to provide an easy
             * way to save to a file via API, then just replay the stdout for saving the
             * CSV data.
             */

            System.setOut(newOutStream);

            HistogramLogProcessor processor = new HistogramLogProcessor(args);
            processor.run();

            /*
             * Restore it after use
             */

        } finally {
            System.setOut(oldOut);
        }


        return csvFile;
    }
}
