package com.berkhanakdag.saglicaklauyg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfilDuzenleActivity extends AppCompatActivity {

    Bitmap secilenBitMap;
    String uri;
    SunucuAdres sunucuAdres;
    private ImageView pDuzenleImg;
    private Spinner spnrCinsiyet;
    private Button btnResimSec,btnKaydet;
    private EditText edtAd,edtSoyad,edtMail,edtSifre,edtSifreTekrar,edtTelefon,edtDogumYil;
    String gelenFoto;

    private ArrayList<String> cinsiyet;
    private ArrayAdapter<String> cinsiyetAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_duzenle);

        sunucuAdres=new SunucuAdres();
        uri=sunucuAdres.getSunucuIp()+"/saglikAPI/profil.php";

        cinsiyet = new ArrayList<>();
        cinsiyet.add("Kadın");
        cinsiyet.add("Erkek");
        cinsiyetAdapter = new ArrayAdapter<String>(ProfilDuzenleActivity.this, android.R.layout.simple_spinner_item,cinsiyet);
        cinsiyetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        pDuzenleImg = findViewById(R.id.imgPduzenle);
        spnrCinsiyet = findViewById(R.id.spnrPduzenleCinsiyet);
        btnResimSec = findViewById(R.id.btnPduzenleImg);
        btnKaydet = findViewById(R.id.btnPduzenleKaydet);
        edtAd = findViewById(R.id.edtPduzenleAd);
        edtSoyad = findViewById(R.id.edtPduzenleSoyad);
        edtMail = findViewById(R.id.edtPduzenleMail);
        edtSifre = findViewById(R.id.edtPduzenleSifre);
        edtSifreTekrar = findViewById(R.id.edtPduzenleSifreTekrar);
        edtTelefon = findViewById(R.id.edtPduzenleTelefon);
        edtDogumYil = findViewById(R.id.edtPduzenleDogumYil);

        spnrCinsiyet.setAdapter(cinsiyetAdapter);
        Intent intent = getIntent();

        Picasso.get().load(sunucuAdres.getSunucuIp()+intent.getStringExtra("gelenFoto")).resize(140,150).into(pDuzenleImg);
        edtAd.setText(intent.getStringExtra("gelenAd"));
        edtSoyad.setText(intent.getStringExtra("gelenSoyad"));
        edtMail.setText(AnasayfaActivity.getGelenMail());
        edtSifre.setText(intent.getStringExtra("gelenSifre"));
        edtSifreTekrar.setText(intent.getStringExtra("gelenSifre"));
        edtTelefon.setText(intent.getStringExtra("gelenTelefon"));
        gelenFoto = intent.getStringExtra("gelenFoto");
        
        int yil =2021 - Integer.valueOf(intent.getStringExtra("gelenYas"));
        edtDogumYil.setText(""+yil);

        btnResimSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimSecTikla();
            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSifre.getText().toString().equals(edtSifreTekrar.getText().toString()) && edtSifre.getText().length()>5)
                {
                    bilgileriGuncelle();
                }
                else
                    {
                        Toast.makeText(ProfilDuzenleActivity.this, "Şifreler uyuşmuyor veya çok kısa!"+edtSifre.getText().toString()+" : "+edtSifreTekrar.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

    private void bilgileriGuncelle()
    {
        RequestQueue requestQueue= Volley.newRequestQueue(ProfilDuzenleActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                    {
                        Toast.makeText(ProfilDuzenleActivity.this, "Bilgiler Güncellendi!", Toast.LENGTH_SHORT).show();
                        finish();
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
                stringMap.put("ad",edtAd.getText().toString());
                stringMap.put("soyad",edtSoyad.getText().toString());
                stringMap.put("sifre",edtSifre.getText().toString());
                stringMap.put("telefon",edtTelefon.getText().toString());
                stringMap.put("cinsiyet",spnrCinsiyet.getSelectedItem().toString());
                int y = 2021 - Integer.valueOf(edtDogumYil.getText().toString());
                stringMap.put("yas",String.valueOf(y));
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
                    pDuzenleImg.setImageBitmap(bitmap);
                    resmiKaydet();
                }
                else{
                    secilenBitMap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    pDuzenleImg.setImageBitmap(secilenBitMap);
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
        RequestQueue requestQueue= Volley.newRequestQueue(ProfilDuzenleActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getJSONObject("Deger").getString("basarili").equals("1"))
                    {
                        Toast.makeText(ProfilDuzenleActivity.this, "Fotoğraf Güncellendi!", Toast.LENGTH_SHORT).show();
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
                stringMap.put("tip",AnasayfaActivity.getGelenTip());
                stringMap.put("mail",AnasayfaActivity.getGelenMail());
                stringMap.put("foto",fotoStringCevir(secilenBitMap));
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}