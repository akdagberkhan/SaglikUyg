package com.berkhanakdag.saglicaklauyg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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
import com.berkhanakdag.saglicaklauyg.Fragments.DoktorPanelFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.DoktorYorumlarFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.GonderilerFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.GonderilerimFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.IletisimFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.ProfilFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.SaglikFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.SporFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnasayfaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    View Baslikview;
    String sunucu,pFoto;
    public static String gelenMail,gelenTip;
    private static ArrayList kulArray =new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        Intent intent = getIntent();
        gelenMail = intent.getStringExtra("mail");
        gelenTip = intent.getStringExtra("girisTip");
        SunucuAdres sunucuAdres =new SunucuAdres();
        sunucu = sunucuAdres.getSunucuIp()+"/saglikAPI/KullaniciBilgi.php";
        kullaniciCek();



        Toolbar toolbar = findViewById(R.id.toolbarAna);// Toolbarı tanımladım ve Action bar oalrak ayarladım
        setSupportActionBar(toolbar);

        if(gelenTip.equals("1"))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new GonderilerimFragment()).commit();
            NavigationView navigationView=findViewById(R.id.nav_view_ana);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.yan_menu_kullanicilar);
            navigationView.setNavigationItemSelectedListener(this);
            Baslikview = navigationView.getHeaderView(0);// Menü alanımı tanımladım , Header ımı tanımladım
        }
        else if(gelenTip.equals("2"))
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new GonderilerFragment()).commit();// Başlangıçta gonderielr frame layoutuma fragment gonderileri commit ettim
            NavigationView navigationView=findViewById(R.id.nav_view_ana);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.yan_menu_doktorlar);
            navigationView.setNavigationItemSelectedListener(this);
            Baslikview = navigationView.getHeaderView(0);
        }
        else
        {
            finish();
        }

        TextView txtKuladBaslik = (TextView) Baslikview.findViewById(R.id.nav_baslik_text);
        txtKuladBaslik.setText(" "+ gelenMail);



        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();// Kayan menümü tanımladım ve toolbara bağladım
    }

    public static String getGelenMail() {
        return gelenMail;
    }

    public static String getGelenTip() {
        return gelenTip;
    }

    public static String getAnonimId() {
        return anonimId;
    }

    public static String getAnonimAd() {
        return anonimAd;
    }

    public static String getAnonimSoyad() {
        return anonimSoyad;
    }

    public static String getAnonimSifre() {
        return anonimSifre;
    }

    public static String getAnonimTelefon() {
        return anonimTelefon;
    }

    public static String getAnonimCinsiyet() {
        return anonimCinsiyet;
    }

    public static String getAnonimYas() {
        return anonimYas;
    }

    public static String getAnonimFoto() {
        return anonimFoto;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //Seçilen menü elemanı


        switch (item.getItemId()){

            case R.id.nav_gonderilerDoktor:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new GonderilerFragment()).commit();
                break;
            case R.id.nav_iletisimKullanici:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new IletisimFragment()).commit();
                break;
            case R.id.nav_iletisimDoktor:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new IletisimFragment()).commit();
                break;
            case R.id.nav_profilKullanici:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new ProfilFragment()).commit();
                break;
            case R.id.nav_profilDoktor:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new ProfilFragment()).commit();
                break;
            case R.id.nav_cikisKullanici:
                startActivity(new Intent(AnasayfaActivity.this,GirisActivity.class));
                finish();
                break;
            case R.id.nav_cikisDoktor:
                startActivity(new Intent(AnasayfaActivity.this,GirisActivity.class));
                finish();
                break;
            case R.id.nav_gonderilerimKullanici:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new GonderilerimFragment()).commit();
                break;
            case R.id.nav_doktorpanelDoktor:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new DoktorPanelFragment()).commit();
                break;
            case R.id.nav_yorumalrimDoktor:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new DoktorYorumlarFragment()).commit();
                break;
            case R.id.nav_saglikKullanici:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new SaglikFragment()).commit();
                break;
            case R.id.nav_sporKullanici:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutAna,new SporFragment()).commit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    // geri tuşuna basıldığında menü açıksa kapat
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }



    public static String anonimId,anonimAd,anonimSoyad,anonimSifre,anonimTelefon,anonimCinsiyet,anonimYas,anonimFoto;
    private void kullaniciCek()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(AnasayfaActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, sunucu, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(gelenTip.equals("1")) {
                        anonimId = jsonObject.getJSONObject("Deger").getString("kulId");
                        anonimAd = jsonObject.getJSONObject("Deger").getString("ad");
                        anonimSoyad = jsonObject.getJSONObject("Deger").getString("soyad");
                        anonimSifre = jsonObject.getJSONObject("Deger").getString("sifre");
                        anonimTelefon = jsonObject.getJSONObject("Deger").getString("telefon");
                        anonimCinsiyet = jsonObject.getJSONObject("Deger").getString("cinsiyet");
                        anonimYas = jsonObject.getJSONObject("Deger").getString("yas");
                        anonimFoto = jsonObject.getJSONObject("Deger").getString("kullaniciFoto");
                        pFoto = jsonObject.getJSONObject("Deger").getString("kullaniciFoto");
                        SunucuAdres sunucuAdres = new SunucuAdres();
                        Picasso.get().load(sunucuAdres.getSunucuIp()+pFoto).resize(80,80).into((ImageView)Baslikview.findViewById(R.id.nav_baslik_image));
                    }
                    else if(gelenTip.equals("2"))
                        {
                            anonimId = jsonObject.getJSONObject("Deger").getString("kulId");
                            anonimAd = jsonObject.getJSONObject("Deger").getString("ad");
                            anonimSoyad = jsonObject.getJSONObject("Deger").getString("soyad");
                            anonimSifre = jsonObject.getJSONObject("Deger").getString("sifre");
                            anonimTelefon = jsonObject.getJSONObject("Deger").getString("telefon");
                            anonimCinsiyet = jsonObject.getJSONObject("Deger").getString("cinsiyet");
                            anonimFoto = jsonObject.getJSONObject("Deger").getString("kullaniciFoto");
                            pFoto = jsonObject.getJSONObject("Deger").getString("kullaniciFoto");
                            SunucuAdres sunucuAdres = new SunucuAdres();
                            Picasso.get().load(sunucuAdres.getSunucuIp()+pFoto).resize(80,80).into((ImageView)Baslikview.findViewById(R.id.nav_baslik_image));
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
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("mail",gelenMail);
                stringMap.put("tip",gelenTip);
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}