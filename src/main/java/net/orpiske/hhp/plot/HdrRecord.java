package net.orpiske.hhp.plot;

public class HdrRecord {
    private final double percentile;
    private final double value;

    public HdrRecord(double percentile, double value) {
        this.percentile = percentile;
        this.value = value;
    }

    public double getPercentile() {
        return percentile;
    }

    public double getValue() {
        return value;
    }
}
