package com.berkhanakdag.saglicaklauyg;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.GonderilerimAdapter;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.Adapters.YorumlarAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GdetayActivity extends AppCompatActivity {

    private TextView txtBaslik,txtAciklama;
    private ImageView imgDetay;
    private EditText edtYorum;
    private Button btnGonder;
    private RecyclerView rcycYorumlar;
    private String uri,gonderiId;
    SunucuAdres sunucuAdres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdetay);

        ayorumId = new ArrayList<>();
        akullaniciId = new ArrayList<>();
        akullaniciAd = new ArrayList<>();
        ayorum = new ArrayList<>();
        akullaniciFoto = new ArrayList<>();
        agonderiId = new ArrayList<>();

        sunucuAdres=new SunucuAdres();
        uri = sunucuAdres.getSunucuIp()+"/saglikAPI/yorumlar.php";
        txtBaslik=findViewById(R.id.txtDetayBaslik);
        txtAciklama=findViewById(R.id.txtDetayAciklama);
        imgDetay=findViewById(R.id.imgDetay);
        edtYorum=findViewById(R.id.edtDetayYorum);
        btnGonder=findViewById(R.id.btnDetay);
        rcycYorumlar=findViewById(R.id.rcycDetayYorumlar);

        Intent intent = getIntent();

        gonderiId=intent.getStringExtra("gId");
        txtBaslik.setText(intent.getStringExtra("gBaslik"));
        txtAciklama.setText(intent.getStringExtra("gAciklama"));

        Picasso.get().load(sunucuAdres.getSunucuIp()+"/saglikAPI/"+intent.getStringExtra("gFoto")).resize(200,150).into(imgDetay);

        yorumlar();
        RecyclerView recyclerView=findViewById(R.id.rcycDetayYorumlar);
        recyclerView.setLayoutManager(new LinearLayoutManager(GdetayActivity.this));
        recyclerView.setHasFixedSize(true);
        yorumlarAdapter = new YorumlarAdapter(ayorumId,akullaniciId,agonderiId,akullaniciAd,ayorum,akullaniciFoto);
        recyclerView.setAdapter(yorumlarAdapter);

        btnGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yorumYap();
            }
        });
    }


    private void yorumYap()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(GdetayActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    String basarili = jsonObject.getJSONObject("Deger").getString("basarili");

                    if(basarili.equals("1"))
                    {
                        Toast.makeText(GdetayActivity.this, "Yorum gönderildi!", Toast.LENGTH_SHORT).show();
                        edtYorum.setText("");
                        ayorumId.clear();
                        akullaniciAd.clear();
                        agonderiId.clear();
                        ayorum.clear();
                        akullaniciFoto.clear();
                        agonderiId.clear();
                        yorumlar();

                    }
                    else
                        {
                            Toast.makeText(GdetayActivity.this, "Hata, tekrar deneyin!", Toast.LENGTH_SHORT).show();
                        }
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
                stringMap.put("islem","1");
                stringMap.put("tip",AnasayfaActivity.getGelenTip());
                stringMap.put("kullaniciAd",AnasayfaActivity.getGelenMail());
                stringMap.put("yorum",edtYorum.getText().toString());
                stringMap.put("gonderiId",gonderiId);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    YorumlarAdapter yorumlarAdapter;
    private ArrayList<String> ayorumId,akullaniciId,akullaniciAd,ayorum,akullaniciFoto,agonderiId;
    private void yorumlar()
    {

        RequestQueue requestQueue= Volley.newRequestQueue(GdetayActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for(int i =0 ; i < jsonArray.length();i++)
                    {
                        JSONObject jsonObject= jsonArray.getJSONObject(i);
                        ayorumId.add(jsonObject.getString("yorumId"));
                        akullaniciId.add(jsonObject.getString("kullaniciId"));
                        akullaniciAd.add(jsonObject.getString("kullaniciAd"));
                        ayorum.add(jsonObject.getString("yorum"));
                        akullaniciFoto.add(jsonObject.getString("kullaniciFoto"));
                        agonderiId.add(jsonObject.getString("gonderiId"));

                        yorumlarAdapter.notifyDataSetChanged();
                    }
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
                stringMap.put("islem","2");
                stringMap.put("gonderiId",gonderiId);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}