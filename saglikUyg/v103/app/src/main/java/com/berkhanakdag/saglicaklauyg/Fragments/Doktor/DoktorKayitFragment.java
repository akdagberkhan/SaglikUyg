package com.berkhanakdag.saglicaklauyg.Fragments.Doktor;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DoktorKayitFragment extends Fragment {


    private Context mContext;
    private EditText drKayitAd, drKayitSoyad, drKayitMail, drKayitSifre, drKayitSifreTekrar;
    private ImageView drKayitBilgi;
    private Button drKayitGonder;
    private String sunucu;
    private ConstraintLayout drKayitCons;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doktor_kayit, container, false);

        drKayitAd = view.findViewById(R.id.dKayitTxtAd);
        drKayitSoyad = view.findViewById(R.id.dKayitTxtSoyad);
        drKayitMail = view.findViewById(R.id.dKayitTxtMail);
        drKayitSifre = view.findViewById(R.id.dKayitTxtSifre);
        drKayitSifreTekrar = view.findViewById(R.id.dKayitTxtSifreTekrar);
        drKayitBilgi = view.findViewById(R.id.dKayitImgBilgi);
        drKayitGonder = view.findViewById(R.id.dKayitBtnGonder);
        drKayitCons =view.findViewById(R.id.dKayitConsLayout);
        
        SunucuAdres sunucuAdres=new SunucuAdres();
        sunucu=sunucuAdres.getSunucuIp()+"/saglikAPI/KayitGiris.php";

        drKayitGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drKayitSifre.getText().toString().equals(drKayitSifreTekrar.getText().toString()))
                {
                    if(drKayitSifre.getText().length() > 5) {
                        if (drKayitAd.getText().toString().equals("") || drKayitSoyad.getText().toString().equals("") || drKayitMail.getText().toString().equals("") || drKayitSifre.getText().toString().equals("") || drKayitSifreTekrar.getText().toString().equals("")) {
                            Toast.makeText(mContext, "Bilgiler boş bırakılamaz!", Toast.LENGTH_SHORT).show();
                        } else {

                            RequestQueue requestQueue = Volley.newRequestQueue(mContext);

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, sunucu, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject=new JSONObject(response);

                                        String basarili= jsonObject.getJSONObject("Deger").getString("basarili");
                                        if (basarili.equals("2"))
                                        {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                            builder.setTitle("BİLGİ");
                                            builder.setMessage("Hesabınız kısa bir süre içinde onaylanıp, sizinle mail yolu ile iletişime geçilecektir.");
                                            builder.setNeutralButton("Tamam",null);
                                            builder.show();

                                            for(int i=0;i<=drKayitCons.getChildCount();i++)
                                            {
                                                View v = drKayitCons.getChildAt(i);
                                                if (v instanceof EditText)
                                                {
                                                    ((EditText) v).setText("");
                                                }
                                            }
                                            
                                        }
                                        else if(basarili.equals("02"))
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
                                    Toast.makeText(mContext, "" + error.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> stringMap = new HashMap<>();

                                    stringMap.put("ad", drKayitAd.getText().toString());
                                    stringMap.put("soyad", drKayitSoyad.getText().toString());
                                    stringMap.put("mail", drKayitMail.getText().toString());
                                    stringMap.put("sifre", drKayitSifre.getText().toString());
                                    stringMap.put("islem", "2");

                                    return stringMap;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                    }
                    else
                        {
                            Toast.makeText(mContext, "Şifreniz 5 karakterden uzun olmalı!", Toast.LENGTH_SHORT).show();
                        }
                }
                else
                    {
                        Toast.makeText(mContext, "Şifreler eşleşmiyor!", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        drKayitBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "İletişim için zorunlu!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}