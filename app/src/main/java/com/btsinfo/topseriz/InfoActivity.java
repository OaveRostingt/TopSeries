package com.btsinfo.topseriz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    private ListView lvSeries;
    private ArrayList<Series> listeSeries;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private Button btDejaVu;
    private Button btAVoir;
    private String id;
    private Series uneSerie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        lvSeries = (ListView) findViewById(R.id.lvSeries);
        listeSeries = new ArrayList<>();
        btAVoir=(Button) findViewById(R.id.btAVoir);
        btDejaVu=(Button) findViewById(R.id.btDejaVu);

        btDejaVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dejaVu();
            }
        });

        btAVoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aVoir();
            }
        });

        chargerListe();

        lvSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startViewActivity(i);
            }
        });

        Intent IIntent = getIntent();
         id = IIntent.getStringExtra("id_usr");
    }


    public void dejaVu(){
        Intent intent = new Intent(getApplicationContext(), DejaVuActivity.class);
        intent.putExtra("id_usr", id);
        startActivity(intent);
    }
    public void aVoir(){
        Intent intent = new Intent(getApplicationContext(), AVoirActivity.class);
        intent.putExtra("id_usr", id);
        startActivity(intent);
    }
    private void startViewActivity(int i) {
        Series uneSerie = listeSeries.get(i);

        Intent intent = new Intent(this,FicheSerie.class);
        intent.putExtra("id",String.valueOf(uneSerie.getId()));
        intent.putExtra("id_usr", id);
        startActivity(intent);
    }
    private void chargerListe() {
        jsonArrayRequest = new JsonArrayRequest(BDPages.affiche_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        uneSerie = new Series(
                                jsonObject.getInt("id"),
                                jsonObject.getString("titre"),
                                jsonObject.getString("affiche"),
                                jsonObject.getString("annee"));
                        listeSeries.add(uneSerie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setAdapterSerie(listeSeries);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(InfoActivity.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void setAdapterSerie(ArrayList<Series> listeSeries) {
        ListAdapter listeAdapter = new listeAdapter(this, listeSeries);
        lvSeries.setAdapter(listeAdapter);
    }


}
