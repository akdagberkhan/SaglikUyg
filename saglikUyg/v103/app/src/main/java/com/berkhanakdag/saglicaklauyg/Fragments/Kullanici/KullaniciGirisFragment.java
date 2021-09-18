package com.berkhanakdag.saglicaklauyg.Fragments.Kullanici;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.berkhanakdag.saglicaklauyg.Adapters.GirisAdapter;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class KullaniciGirisFragment extends Fragment {

    private Context mContext;
    private EditText kulGirisMail,kulGirisSifre;
    private Button kulGirisBtn;
    private String sunucu;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kullanici_giris, container, false);

        SunucuAdres sunucuAdres=new SunucuAdres();
        sunucu = sunucuAdres.getSunucuIp()+"/saglikAPI/KayitGiris.php";

        kulGirisMail = view.findViewById(R.id.girisKullaniciEdtMail);
        kulGirisSifre = view.findViewById(R.id.girisKullaniciEdtSifre);
        kulGirisBtn = view.findViewById(R.id.girisKullaniciBtn);

        kulGirisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kullaniciGiris();
            }
        });
        return view;
    }



    private void kullaniciGiris()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, sunucu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String basari = jsonObject.getJSONObject("Deger").getString("basarili");
                    
                    if (basari.equals("3"))
                    {
                        Intent intent = new Intent(mContext,AnasayfaActivity.class);
                        intent.putExtra("mail",kulGirisMail.getText().toString());
                        intent.putExtra("girisTip","1");
                        startActivity(intent);
                    }
                    else if (basari.equals("03"))
                        {
                            Toast.makeText(mContext, "hata", Toast.LENGTH_SHORT).show();
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
                Map<String ,String> stringMap=new HashMap<>();
                stringMap.put("islem","3");
                stringMap.put("mail",kulGirisMail.getText().toString());
                stringMap.put("sifre",kulGirisSifre.getText().toString());
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}