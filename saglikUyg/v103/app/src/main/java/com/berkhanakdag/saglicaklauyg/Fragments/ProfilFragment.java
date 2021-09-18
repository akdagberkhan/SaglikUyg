package com.berkhanakdag.saglicaklauyg.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.ProfilDuzenleActivity;
import com.berkhanakdag.saglicaklauyg.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfilFragment extends Fragment {

    private SunucuAdres sunucuAdres;
    private Context mContext;
    private String uri;
    private TextView baslikAdSoyad,etkilesimSayisi,paylasimSayisi,adSoyad,mailAdres,telefon,cinsiyet,yas,yasBaslik,paylasimBaslik,uzmanlik,uzmanlikBaslik;
    private Button profilDuzenle;
    private ImageView profilFoto,imgyenile;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        baslikAdSoyad = view.findViewById(R.id.txtProfilBaslikAdSoyad);
        etkilesimSayisi = view.findViewById(R.id.txtProfilEtkilesim);
        paylasimSayisi = view.findViewById(R.id.txtProfilPaylasimS);
        profilFoto = view.findViewById(R.id.imgProfilFoto);
        profilDuzenle = view.findViewById(R.id.btnProfilDuzenle);
        adSoyad = view.findViewById(R.id.txtProfilAdSoyad);
        mailAdres = view.findViewById(R.id.txtProfilMail);
        telefon = view.findViewById(R.id.txtProfilTelefon);
        cinsiyet = view.findViewById(R.id.txtProfilCinsiyet);
        yas = view.findViewById(R.id.txtProfilYas);
        yasBaslik = view.findViewById(R.id.txtYasBaslik);
        paylasimBaslik = view.findViewById(R.id.txtPaylasimBaslik);
        uzmanlik = view.findViewById(R.id.txtProfilUzmanlik);
        uzmanlikBaslik = view.findViewById(R.id.txtUzmanlikBaslik);
        imgyenile = view.findViewById(R.id.yenileimg);

        if(AnasayfaActivity.getGelenTip().equals("2"))
        {
            yas.setVisibility(View.GONE);
            yasBaslik.setVisibility(View.GONE);
            paylasimBaslik.setVisibility(View.GONE);
            paylasimSayisi.setVisibility(View.GONE);
            profilDuzenle.setVisibility(View.GONE);
            uzmanlikBaslik.setVisibility(View.VISIBLE);
            uzmanlik.setVisibility(View.VISIBLE);
        }
        else
            {
                uzmanlikBaslik.setVisibility(View.GONE);
                uzmanlik.setVisibility(View.GONE);
                yas.setVisibility(View.VISIBLE);
                yasBaslik.setVisibility(View.VISIBLE);
                paylasimBaslik.setVisibility(View.VISIBLE);
                paylasimSayisi.setVisibility(View.VISIBLE);
            }
        KullaniciBilgi();

        imgyenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KullaniciBilgi();
            }
        });
        profilDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfilDuzenleActivity.class);
                intent.putExtra("gelenFoto",gelenKullFoto);
                intent.putExtra("gelenId",gelenKullId);
                intent.putExtra("gelenSifre",gelenKullSifre);
                intent.putExtra("gelenAd",gelenKullAd);
                intent.putExtra("gelenSoyad",gelenKullSoyad);
                intent.putExtra("gelenTelefon",gelenKullTelefon);
                intent.putExtra("gelenCinsiyet",gelenKullCinsiyet);
                intent.putExtra("gelenYas",gelenKullYas);
                startActivity(intent);

            }
        });
        return view;
    }

    String gelenKullFoto,gelenKullId,gelenKullSifre,gelenKullAd,gelenKullSoyad,gelenKullTelefon,gelenKullCinsiyet,gelenKullYas;
    private void KullaniciBilgi()
    {
        sunucuAdres = new SunucuAdres();
        uri = sunucuAdres.getSunucuIp()+"/saglikAPI/profil.php";

        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if(AnasayfaActivity.getGelenTip().equals("1"))
                    {
                        if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                        {

                            baslikAdSoyad.setText(jsonObject.getJSONObject("Deger").getString("ad")+" "+jsonObject.getJSONObject("Deger").getString("soyad"));
                            adSoyad.setText(":"+jsonObject.getJSONObject("Deger").getString("ad")+" "+jsonObject.getJSONObject("Deger").getString("soyad"));
                            telefon.setText(":"+jsonObject.getJSONObject("Deger").getString("telefon"));
                            cinsiyet.setText(":"+jsonObject.getJSONObject("Deger").getString("cinsiyet"));
                            yas.setText(":"+jsonObject.getJSONObject("Deger").getString("yas"));
                            etkilesimSayisi.setText(":"+jsonObject.getJSONObject("Deger").getString("etkilesim"));
                            paylasimSayisi.setText(":"+jsonObject.getJSONObject("Deger").getString("paylasimS"));
                            mailAdres.setText(":"+AnasayfaActivity.getGelenMail());

                            gelenKullFoto = jsonObject.getJSONObject("Deger").getString("foto");
                            gelenKullId = jsonObject.getJSONObject("Deger").getString("kId");
                            gelenKullSifre = jsonObject.getJSONObject("Deger").getString("sifre");
                            gelenKullAd = jsonObject.getJSONObject("Deger").getString("ad");
                            gelenKullSoyad = jsonObject.getJSONObject("Deger").getString("soyad");
                            gelenKullTelefon = jsonObject.getJSONObject("Deger").getString("telefon");
                            gelenKullCinsiyet = jsonObject.getJSONObject("Deger").getString("cinsiyet");
                            gelenKullYas = jsonObject.getJSONObject("Deger").getString("yas");

                            Picasso.get().load(sunucuAdres.getSunucuIp()+gelenKullFoto).resize(130,120).into(profilFoto);
                        }
                    }
                    else if(AnasayfaActivity.getGelenTip().equals("2"))
                    {
                        if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                        {
                            adSoyad.setText(":"+jsonObject.getJSONObject("Deger").getString("ad")+" "+jsonObject.getJSONObject("Deger").getString("soyad"));
                            telefon.setText(":"+jsonObject.getJSONObject("Deger").getString("telefon"));
                            cinsiyet.setText(":"+jsonObject.getJSONObject("Deger").getString("cinsiyet"));
                            etkilesimSayisi.setText(":"+jsonObject.getJSONObject("Deger").getString("etkilesim"));
                            uzmanlik.setText(":"+jsonObject.getJSONObject("Deger").getString("uzmanlik"));

                            gelenKullFoto = jsonObject.getJSONObject("Deger").getString("foto");
                            gelenKullId = jsonObject.getJSONObject("Deger").getString("kId");
                            gelenKullSifre = jsonObject.getJSONObject("Deger").getString("sifre");

                            Picasso.get().load(sunucuAdres.getSunucuIp()+gelenKullFoto).resize(130,120).into(profilFoto);
                        }
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
                stringMap.put("mail", AnasayfaActivity.getGelenMail());
                stringMap.put("tip",AnasayfaActivity.getGelenTip());
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}