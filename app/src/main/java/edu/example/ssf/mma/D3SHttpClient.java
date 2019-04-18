package edu.example.ssf.mma;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


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
        /*
        String url = "";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
        */
    }

    public void unregister(String uuid) {
    }

    public void  register (){

    }

    public void addMeasureEntries(String uuid) {
    }
}
