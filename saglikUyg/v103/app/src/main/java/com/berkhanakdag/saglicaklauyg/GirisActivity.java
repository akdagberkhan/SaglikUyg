package com.berkhanakdag.saglicaklauyg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.berkhanakdag.saglicaklauyg.Adapters.GirisAdapter;
import com.berkhanakdag.saglicaklauyg.Fragments.Doktor.DoktorGirisFragment;
import com.berkhanakdag.saglicaklauyg.Fragments.Kullanici.KullaniciGirisFragment;
import com.google.android.material.tabs.TabLayout;

public class GirisActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTablayout;
    private GirisAdapter mAdapter;  // Adapter sınıfımız
    private TextView girisKayitOlBtn;

    private void init(){
        mViewPager = findViewById(R.id.girisViewPager);
        mTablayout = findViewById(R.id.girisTabLayout);

        //Sınıf oalrak aldığımız adapterün içine kullanacağamız olan fragmentleri ekliyoruz
        mAdapter = new GirisAdapter(getSupportFragmentManager());
        mAdapter.addFragment(new KullaniciGirisFragment(), "Kullanıcı Giriş");
        mAdapter.addFragment(new DoktorGirisFragment(), "Doktor Giriş");
        //SON

        mViewPager.setAdapter(mAdapter);    //Layoutumuz üzerindeki viewpagera adaptörümüsü atıyoruz
        mTablayout.setupWithViewPager(mViewPager);  //tablayout kullanarak başlık kısmını hallediyoruz
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        init(); // metodumuzu burada çağırdık

        girisKayitOlBtn = findViewById(R.id.girisTxtKayitOl);           //kayıt ol activitesine gitmek için kullanacağız
        girisKayitOlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GirisActivity.this,KayitOlActivity.class));
            }
        });
    }
}