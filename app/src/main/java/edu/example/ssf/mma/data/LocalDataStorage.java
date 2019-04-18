package edu.example.ssf.mma.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the x, y and z values of the accelorometer while data is collected locally
 *
 * Also the sampling rate in Herz is stored
 */
public class LocalDataStorage {

    private static List<Float> xValues = new ArrayList<>();
    private static List<Float> yValues = new ArrayList<>();
    private static List<Float> zValues = new ArrayList<>();

    private static String uuid = "";

    public static void resetStorages(){
        xValues.clear();
        yValues.clear();
        zValues.clear();
    }

    public static void addDataSet(Float x, Float y, Float z){
        xValues.add(x);
        yValues.add(y);
        zValues.add(z);
    }

    public static String getUuid(){
        return uuid;
    }

    public static void setUuid(String uuid) {
        LocalDataStorage.uuid = uuid;
    }
}
