package com.btsinfo.topseriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etLogin;
    private EditText etMdp;
    private Button btCo;
    private Button btInscr;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etMdp = (EditText) findViewById(R.id.etMdp);
        btCo = (Button) findViewById(R.id.btCo);
        btInscr= (Button)findViewById(R.id.btinscr);

        btCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRecap();
            }
        });
        btInscr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toIncr();
            }
        } );
    }
    public void toIncr(){
        Intent intent = new Intent(getApplicationContext(), InscActivity.class);
        startActivity(intent);
    }
    public void toRecap(){

        String login = etLogin.getText().toString().trim();
        String mdp = etMdp.getText().toString().trim();

        if (!login.equalsIgnoreCase("") && !mdp.equalsIgnoreCase(""))
        {
            stringRequest = new StringRequest(Request.Method.POST, BDPages.login_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("DBEres", response);
                    if (StringUtils.contains(response,"utilisateur")){
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                        intent.putExtra("id_usr",response);

                        startActivity(intent);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("login", login);
                    param.put("pwd", mdp);
                    return param;
                }
            };
            requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.add(stringRequest);

        }
        else {
            Toast.makeText(getApplicationContext(), "Les champs ne sont pas remplis", Toast.LENGTH_SHORT).show();
        }
    }


}