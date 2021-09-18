package com.berkhanakdag.saglicaklauyg.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.R;
import com.google.android.material.badge.BadgeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SporFragment extends Fragment {

    private EditText edtAdimSayisi,edtHaraketAdi,edtYakilanKalori;
    private TextView txtYakilan;
    private Button btnHesap,btnKaydet;
    SunucuAdres sunucuAdres;
    String uri;
    private Context mcontext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_spor, container, false);
        sunucuAdres = new SunucuAdres();
        uri= sunucuAdres.getSunucuIp()+"/saglikAPI/spor.php";
        edtAdimSayisi = view.findViewById(R.id.edtSporAdimS);
        edtHaraketAdi = view.findViewById(R.id.edtSporHaraketAdi);
        edtYakilanKalori = view.findViewById(R.id.edtSporTahminiKalori);
        txtYakilan = view.findViewById(R.id.txtSporYakilanK);
        btnHesap = view.findViewById(R.id.btnSporHesapla);
        btnKaydet = view.findViewById(R.id.btnSporKaydet);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sporKayit();
            }
        });

        btnHesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double snc = Integer.valueOf(edtAdimSayisi.getText().toString())*0.05;

                txtYakilan.setTextColor(getResources().getColor(R.color.ozel6));
                txtYakilan.setText(String.valueOf(snc));
            }
        });

        return view;
    }

    private void sporKayit()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    if (jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                    {
                        Toast.makeText(mcontext, "Haraket kayıt edildi!", Toast.LENGTH_SHORT).show();
                        edtHaraketAdi.setText("");
                        edtYakilanKalori.setText("");
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
                stringMap.put("haraketAdi",edtHaraketAdi.getText().toString());
                stringMap.put("tahminiKalori",edtYakilanKalori.getText().toString());
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }
}