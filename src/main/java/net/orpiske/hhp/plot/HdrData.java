package net.orpiske.hhp.plot;

import java.util.LinkedList;
import java.util.List;

public class HdrData {
    List<Double> value = new LinkedList<>();
    List<Double> percentile = new LinkedList<>();



    public List<Double> getValue() {
        return value;
    }

    public void setValue(List<Double> value) {
        this.value = value;
    }

    public List<Double> getPercentile() {
        return percentile;
    }

    public void setPercentile(List<Double> percentile) {
        this.percentile = percentile;
    }
}
