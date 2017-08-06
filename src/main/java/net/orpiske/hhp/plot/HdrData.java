package net.orpiske.hhp.plot;

import java.util.LinkedList;
import java.util.List;

public class HdrData {
    private List<Double> value = new LinkedList<>();
    private List<Double> percentile = new LinkedList<>();

    public List<Double> getValue() {
        return value;
    }

    public List<Double> getPercentile() {
        return percentile;
    }
}
