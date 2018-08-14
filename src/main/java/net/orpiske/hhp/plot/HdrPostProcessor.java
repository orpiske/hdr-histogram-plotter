package net.orpiske.hhp.plot;

import org.HdrHistogram.Histogram;

import java.io.File;
import java.io.IOException;


/**
 * Post processing of HDR files
 */
public interface HdrPostProcessor {

    /**
     * Post process an HDR file
     * @param histogramFile the file to post-process
     * @param handler An histogram handler to read/save/etc the histogram data
     * @throws Exception implementation-specific
     */
    default void postProcess(final Histogram histogram, final String histogramFile, final HistogramHandler handler) throws Exception {
        handler.handle(histogram, new File(histogramFile));
    }

    /**
     * Post process an HDR file
     * @param histogramFile the file to post-process
     * @param handler An histogram handler to read/save/etc the histogram data
     * @throws Exception implementation-specific
     */
    default void postProcess(final Histogram histogram, final File histogramFile, final HistogramHandler handler) throws Exception {
        handler.handle(histogram, histogramFile);
    }


    /**
     * Save a summary of the analyzed rate data to a properties file named "latency.properties"
     * @param histogramFile the file to post-process
     * @throws IOException implementation-specific
     */
    void postProcess(final Histogram histogram, final File histogramFile) throws Exception;

    /**
     * Save a summary of the analyzed rate data to a properties file named "latency.properties"
     * @param histogramFile the file to post-process
     * @throws IOException implementation-specific
     */
    void postProcess(final Histogram histogram, final String histogramFile) throws Exception;
}
