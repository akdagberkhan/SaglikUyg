package com.berkhanakdag.saglicaklauyg.Fragments.Doktor;

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
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DoktorGirisFragment extends Fragment {

    private Context mContext;
    private String sunucu;
    private EditText drGirisMail,drGirisSifre;
    private Button drGirisBtn;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doktor_giris, container, false);

        drGirisBtn=view.findViewById(R.id.girisDoktorBtn);
        drGirisMail=view.findViewById(R.id.girisDoktorEdtMail);
        drGirisSifre=view.findViewById(R.id.girisDoktorEdtSifre);

        SunucuAdres sunucuAdres=new SunucuAdres();
        sunucu = sunucuAdres.getSunucuIp()+"/saglikAPI/KayitGiris.php";

        drGirisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doktorGiris();
            }
        });

        return view;
    }

    private void doktorGiris()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, sunucu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String basarili=jsonObject.getJSONObject("Deger").getString("basarili");

                    if (basarili.equals("4"))
                    {

                        Intent intent = new Intent(mContext, AnasayfaActivity.class);
                        intent.putExtra("mail",drGirisMail.getText().toString());
                        intent.putExtra("girisTip","2");
                        startActivity(intent);
                    }
                    else if(basarili.equals("044"))
                    {
                        Toast.makeText(mContext, "Hesap Onay Bekliyor!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        {
                            Toast.makeText(mContext, "Kullanıcı Bulunamadı!", Toast.LENGTH_SHORT).show();
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
                stringMap.put("islem","4");
                stringMap.put("mail",drGirisMail.getText().toString());
                stringMap.put("sifre",drGirisSifre.getText().toString());

                return  stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}