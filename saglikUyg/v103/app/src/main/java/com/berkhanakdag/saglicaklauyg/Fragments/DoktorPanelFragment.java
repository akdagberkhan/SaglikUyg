package com.berkhanakdag.saglicaklauyg.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.berkhanakdag.saglicaklauyg.GonderiPaylasActivity;
import com.berkhanakdag.saglicaklauyg.ProfilDuzenleActivity;
import com.berkhanakdag.saglicaklauyg.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class DoktorPanelFragment extends Fragment {

    Bitmap secilenBitMap;
    private ArrayList<String> cinsiyet;
    private ArrayAdapter<String> cinsiyetAdapter;
    private ArrayList<String> kategori;
    private ArrayList<String> kategori_id;
    private ArrayAdapter<String> kategoriAdapter;
    private Context mContext;
    private Spinner drPanelCinsiyet,drPanelKategori;
    private ImageView drPanelImg;
    private Button drPanelBtnFoto,drPanelBtnKaydet;
    private EditText drPanelTelefon,drPanelSifre,drPanelSifreTekrar;
    SunucuAdres sunucuAdres;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_doktor_panel, container, false);
        sunucuAdres=new SunucuAdres();
        kategori = new ArrayList<>();
        kategori_id = new ArrayList<>();
        cinsiyet = new ArrayList<>();

        drPanelCinsiyet = view.findViewById(R.id.spnrDrPanelCinsiyet);
        drPanelKategori=view.findViewById(R.id.spnrDrPanelKategori);
        drPanelImg=view.findViewById(R.id.drPanelImg);
        drPanelBtnFoto=view.findViewById(R.id.btnDrPanelFoto);
        drPanelTelefon=view.findViewById(R.id.edtDrPanelTelefon);
        drPanelSifre=view.findViewById(R.id.edtDrPanelSifre);
        drPanelSifreTekrar=view.findViewById(R.id.edtDrPanelSifreTekrar);
        drPanelBtnKaydet = view.findViewById(R.id.btnDrPanelKaydet);
        cinsiyet.add("Kadın");
        cinsiyet.add("Erkek");
        cinsiyetAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,cinsiyet);
        cinsiyetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        drPanelCinsiyet.setAdapter(cinsiyetAdapter);
        kategoriler();
        KullaniciBilgi();
        drPanelBtnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimSecTikla();
            }
        });

        drPanelBtnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drPanelSifre.getText().toString().equals(drPanelSifreTekrar.getText().toString()) && drPanelSifre.getText().length()>5)
                {
                    bilgileriGuncelle();
                }
                else
                {
                    Toast.makeText(mContext, "Şifreler uyuşmuyor veya çok kısa!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
    private void kategoriler()
    {
        String uri =sunucuAdres.getSunucuIp()+"/saglikAPI/kategoriler.php";

        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        kategori.add(jsonObject.getString("kategoriAd"));
                        kategori_id.add(jsonObject.getString("kategoriId"));
                    }
                    kategoriAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,kategori);
                    kategoriAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    drPanelKategori.setAdapter(kategoriAdapter);
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
                stringMap.put("islem","10");
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    //FOTOĞRAF SEÇME VE İZİN -------------------------------------------------------------------------------------------------------------------------------------
    public void resimSecTikla(){
        if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
        else{
            Intent intentGaleri=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentGaleri,2);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intentGaleri=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGaleri,2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==2 && resultCode==RESULT_OK&&data!=null){

            Uri uri =data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28){

                    ImageDecoder.Source source=ImageDecoder.createSource( mContext.getContentResolver(),uri);
                    Bitmap bitmap=ImageDecoder.decodeBitmap(source);
                    secilenBitMap=MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
                    drPanelImg.setImageBitmap(bitmap);
                    resmiKaydet();
                }
                else{
                    secilenBitMap=MediaStore.Images.Media.getBitmap(mContext.getContentResolver(),uri);
                    drPanelImg.setImageBitmap(secilenBitMap);
                    resmiKaydet();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String fotoStringCevir(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] fotoByte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(fotoByte,Base64.DEFAULT);
    }

    private void resmiKaydet()
    {
        String uri=sunucuAdres.getSunucuIp()+"/saglikAPI/profil.php";
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Toast.makeText(mContext, ""+response, Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                    {
                        Toast.makeText(mContext, "Fotoğraf Güncellendi!", Toast.LENGTH_SHORT).show();
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
                stringMap.put("islem","3");
                stringMap.put("tip", AnasayfaActivity.getGelenTip());
                stringMap.put("mail",AnasayfaActivity.getGelenMail());
                stringMap.put("foto",fotoStringCevir(secilenBitMap));
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    String gelenKullFoto,gelenKullId,gelenKullSifre;
    private void KullaniciBilgi()
    {
        sunucuAdres = new SunucuAdres();
        String uri = sunucuAdres.getSunucuIp()+"/saglikAPI/profil.php";

        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                        if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                        {

                            drPanelTelefon.setText(jsonObject.getJSONObject("Deger").getString("telefon"));
                            drPanelSifre.setText(jsonObject.getJSONObject("Deger").getString("sifre"));
                            drPanelSifreTekrar.setText(jsonObject.getJSONObject("Deger").getString("sifre"));

                            gelenKullFoto = jsonObject.getJSONObject("Deger").getString("foto");
                            gelenKullId = jsonObject.getJSONObject("Deger").getString("kId");
                            gelenKullSifre = jsonObject.getJSONObject("Deger").getString("sifre");

                            Picasso.get().load(sunucuAdres.getSunucuIp()+gelenKullFoto).resize(130,120).into(drPanelImg);
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

    private void bilgileriGuncelle()
    {
        sunucuAdres = new SunucuAdres();
        String uri = sunucuAdres.getSunucuIp()+"/saglikAPI/profil.php";
        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                    {
                        Toast.makeText(mContext, "Bilgiler Güncellendi!", Toast.LENGTH_SHORT).show();
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
                stringMap.put("tip",AnasayfaActivity.getGelenTip());
                stringMap.put("mail",AnasayfaActivity.getGelenMail());
                stringMap.put("sifre",drPanelSifre.getText().toString());
                stringMap.put("kulId",gelenKullId.toString());
                stringMap.put("telefon",drPanelTelefon.getText().toString());
                stringMap.put("cinsiyet",drPanelCinsiyet.getSelectedItem().toString());
                stringMap.put("kategori",String.valueOf(drPanelKategori.getSelectedItemPosition()+1));
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}