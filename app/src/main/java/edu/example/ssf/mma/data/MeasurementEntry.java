package edu.example.ssf.mma.data;

import java.util.List;

public class MeasurementEntry {

    private List<Float> accelerationX;
    private List<Float> accelerationY;
    private List<Float> accelerationZ;

    private long time;

    public List<Float> getValuesX() {
        return accelerationX;
    }

    public void setValuesX(List<Float> valuesX) {
        this.accelerationX = valuesX;
    }

    public List<Float> getValuesY() {
        return accelerationY;
    }

    public void setValuesY(List<Float> valuesY) {
        this.accelerationY = valuesY;
    }

    public List<Float> getValuesZ() {
        return accelerationZ;
    }

    public void setValuesZ(List<Float> valuesZ) {
        this.accelerationZ = valuesZ;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
