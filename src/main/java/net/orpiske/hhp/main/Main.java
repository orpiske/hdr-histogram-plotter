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
package net.orpiske.hhp.main;

import net.orpiske.hhp.plot.*;
import net.orpiske.hhp.utils.Constants;
import org.HdrHistogram.Histogram;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class Main {
    private static CommandLine cmdLine;
    private static String fileName;
    private static String timeUnit;
    private static String unitRate;
    private static String knownCO;

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
        CommandLineParser parser = new DefaultParser();

        Options options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("f", "file", true, "file to plot");
        options.addOption("t", "time-unit", true, "time unit to use (milliseconds, microseconds, etc)");
        options.addOption("r", "unit-rate", true, "the unit rate to use (default = 1)");
        options.addOption("", "time-unit", true, "time unit to use (milliseconds, microseconds, etc)");

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

        timeUnit = cmdLine.getOptionValue('t');
        if (timeUnit == null) {
            help(options, -1);
        }

        unitRate = cmdLine.getOptionValue('r');
        if (unitRate == null) {
            unitRate = HdrLogProcessorWrapper.DEFAULT_UNIT_RATE;
        }
    }

    public static void main(String[] args) {
        processCommand(args);

        try {
            // HDR Converter
            HdrLogProcessorWrapper processorWrapper;

            processorWrapper = new HdrLogProcessorWrapper();

            Histogram histogram = Util.getAccumulated(new File(fileName));

            HdrData hdrData = processorWrapper.convertLog(histogram, fileName);

            // HdrPlotter
            HdrPlotter plotter = new HdrPlotter(FilenameUtils.removeExtension(fileName), timeUnit);
            plotter.plot(hdrData);

            HdrPropertyWriter hdrPropertyWriter = new HdrPropertyWriter();

            hdrPropertyWriter.postProcess(histogram, fileName);

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        System.exit(1);
    }


}
