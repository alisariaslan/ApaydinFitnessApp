package com.pakachu.apaydinfitness.helpers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pakachu.apaydinfitness.MyCustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONWorkbench {

    private String url = "http://bahadirduzcan.com.tr/pakachu/quick/select/";
    private String url2 = "http://bahadirduzcan.com.tr/pakachu/quick/";

    private Context mContext;
    public boolean finishStatus = false;

    public JSONWorkbench(Context context) {
        mContext = context;
    }

    public ArrayList<ArrayList> GET(String sql) {
        finishStatus = false;
        ArrayList<ArrayList> arrayListArrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    String status = respObj.getString("status");
                    String message = respObj.getString("message");
                    String data = respObj.getString("data");
                    try {
                        JSONArray jsonarray = new JSONArray(data);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            ArrayList<String> arrayList = new ArrayList<>();
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            Iterator<String> iter = jsonobject.keys();
                            while (iter.hasNext()) {
                                String key = iter.next();
                                try {
                                    arrayList.add(jsonobject.get(key).toString());
                                    Log.e("key", "key: " + jsonobject.get(key).toString());
                                } catch (JSONException e) {
                                    Log.e("parseError", "" + e);
                                }
                            }
                            arrayListArrayList.add(arrayList);
                        }
                        finishStatus = true;
                    } catch (JSONException e) {
                        Log.e("parseError", "" + e);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", "" + error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "\"select\" : true,\n" +
                        "\"quickSql\": \"" + sql + "\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent", "CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return arrayListArrayList;
    }

    public void ADDUSER(String... strings) {
        finishStatus = false;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus = true;
                new MyCustomDialog((Activity) mContext).Toast("Üye başarıyla eklendi.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext, "Hata: "+error, Toast.LENGTH_SHORT).show();
                Log.e("volley", "" + error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"INSERT INTO `uyeler` (`id`, `tarih`, `ad`, `yas`,`cinsiyet`, `kilo`, `boy`, `boyun`, `onkol`, `kol`, `bicep`, `omuz`, `gogus`, `karin`, `kalca`, `bacak`, `calf`, `antrenor`) VALUES (NULL, '" + strings[0] + "', '" + strings[1] + "', '" + strings[2] + "', '" + strings[3] + "', '" + strings[4] + "', '" + strings[5] + "', '" + strings[6] + "', '" + strings[7] + "', '" + strings[8] + "','" + strings[9] + "', '" + strings[10] + "', '" + strings[11] + "', '" + strings[12] + "', '" + strings[13] + "', '" + strings[14] + "', '" + strings[15] + "', '" + strings[16] + "')\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent", "CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void UPDATEUSER(String columnName, String text, String id) {
        finishStatus = false;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus = true;
                new MyCustomDialog((Activity) mContext).Toast("Üye başarıyla güncellendi");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext, "Hata: "+error, Toast.LENGTH_SHORT).show();
                Log.e("volley", "" + error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"UPDATE `uyeler` SET `" + columnName + "` = '" + text + "' WHERE `id` = " + id + ";\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent", "CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void DELETEUSER(String id) {
        finishStatus = false;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus = true;
                new MyCustomDialog((Activity) mContext).Toast("Üye başarıyla silindi");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", "" + error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"DELETE FROM uyeler WHERE id = " + id + "\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent", "CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ADDNOTIF(String... strings) {
        finishStatus = false;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus = true;
                new MyCustomDialog((Activity) mContext).Toast("Duyuru yayınlandı.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext, "Hata: "+error, Toast.LENGTH_SHORT).show();
                Log.e("volley", "" + error);
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"INSERT INTO `duyurular` (`id`, `text`) VALUES (NULL, '" + strings[0] + "')\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent", "CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void SET(String sql) {
        finishStatus = false;
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext, "Hata: "+error, Toast.LENGTH_SHORT).show();
                Log.e("volley", "" + error);
                new MyCustomDialog((Activity) mContext).Toast("Hata!");
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \" "+sql+" \"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent", "CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
