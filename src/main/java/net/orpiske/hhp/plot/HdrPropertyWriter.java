package net.orpiske.hhp.plot;

import org.HdrHistogram.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * A post processor that writes latency data properties to a file
 */
public class HdrPropertyWriter implements HdrPostProcessor {
    static class DefaultHistogramHandler implements HistogramHandler {
        private static final Logger logger = LoggerFactory.getLogger(DefaultHistogramHandler.class);

        private static void doSave(File histogramFile, EncodableHistogram eh) throws IOException {
            Properties prop = new Properties();

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
                prop.setProperty("latency99999th", Long.toString(ah.getValueAtPercentile(99.999)));
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
                    prop.setProperty("latency99999th", Double.toString(dh.getValueAtPercentile(99.999)));
                    prop.setProperty("latencyStdDeviation", Double.toString(dh.getStdDeviation()));
                    prop.setProperty("latencyTotalCount", Long.toString(dh.getTotalCount()));
                    prop.setProperty("latencyMean", Double.toString(dh.getMean()));
                }
            }

            File outFile = new File(histogramFile.getParentFile(), "latency.properties");
            try (OutputStream fos = new BufferedOutputStream(new FileOutputStream(outFile))) {
                prop.store(fos, "hdr-histogram-plotter");
            }
        }

        @Override
        public void handle(final Histogram accumulatedHistogram, final File histogramFile) throws Exception {
            logger.trace("Writing properties to {}/latency.properties", histogramFile.getPath());

            doSave(histogramFile, accumulatedHistogram);
        }
    }



    /**
     * Save a summary of the analyzed rate data to a properties file named "latency.properties"
     * @param histogramFile the file to post-process
     * @throws IOException if unable to save
     */
    public void postProcess(final Histogram histogram, final File histogramFile) throws Exception {
        postProcess(histogram, histogramFile, new DefaultHistogramHandler());
    }

    /**
     * Save a summary of the analyzed rate data to a properties file named "latency.properties"
     * @param histogramFile the file to post-process
     * @throws IOException if unable to save
     */
    public void postProcess(final Histogram histogram, final String histogramFile) throws Exception {
        postProcess(histogram, new File(histogramFile));
    }


}
