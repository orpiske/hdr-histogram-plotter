package net.orpiske.hhp.main;

import net.orpiske.hhp.plot.HdrData;
import net.orpiske.hhp.plot.HdrLogProcessorWrapper;
import net.orpiske.hhp.plot.HdrPlotter;
import net.orpiske.hhp.plot.HdrReader;
import net.orpiske.hhp.utils.Constants;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;

public class Main {
    private static CommandLine cmdLine;
    private static Options options;

    private static String fileName;
    private static String seriesName;

    /**
     * Prints the help for the action and exit
     * @param options the options object
     * @param code the exit code
     */
    private static void help(final Options options, int code) {
        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp(Constants.BIN_NAME, options);
        System.exit(code);
    }

    private static void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("f", "file", true, "file to plot");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            help(options, -1);
        }

        if (cmdLine.hasOption("help")) {
            help(options, 0);
        }

        fileName = cmdLine.getOptionValue('f');
        if (fileName == null) {
            help(options, -1);
        }
    }

    public static void main(String[] args) {
        processCommand(args);

        try {
            // HDR Converter
            HdrLogProcessorWrapper processorWrapper = new HdrLogProcessorWrapper();

            String csvFile = processorWrapper.convertLog(fileName);

            // CSV Reader
            HdrReader reader = new HdrReader();

            HdrData hdrData = reader.read(csvFile);

            // HdrPlotter
            HdrPlotter plotter = new HdrPlotter(FilenameUtils.removeExtension(fileName));
            plotter.plot(hdrData.getPercentile(), hdrData.getValue());

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }


}
