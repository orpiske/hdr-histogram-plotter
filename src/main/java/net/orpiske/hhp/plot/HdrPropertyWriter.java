package net.orpiske.hhp.plot;

import org.HdrHistogram.AbstractHistogram;
import org.HdrHistogram.DoubleHistogram;
import org.HdrHistogram.EncodableHistogram;
import org.HdrHistogram.HistogramLogReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Writes latency data properties to a file
 */
public class HdrPropertyWriter {
    private static final Logger logger = LoggerFactory.getLogger(HdrPropertyWriter.class);

    private HdrPropertyWriter() {}

    /**
     * Save a summary of the analyzed rate data to a properties file named "rate.properties"
     * @param histogramFile the directory where the saved file will be located
     * @throws IOException if unable to save
     */
    public static void writeFrom(final String histogramFile) throws IOException {
        File in = new File(histogramFile);
        logger.trace("Writing properties to {}/latency.properties", in.getPath());

        Properties prop = new Properties();

        HistogramLogReader histogramLogReader = new HistogramLogReader(in);

        EncodableHistogram eh = histogramLogReader.nextIntervalHistogram();

        prop.setProperty("latencyStartTS", Long.toString(eh.getStartTimeStamp()));
        prop.setProperty("latencyEndTS", Long.toString(eh.getEndTimeStamp()));

        prop.setProperty("latencyMaxValue", Double.toString(eh.getMaxValueAsDouble()));


        if (eh instanceof AbstractHistogram) {
            AbstractHistogram ah = (AbstractHistogram) eh;

            prop.setProperty("latency50th", Long.toString(ah.getValueAtPercentile(50.0)));
            prop.setProperty("latency90th", Long.toString(ah.getValueAtPercentile(90.0)));
            prop.setProperty("latency95th", Long.toString(ah.getValueAtPercentile(95.0)));
            prop.setProperty("latency99th", Long.toString(ah.getValueAtPercentile(99.0)));
            prop.setProperty("latency999th", Long.toString(ah.getValueAtPercentile(99.9)));
            prop.setProperty("latency9999th", Long.toString(ah.getValueAtPercentile(99.99)));
            prop.setProperty("latencyStdDeviation", Double.toString(ah.getStdDeviation()));
            prop.setProperty("latencyTotalCount", Long.toString(ah.getTotalCount()));
            prop.setProperty("latencyMean", Double.toString(ah.getMean()));
        }
        else {
            if (eh instanceof DoubleHistogram) {
                DoubleHistogram dh = (DoubleHistogram) eh;

                prop.setProperty("latency50th", Double.toString(dh.getValueAtPercentile(50.0)));
                prop.setProperty("latency90th", Double.toString(dh.getValueAtPercentile(90.0)));
                prop.setProperty("latency95th", Double.toString(dh.getValueAtPercentile(95.0)));
                prop.setProperty("latency99th", Double.toString(dh.getValueAtPercentile(99.0)));
                prop.setProperty("latency999th", Double.toString(dh.getValueAtPercentile(99.9)));
                prop.setProperty("latency9999th", Double.toString(dh.getValueAtPercentile(99.99)));
                prop.setProperty("latencyStdDeviation", Double.toString(dh.getStdDeviation()));
                prop.setProperty("latencyTotalCount", Long.toString(dh.getTotalCount()));
                prop.setProperty("latencyMean", Double.toString(dh.getMean()));
            }
        }


        try (FileOutputStream fos = new FileOutputStream(new File(in.getParentFile(), "latency.properties"))) {
            prop.store(fos, "hdr-histogram-plotter");
        }
    }


}
