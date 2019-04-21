package edu.example.ssf.mma;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import edu.example.ssf.mma.config.ConfigApp;
import edu.example.ssf.mma.data.CurrentTickData;
import edu.example.ssf.mma.data.LocalDataStorage;
import edu.example.ssf.mma.data.MeasurementEntry;


public class D3SHttpClient {

    private RequestQueue queue;
    private static Context ctx;
    private static D3SHttpClient instance;

    public static synchronized D3SHttpClient getInstance(Context context) {
        if (instance==null) {
            instance = new D3SHttpClient(context);
        }
        return instance;
    }

    public static synchronized D3SHttpClient getInstance(){
        if (instance == null) throw new IllegalStateException("Instance not initialized from MainActivity");
        return instance;
    }

    private D3SHttpClient(Context context){
        ctx = context;
        queue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(queue == null){
            queue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return queue;
    }

    public void sendPing(String uuid) {

        String url = ConfigApp.backendURL + "ping?id=" + uuid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    LocalDataStorage.setEarthquake(response.get("description").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);

    }

    public void unregister(String uuid) {

        String url = ConfigApp.backendURL + "unregister?id=" + uuid;

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, null, null);

        queue.add(stringRequest);
    }

    public void  register(){

        String url = ConfigApp.backendURL + "register"
                + "?latitude=" + CurrentTickData.GPSlat
                + "&longitude=" + CurrentTickData.GPSlon
                + "&samplingRate=60";

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    LocalDataStorage.setUuid(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(stringRequest);
    }

    public synchronized void addMeasureEntries(String uuid, MeasurementEntry measurementEntry) throws JSONException {
        String url = ConfigApp.backendURL + "addMeasurement?id=" + uuid;

        Gson gson = new GsonBuilder().create();
        String requestJson = gson.toJson(measurementEntry);
        JSONObject requestBody = new JSONObject(requestJson);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, requestBody,  null, null);

        queue.add(jsonObjectRequest);
    }
}
