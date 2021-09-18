package com.berkhanakdag.saglicaklauyg.Fragments.Kullanici;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.GirisActivity;
import com.berkhanakdag.saglicaklauyg.KayitOlActivity;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class KullaniciKayitFragment extends Fragment {

    private String sunucu;
    private ConstraintLayout kulKayitCons;
    private EditText kulKayitAd,kulKayitSoyad,kulKayitMail,kulKayitSifre,kulKayitSifreTekrar,kulKayitTelefon;
    private Button kulKayitGonder;
    SunucuAdres sunucuAdres=new SunucuAdres();

    private Context mContext;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kullanici_kayit, container, false);

        sunucu = sunucuAdres.getSunucuIp()+"/saglikAPI/KayitGiris.php";
        kulKayitCons = view.findViewById(R.id.kKayitCons);
        kulKayitGonder = view.findViewById(R.id.kKayitBtnGonder);
        kulKayitAd = view.findViewById(R.id.kKayitTxtAd);
        kulKayitSoyad = view.findViewById(R.id.kKayitTxtSoyad);
        kulKayitMail = view.findViewById(R.id.kKayitTxtMail);
        kulKayitSifre = view.findViewById(R.id.kKayitTxtSifre);
        kulKayitSifreTekrar = view.findViewById(R.id.kKayitTxtSifreTekrar);
        kulKayitTelefon = view.findViewById(R.id.kKayitTxtTelefon);


        kulKayitGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullKayit();
            }
        });


        return view;
    }


    private void kullKayit()
    {

        if(kulKayitAd.getText().toString().equals("") || kulKayitSoyad.getText().toString().equals("") || kulKayitMail.getText().toString().equals("") || kulKayitSifre.getText().toString().equals("") || kulKayitSifreTekrar.getText().toString().equals("") || kulKayitTelefon.getText().toString().equals(""))
        {
            Toast.makeText(mContext, "Alanlar boş bırakılamaz!", Toast.LENGTH_SHORT).show();
        }
        else
            {
                if(kulKayitSifre.getText().toString().equals(kulKayitSifreTekrar.getText().toString()))
                {
                    if (kulKayitSifre.getText().length() > 5)
                    {
                        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, sunucu, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject =new JSONObject(response);
                                    String basarili = jsonObject.getJSONObject("Deger").getString("basarili");
                                    if(basarili.equals("1"))
                                    {
                                        Toast.makeText(mContext, "Başarılı, Giriş yapabilirsiniz!", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(mContext,GirisActivity.class));
                                    }
                                    else
                                        {
                                            Toast.makeText(mContext, "Hata, Bilgilerinizi kontrol edip tekrar kayıt olunuz!", Toast.LENGTH_SHORT).show();
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
                                stringMap.put("ad",kulKayitAd.getText().toString());
                                stringMap.put("soyad",kulKayitSoyad.getText().toString());
                                stringMap.put("mail",kulKayitMail.getText().toString());
                                stringMap.put("sifre",kulKayitSifre.getText().toString());
                                stringMap.put("telefon",kulKayitTelefon.getText().toString());
                                return stringMap;
                            }

                        };
                        requestQueue.add(stringRequest);
                    }
                    else
                        {
                            Toast.makeText(mContext, "Şifre 5 karakterden uzun olamalı!", Toast.LENGTH_SHORT).show();
                        }
                }
                else
                    {
                        Toast.makeText(mContext, "Şifreler eşleşmiyor!", Toast.LENGTH_SHORT).show();
                    }
            }

    }
}