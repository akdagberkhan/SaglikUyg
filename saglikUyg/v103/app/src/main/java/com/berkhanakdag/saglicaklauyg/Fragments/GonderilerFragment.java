package com.berkhanakdag.saglicaklauyg.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.GonderiAdapter;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.GonderiPaylasActivity;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GonderilerFragment extends Fragment {

    private Context mcontext;
    private ArrayList<String> gonderiId;
    private ArrayList<String> gonderiFoto;
    private ArrayList<String> gonderiBaslik;
    private ArrayList<String> gonderiAciklama;
    private ArrayList<String> gonderiKategoriAd;
    private ArrayList<String> gonderiKategoriId;
    private ArrayList<String> gonderiKadSoyad;
    private ArrayList<String> gonderiKid;
    private ArrayList<String> gonderiKcinsiyet;
    private ArrayList<String> gonderiKyas;
    private ArrayList<String> gonderiKmail;
    GonderiAdapter gonderiAdapter;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gonderiler, container, false);
        gonderiId = new ArrayList<String>();
        gonderiFoto = new ArrayList<String>();
        gonderiBaslik = new ArrayList<String>();
        gonderiAciklama = new ArrayList<String>();
        gonderiKategoriAd  = new ArrayList<String>();
        gonderiKategoriId  = new ArrayList<String>();
        gonderiKadSoyad = new ArrayList<String>();
        gonderiKid = new ArrayList<String>();
        gonderiKcinsiyet = new ArrayList<String>();
        gonderiKyas = new ArrayList<String>();
        gonderiKmail = new ArrayList<String>();

        gonderilerAl();

        RecyclerView recyclerView=view.findViewById(R.id.rViewGonderiler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gonderiAdapter=new GonderiAdapter(gonderiId,gonderiFoto,gonderiBaslik,gonderiAciklama,gonderiKategoriAd,
                gonderiKadSoyad,gonderiKid,gonderiKcinsiyet,gonderiKyas,gonderiKmail);
        recyclerView.setAdapter(gonderiAdapter);
        return view;
    }

    public void gonderilerAl()
    {
        SunucuAdres sunucuAdres=new SunucuAdres();
        String uri = sunucuAdres.getSunucuIp()+"/saglikAPI/GonderiPaylas.php";
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        gonderiId.add(jsonObject.getString("gonderiId"));
                        gonderiFoto.add(jsonObject.getString("gonderiFoto"));
                        gonderiBaslik.add(jsonObject.getString("gonderiBaslik"));
                        gonderiAciklama.add(jsonObject.getString("gonderiAciklama"));
                        gonderiKategoriAd.add("boş");
                        gonderiKategoriId.add(jsonObject.getString("gonderiKategoriId"));
                        gonderiKadSoyad.add(jsonObject.getString("kullaniciAdSoyad"));
                        gonderiKid.add(jsonObject.getString("kullaniciId"));
                        gonderiKcinsiyet.add(jsonObject.getString("kullaniciCinsiyet"));
                        gonderiKyas.add(jsonObject.getString("kullaniciYas"));
                        gonderiKmail.add(jsonObject.getString("kullaniciMail"));
                        gonderiAdapter.notifyDataSetChanged();

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
                Map<String,String>stringMap=new HashMap<>();

                stringMap.put("drMail",AnasayfaActivity.getGelenMail());
                stringMap.put("islem","2");
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}