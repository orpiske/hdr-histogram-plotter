package net.orpiske.hhp.plot;

import org.HdrHistogram.Histogram;

import java.io.File;

/**
 * An interface for post-processing of Histogram files
 */
public interface HistogramHandler {
    /**
     * Post process the histogram file
     * @param histogramFile the histogram file
     * @throws Exception Implementation specific
     */
    void handle(final Histogram histogram, final File histogramFile) throws Exception;
}
