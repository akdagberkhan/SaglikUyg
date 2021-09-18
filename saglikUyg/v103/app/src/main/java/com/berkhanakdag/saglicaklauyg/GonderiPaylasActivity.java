package com.berkhanakdag.saglicaklauyg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GonderiPaylasActivity extends AppCompatActivity {


    private String sunucu,mailg,tipg;
    Bitmap secilenBitMap;
    private ImageView secilenFotoGonderiPaylas;
    private Button resimSecGonderiPaylas,gonderiPaylas;
    private EditText gonderiBaslik,gonderiAciklama;
    private Spinner gonderiKategori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonderi_paylas);

        SunucuAdres sunucuAdres=new SunucuAdres();
        sunucu=sunucuAdres.getSunucuIp()+"/saglikAPI/GonderiPaylas.php";
        mailg=AnasayfaActivity.getGelenMail();
        tipg=AnasayfaActivity.getGelenTip();

        gonderiPaylas = findViewById(R.id.btnGonderiPaylas);
        gonderiBaslik = findViewById(R.id.edtGonderiPaylasBaslik);
        gonderiAciklama = findViewById(R.id.edtGonderiPaylasAciklama);
        gonderiKategori = findViewById(R.id.spnrGonderiPaylasKategoriler);
        resimSecGonderiPaylas = findViewById(R.id.btnGonderiPaylasImgSec);
        secilenFotoGonderiPaylas = findViewById(R.id.imgGonderiPaylas);

        kategoriler();
        resimSecGonderiPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimSecTikla();
            }
        });

        gonderiPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gonderiPaylas();
            }
        });
    }

    private void gonderiPaylas()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(GonderiPaylasActivity.this);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, sunucu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);

                    String basarili = jsonObject.getJSONObject("Deger").getString("basarili");

                    if(basarili.equals("1"))
                    {
                        Toast.makeText(GonderiPaylasActivity.this, "Paylaşım Başarılı!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GonderiPaylasActivity.this,AnasayfaActivity.class);
                        intent.putExtra("mail",AnasayfaActivity.getGelenMail());
                        intent.putExtra("girisTip",AnasayfaActivity.getGelenTip());
                        startActivity(intent);
                    }
                    else if(basarili.equals("01"))
                    {
                        Toast.makeText(GonderiPaylasActivity.this, "Hata, Tekrar deneyiniz!", Toast.LENGTH_SHORT).show();
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
                stringMap.put("baslik",gonderiBaslik.getText().toString());
                stringMap.put("aciklama",gonderiAciklama.getText().toString());
                stringMap.put("kategoriId",String.valueOf(gonderiKategori.getSelectedItemPosition()+1));
                stringMap.put("kullaniciId",AnasayfaActivity.getAnonimId());
                stringMap.put("kullaniciAdSoyad",AnasayfaActivity.getAnonimAd()+" "+AnasayfaActivity.getAnonimSoyad());
                stringMap.put("kullaniciMail",AnasayfaActivity.getGelenMail());
                stringMap.put("kullaniciYas",AnasayfaActivity.getAnonimYas());
                stringMap.put("kullaniciCinsiyet",AnasayfaActivity.getAnonimCinsiyet());
                stringMap.put("fotoyol",fotoStringCevir(secilenBitMap));
                return stringMap;
            }
        };

        requestQueue.add(stringRequest);
    }
    private ArrayList<String> kategori;
    private ArrayList<String> kategori_id;
    private ArrayAdapter<String> dataAdapterKategori;
    private void kategoriler()
    {
        kategori = new ArrayList<>();
        kategori_id=new ArrayList<>();
        SunucuAdres sunucuAdres=new SunucuAdres();
        String uri =sunucuAdres.getSunucuIp()+"/saglikAPI/kategoriler.php";

        RequestQueue requestQueue=Volley.newRequestQueue(GonderiPaylasActivity.this);

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
                    dataAdapterKategori =  new ArrayAdapter<>(GonderiPaylasActivity.this, android.R.layout.simple_spinner_item,kategori);
                    dataAdapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gonderiKategori.setAdapter(dataAdapterKategori);
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
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==2 && resultCode==RESULT_OK&&data!=null){

            Uri uri =data.getData();
            try {
                if(Build.VERSION.SDK_INT>=28){

                    ImageDecoder.Source source=ImageDecoder.createSource(this.getContentResolver(),uri);
                    Bitmap bitmap=ImageDecoder.decodeBitmap(source);
                    secilenBitMap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    secilenFotoGonderiPaylas.setImageBitmap(bitmap);
                }
                else{
                    secilenBitMap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    secilenFotoGonderiPaylas.setImageBitmap(secilenBitMap);
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
}