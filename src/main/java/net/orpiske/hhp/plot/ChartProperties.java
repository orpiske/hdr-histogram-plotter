package net.orpiske.hhp.plot;

public class ChartProperties {
    private String title = "Latency by Percentile Distribution";
    private String seriesName = "Percentiles range";
    private String xTitle;
    private String yTitle = "microseconds";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getxTitle() {
        return this.xTitle.substring(0, 1).toUpperCase() + xTitle.substring(1);
    }

    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
    }

    public String getyTitle() {
        return this.yTitle.substring(0, 1).toUpperCase() + yTitle.substring(1);
    }

    public void setyTitle(String yTitle) {
        this.yTitle = yTitle;
    }
}
