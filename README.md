HDR Histogram Plotter: A Java library to plot HDR Histogram Files
============

Build Status
----

Build Status: [![Build Status](https://travis-ci.org/orpiske/hdr-histogram-plotter.svg?branch=master)](https://travis-ci.org/orpiske/hdr-histogram-plotter)

Introduction
----

This library reads compressed [HDR Histogram](https://hdrhistogram.github.io/HdrHistogram/) and plot them. It makes it
easier to integrate processing of those files into custom automation tools without relying on Excel, GNUPlot or
GoogleCharts.
It can be used as a command-line tool or it can be integrated in other projects as a library.


Building
----

To build the project run:

```
mvn clean install
```

To generate the delivery tarball run:

```
mvn -PDelivery clean package
```

If you maintain your own Maven repository, you can deploy this library using:

```
mvn deploy -DaltDeploymentRepository=libs-snapshot::default::http://hostname:8081/path/to/libs-snapshot-local
```


Using as Command Line Tool
----

Run:

```
./hdr-histogram-plotter -f /path/to/sample.hdr
```

Using as Library
----

Use:

```
// HDR Converter
HdrLogProcessorWrapper processorWrapper = new HdrLogProcessorWrapper();

String csvFile = processorWrapper.convertLog(fileName);

// CSV Reader
HdrReader reader = new HdrReader();

HdrData hdrData = reader.read(csvFile);

// HdrPlotter
HdrPlotter plotter = new HdrPlotter(FilenameUtils.removeExtension(fileName));
plotter.plot(hdrData.getPercentile(), hdrData.getValue());

```

Output
----

The HDR processing will generate 4 files: 

```
/path/to/sample_90.png
/path/to/sample_99.png
/path/to/sample_all.png
/path/to/sample.csv
/path/to/sample.hdr
```

The files are, in order:

* Plotted PNG file for the 90th percentile
* Plotted PNG file for the 99th percentile
* Plotted PNG file for the all data range
* Intermediary CSV file that can be used elsewhere


Samples
----
![90th percentile sample](doc/receiverd-latency_90.png)
![99th percentile sample](doc/receiverd-latency_99.png)
![All data range sample](doc/receiverd-latency_all.png)