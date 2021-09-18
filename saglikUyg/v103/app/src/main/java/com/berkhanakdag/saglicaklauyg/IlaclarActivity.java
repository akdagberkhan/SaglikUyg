package com.berkhanakdag.saglicaklauyg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.IlaclarAdapter;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IlaclarActivity extends AppCompatActivity {

    private ArrayList<String> ilacAdList,ilacKullanim,ilacKullanimA,baslangicT;
    private SunucuAdres sunucuAdres;
    IlaclarAdapter ilaclarAdapter;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilaclar);

        sunucuAdres = new SunucuAdres();
        uri = sunucuAdres.getSunucuIp()+"/saglikAPI/saglik.php";

        ilacAdList=new ArrayList<>();
        ilacKullanim=new ArrayList<>();
        ilacKullanimA=new ArrayList<>();
        baslangicT=new ArrayList<>();
        bilgiler();

        RecyclerView recyclerView=findViewById(R.id.rcycIlaclar);
        recyclerView.setLayoutManager(new LinearLayoutManager(IlaclarActivity.this));
        recyclerView.setHasFixedSize(true);
        ilaclarAdapter = new IlaclarAdapter(ilacAdList,ilacKullanim,ilacKullanimA,baslangicT);
        recyclerView.setAdapter(ilaclarAdapter);
    }

    private void bilgiler()
    {

        RequestQueue requestQueue= Volley.newRequestQueue(IlaclarActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray= new JSONArray(response);
                    for(int i =0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        ilacAdList.add(jsonObject.getString("ilacAd"));
                        ilacKullanim.add(jsonObject.getString("ilacKullanim"));
                        ilacKullanimA.add(jsonObject.getString("ilacKullanimAralik"));
                        baslangicT.add(jsonObject.getString("baslangicTarih"));
                        ilaclarAdapter.notifyDataSetChanged();
                    }
                    Toast.makeText(IlaclarActivity.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap=new HashMap<>();

                stringMap.put("islem","3");
                stringMap.put("kMail",AnasayfaActivity.getGelenMail());
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}