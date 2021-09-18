package com.berkhanakdag.saglicaklauyg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.berkhanakdag.saglicaklauyg.Adapters.KayitOlViewAdapter;
import com.berkhanakdag.saglicaklauyg.Fragments.Doktor.DoktorKayitFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.Kullanici.KullaniciKayitFragment;
import com.google.android.material.tabs.TabLayout;

public class KayitOlActivity extends AppCompatActivity {

    private KayitOlViewAdapter kayitOlViewAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView kayitOlTxtGirisBtn;
    private void init()
    {
        viewPager = findViewById(R.id.kayitolViewPager);
        tabLayout=findViewById(R.id.kayitolTabLayout);

        kayitOlViewAdapter = new KayitOlViewAdapter(getSupportFragmentManager());
        kayitOlViewAdapter.addFragment(new KullaniciKayitFragment(),"Kullanıcı Kayıt");
        kayitOlViewAdapter.addFragment(new DoktorKayitFragment(),"Doktor Kayıt");

        viewPager.setAdapter(kayitOlViewAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        init();

        kayitOlTxtGirisBtn=findViewById(R.id.kayitolTxtGirisYap);

        kayitOlTxtGirisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(KayitOlActivity.this,GirisActivity.class));
            }
        });
    }
}