package com.berkhanakdag.saglicaklauyg.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.GonderilerimAdapter;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DoktorYorumlarFragment extends Fragment {

    private Context mContext;
    private ArrayList<String> gonderilerimId;
    private ArrayList<String> gonderilerimFoto;
    private ArrayList<String> gonderilerimBaslik;
    private ArrayList<String> gonderilerimAciklama;
    private ArrayList<String> gonderilerimKategoriAd;
    private ArrayList<String> gonderilerimKategoriId;
    private ArrayList<String> gonderilerimKadSoyad;
    private ArrayList<String> gonderilerimKid;
    private ArrayList<String> gonderilerimKcinsiyet;
    private ArrayList<String> gonderilerimKyas;
    private ArrayList<String> gonderilerimKmail;
    GonderilerimAdapter gonderilerimAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doktor_yorumlar, container, false);
        gonderilerimId = new ArrayList<String>();
        gonderilerimFoto = new ArrayList<String>();
        gonderilerimBaslik = new ArrayList<String>();
        gonderilerimAciklama = new ArrayList<String>();
        gonderilerimKategoriAd  = new ArrayList<String>();
        gonderilerimKategoriId  = new ArrayList<String>();
        gonderilerimKadSoyad = new ArrayList<String>();
        gonderilerimKid = new ArrayList<String>();
        gonderilerimKcinsiyet = new ArrayList<String>();
        gonderilerimKyas = new ArrayList<String>();
        gonderilerimKmail = new ArrayList<String>();

        gonderilerAl();

        RecyclerView recyclerView=view.findViewById(R.id.rcycYorumGonderi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        gonderilerimAdapter= new GonderilerimAdapter(gonderilerimId,gonderilerimFoto,gonderilerimBaslik,gonderilerimAciklama,
                gonderilerimKategoriAd,gonderilerimKadSoyad,gonderilerimKid,gonderilerimKcinsiyet,gonderilerimKyas,gonderilerimKmail);
        recyclerView.setAdapter(gonderilerimAdapter);
        return view;
    }

    public void gonderilerAl()
    {
        SunucuAdres sunucuAdres=new SunucuAdres();
        String uri = sunucuAdres.getSunucuIp()+"/saglikAPI/GonderiPaylas.php";
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        gonderilerimId.add(jsonObject.getString("gonderiId"));
                        gonderilerimFoto.add(jsonObject.getString("gonderiFoto"));
                        gonderilerimBaslik.add(jsonObject.getString("gonderiBaslik"));
                        gonderilerimAciklama.add(jsonObject.getString("gonderiAciklama"));
                        gonderilerimKategoriAd.add("bo??");
                        gonderilerimKategoriId.add(jsonObject.getString("gonderiKategoriId"));
                        gonderilerimKadSoyad.add(jsonObject.getString("kullaniciAdSoyad"));
                        gonderilerimKid.add(jsonObject.getString("kullaniciId"));
                        gonderilerimKcinsiyet.add(jsonObject.getString("kullaniciCinsiyet"));
                        gonderilerimKyas.add(jsonObject.getString("kullaniciYas"));
                        gonderilerimKmail.add(jsonObject.getString("kullaniciMail"));
                        gonderilerimAdapter.notifyDataSetChanged();

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

                stringMap.put("drMail", AnasayfaActivity.getGelenMail());
                stringMap.put("islem","4");
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}