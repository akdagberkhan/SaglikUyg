package com.berkhanakdag.saglicaklauyg.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.berkhanakdag.saglicaklauyg.Adapters.SunucuAdres;
import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.IlaclarActivity;
import com.berkhanakdag.saglicaklauyg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SaglikFragment extends Fragment {

    private SunucuAdres sunucuAdres;
    private String uri;
    private Context mcontext;
    private Spinner spnrKullanim,spnrKullanimAralik;
    private Button btnKaydet,btnIlacKaydet,btnIlacGoster;
    private EditText edtBtansiyon,edtKtansiyon,edtKalpAtis,edtIlacAdi,edtIlacBaslangicTarihi;

    private ArrayList<String> kullanim;
    private ArrayList<String> kullanimA;

    private ArrayAdapter<String> kullanimAdapter;
    private ArrayAdapter<String> kullanimAralikAdapter;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saglik, container, false);

        sunucuAdres = new SunucuAdres();
        uri = sunucuAdres.getSunucuIp()+"/saglikAPI/saglik.php";
        spnrKullanim = view.findViewById(R.id.spnrSaglikKullanim);
        spnrKullanimAralik =view.findViewById(R.id.spnrSaglikKullanimAralik);
        btnKaydet = view.findViewById(R.id.btnSaglikKaydet);
        btnIlacGoster = view.findViewById(R.id.btnSaglikIlacG);
        btnIlacKaydet = view.findViewById(R.id.btnSaglikIlacK);
        edtBtansiyon = view.findViewById(R.id.edtSaglikBuyukT);
        edtKtansiyon = view.findViewById(R.id.edtSaglikKucukT);
        edtKalpAtis = view.findViewById(R.id.edtSaglikKalpA);
        edtIlacAdi = view.findViewById(R.id.edtSaglikIlacAdi);
        edtIlacBaslangicTarihi =view.findViewById(R.id.edtSaglikBaslangicT);

        kullanim = new ArrayList<>();
        kullanim.add("Aç");
        kullanim.add("Tok");
        kullanimA = new ArrayList<>();
        kullanimA.add("Sabah");
        kullanimA.add("Öğle");
        kullanimA.add("Akşam");
        kullanimA.add("Sabah-Akşam");
        kullanimA.add("Sabah-Öğle-Akşam");
        kullanimAdapter = new ArrayAdapter<String>(mcontext, android.R.layout.simple_spinner_item,kullanim);
        kullanimAralikAdapter = new ArrayAdapter<String>(mcontext, android.R.layout.simple_spinner_item,kullanimA);
        kullanimAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kullanimAralikAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrKullanim.setAdapter(kullanimAdapter);
        spnrKullanimAralik.setAdapter(kullanimAralikAdapter);
        bilgiCek();

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bTansiyon,kTansiyon,kAtis;
                if(!edtBtansiyon.equals("") && !edtKtansiyon.equals("") && !edtKalpAtis.equals("") )
                {
                    bTansiyon = Integer.valueOf(edtBtansiyon.getText().toString());
                    kTansiyon = Integer.valueOf(edtKtansiyon.getText().toString());
                    kAtis = Integer.valueOf(edtKalpAtis.getText().toString());
                    bilgiKaydet();
                    if(bTansiyon>=80 && bTansiyon<=120)
                    {
                        if(kTansiyon>=60 && kTansiyon<=80)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                            builder.setTitle("BILGI");
                            builder.setMessage("Tansiyon değerleriniz normaldir.");
                            builder.setNeutralButton("Tamam",null);
                            builder.show();
                        }
                        else if(kTansiyon < 60)
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                            builder.setTitle("BILGI");
                            builder.setMessage("Küçük tansiyonunuz düşük, Doktorunuza başvurun.");
                            builder.setNeutralButton("Tamam",null);
                            builder.show();
                        }
                        else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                                builder.setTitle("BILGI");
                                builder.setMessage("Küçük tansiyonunuz yüksek, Doktorunuza başvurun.");
                                builder.setNeutralButton("Tamam",null);
                                builder.show();
                            }
                    }
                    else if(bTansiyon < 80)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setTitle("BILGI");
                        builder.setMessage("Büyük tansiyonunuz düşük, Doktorunuza başvurun.");
                        builder.setNeutralButton("Tamam",null);
                        builder.show();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                        builder.setTitle("BILGI");
                        builder.setMessage("Büyük tansiyonunuz yüksek, Doktorunuza başvurun.");
                        builder.setNeutralButton("Tamam",null);
                        builder.show();
                    }
                }

            }
        });

        btnIlacKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilacKaydet();
            }
        });

        btnIlacGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mcontext, IlaclarActivity.class);
                intent.putExtra("kullanici", AnasayfaActivity.getGelenMail());
                startActivity(intent);
            }
        });
        return view;
    }

    private void bilgiKaydet()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                stringMap.put("kMail",AnasayfaActivity.getGelenMail());
                stringMap.put("bTansiyon",edtBtansiyon.getText().toString());
                stringMap.put("kTansiyon",edtKtansiyon.getText().toString());
                stringMap.put("kAtis",edtKalpAtis.getText().toString());
                return stringMap;
            }
        };
    }
    private void bilgiCek()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(mcontext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i = 0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        edtBtansiyon.setText(jsonObject.getString("buyukT"));
                        edtKtansiyon.setText(jsonObject.getString("kucukT"));
                        edtKalpAtis.setText(jsonObject.getString("kalpA"));
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
                stringMap.put("kMail",AnasayfaActivity.getGelenMail());
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ilacKaydet()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(mcontext);
        StringRequest stringRequest =new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
                stringMap.put("ilacAd",edtIlacAdi.getText().toString());
                stringMap.put("ilacKullanim",spnrKullanim.getSelectedItem().toString());
                stringMap.put("ilacKullanimA",spnrKullanimAralik.getSelectedItem().toString());
                stringMap.put("baslangicTarih",edtIlacBaslangicTarihi.getText().toString());
                stringMap.put("kMail",AnasayfaActivity.getGelenMail());
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}