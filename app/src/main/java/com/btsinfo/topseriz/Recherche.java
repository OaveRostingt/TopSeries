package com.btsinfo.topseriz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Recherche extends AppCompatActivity {

    private ListView lvResutRech;
    private ArrayList<Series> listeSeries;
    private JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private Series uneSerie;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        lvResutRech = findViewById(R.id.lvResutRech);
        listeSeries = new ArrayList<>();



        Intent IIntent = getIntent();
        id = IIntent.getStringExtra("id_usr");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Recherchez ...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if( ! searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                menuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                chargerListe();

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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
        requestQueue = Volley.newRequestQueue(Recherche.this);
        requestQueue.add(jsonArrayRequest);
    }

    private void setAdapterSerie(ArrayList<Series> listeSeries) {
        ListAdapter listeAdapter = new listeAdapter(this, listeSeries);
        lvResutRech.setAdapter(listeAdapter);
        lvResutRech.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startViewActivity(i);
            }
        });
    }
    private void startViewActivity(int i) {
        Series uneSerie = listeSeries.get(i);

        Intent intent = new Intent(getApplicationContext(),FicheSerie.class);
        intent.putExtra("id",String.valueOf(uneSerie.getId()));
        intent.putExtra("id_usr", String.valueOf(id));
        startActivity(intent);
    }

}