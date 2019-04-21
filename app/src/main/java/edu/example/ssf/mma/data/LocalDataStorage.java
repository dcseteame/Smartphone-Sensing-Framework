package edu.example.ssf.mma.data;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class stores the x, y and z values of the accelorometer while data is collected locally
 *
 * Also the sampling rate in Herz is stored
 */
public class LocalDataStorage {

    private static List<Float> xValues = new CopyOnWriteArrayList<>();
    private static List<Float> yValues = new CopyOnWriteArrayList<>();
    private static List<Float> zValues = new CopyOnWriteArrayList<>();

    private static float[] nullify = new float[3];

    private static String uuid = "";

    private static String stateLabel = "NOT YET SET";

    private static String earthquake = "No Earthquake";

    public static void resetStorages(){
        xValues.clear();
        yValues.clear();
        zValues.clear();

        nullify[0] = 0;
        nullify[1] = 0;
        nullify[2] = 0;
    }

    public static void addDataSet(Float x, Float y, Float z){
        xValues.add(x);
        yValues.add(y);
        zValues.add(z);
    }

    public static void setNullify(float x, float y, float z){
        nullify[0] = x;
        nullify[1] = y;
        nullify[2] = z;
    }

    public static float[] getNullify(){
        return nullify;
    }

    public static String getUuid(){
        return uuid;
    }

    public static void setUuid(String uuid) {
        LocalDataStorage.uuid = uuid;
    }

    public static void setEarthquake(String eq){
        earthquake = eq;
    }

    public static List<Float> getxValues() {
        return xValues;
    }

    public static void setxValues(List<Float> xValues) {
        LocalDataStorage.xValues = xValues;
    }

    public static List<Float> getyValues() {
        return yValues;
    }

    public static void setyValues(List<Float> yValues) {
        LocalDataStorage.yValues = yValues;
    }

    public static List<Float> getzValues() {
        return zValues;
    }

    public static void setzValues(List<Float> zValues) {
        LocalDataStorage.zValues = zValues;
    }

    public static void setStateLabel(String stateName) {
        stateLabel = stateName;
    }

    public static String getStateLabel() {
        return stateLabel;
    }

    public static String getEarthquake() {
        return earthquake;
    }
}
