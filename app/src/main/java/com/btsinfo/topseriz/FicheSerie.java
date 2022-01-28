package com.btsinfo.topseriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FicheSerie extends AppCompatActivity {

    private ArrayList<Series> listeSerie;
    private ListView lvSerie;
    private Button btDejaVu;
    private Button btAVoir;
    private StringRequest stringRequest;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private String id;
    private Series item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_serie);

        lvSerie = (ListView) findViewById(R.id.lvSerie);
        listeSerie = new ArrayList<Series>();
        btAVoir=(Button) findViewById(R.id.btAVoir);
        btDejaVu=(Button) findViewById(R.id.btDejaVu);

        Intent dIntent = getIntent();
        id = dIntent.getStringExtra("id");
        String id_usr = dIntent.getStringExtra("id_usr");

        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        chargerInfo();
    }

    private void chargerInfo() {
        stringRequest = new StringRequest(Request.Method.POST, BDPages.ficheSerie_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray j = new JSONArray(response);
                    for (int i = 0; i < j.length(); i++) {
                        JSONObject obj = j.getJSONObject(i);
                        item = new Series(
                                obj.getInt("id"),
                                obj.getString("titre"),
                                obj.getString("affiche"),
                                obj.getString("annee"),
                                obj.getString("synopsis"),
                                obj.getString("genre"),
                                obj.getString("acteur"));
                        listeSerie.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAdapterSerie(listeSerie);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(id));
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(FicheSerie.this);
        requestQueue.add(jsonArrayRequest);
    }
    private void setAdapterSerie(ArrayList<Series> listeSerie){
        listeAdapterDetail listeAdapter = new listeAdapterDetail(this,listeSerie);
        lvSerie.setAdapter(listeAdapter);
    }

}



