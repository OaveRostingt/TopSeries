package com.btsinfo.topseriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FicheSerie extends AppCompatActivity {

    private ArrayList<Series> listeSeries;
    private Button btDejaVu;
    private Button btAVoir;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_serie);

        listeSeries = new ArrayList<>();
        btAVoir=(Button) findViewById(R.id.btAVoir);
        btDejaVu=(Button) findViewById(R.id.btDejaVu);

        Intent dIntent = getIntent();
        id = dIntent.getStringExtra("id");
        String id_usr = dIntent.getStringExtra("id_usr");

        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        chargerInfo();
    }

    private void chargerInfo() {
        jsonArrayRequest = new JsonArrayRequest(BDPages.ficheSerie_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Series uneSerie = new Series(
                                jsonObject.getInt("id"),
                                jsonObject.getString("titre"),
                                jsonObject.getString("affiche"),
                                jsonObject.getString("annee"),
                                jsonObject.getString("synopsis"),
                                jsonObject.getString("genre"),
                                jsonObject.getString("acteur"));
                        listeSeries.add(uneSerie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("id", id);
                return param;
            }
        };
        requestQueue = Volley.newRequestQueue(FicheSerie.this);
        requestQueue.add(jsonArrayRequest);
    }

}
