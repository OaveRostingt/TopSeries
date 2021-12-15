package com.btsinfo.topseriz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
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

public class InscActivity extends AppCompatActivity {


    private EditText etLogin;
    private EditText etPseudo;
    private EditText etMail;
    private EditText etMdp;
    private EditText etMdp2;
    private Button btInscr;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insc);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPseudo = (EditText) findViewById(R.id.etPseudo);
        etMail = (EditText) findViewById(R.id.etMail);
        etMdp = (EditText) findViewById(R.id.etMdp);
        etMdp2 = (EditText) findViewById(R.id.etMdp2);
        btInscr= (Button)findViewById(R.id.btInscr);

        btInscr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCo();
            }
        });
    }
    public void toCo(){
        String login = etLogin.getText().toString().trim();
        String pseudo = etPseudo.getText().toString().trim();
        String mail = etMail.getText().toString().trim();
        String mdp = etMdp.getText().toString().trim();
        String mdp2 = etMdp2.getText().toString().trim();

        if (!login.equalsIgnoreCase("")
                && !pseudo.equalsIgnoreCase("")
                && !mail.equalsIgnoreCase("")
                && !mdp.equalsIgnoreCase("")
                && !mdp2.equalsIgnoreCase(""))/*aucun champs vide*/
        {
            if (etMdp.getText().toString().equals(etMdp2.getText().toString())){/*2 mdp pareil*/
                stringRequest = new StringRequest(Request.Method.POST, BDPages.inscr_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DBEres", response);
                        if (StringUtils.contains(response, "Login")){
                            Toast.makeText(getApplicationContext(), "Login déjà utilisé", Toast.LENGTH_SHORT).show();}
                        else if (StringUtils.contains(response, "Email"))
                            Toast.makeText(getApplicationContext(), "Email non valide", Toast.LENGTH_SHORT).show();
                        else {
                            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                            intent.putExtra("reponse",response);
                            startActivity(intent);
                            finish();
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
                        param.put("pseudo", pseudo);
                        param.put("mail", mail);
                        param.put("mdp", mdp);
                        return param;
                    }
                };
                requestQueue = Volley.newRequestQueue(InscActivity.this);
                requestQueue.add(stringRequest);

            }
            else {
                Toast.makeText(getApplicationContext(), "Les mots de passe ne coincident pas", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Les champs ne sont pas remplis", Toast.LENGTH_SHORT).show();

        }

    }}