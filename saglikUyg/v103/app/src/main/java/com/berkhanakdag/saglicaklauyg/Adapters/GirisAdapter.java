package com.berkhanakdag.saglicaklauyg.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class GirisAdapter extends FragmentPagerAdapter {    //Fragmentleri pager olarak kullanmak için extandsımızı kulandık

    private ArrayList<Fragment> mFragmentList;  //giriş aktivitesinden gelecek olan fragmentlar
    private ArrayList<String> mNameList;        //giriş aktivitesinden gelecek olan başlıklar

    public GirisAdapter(FragmentManager fragmentManager)    //boş bir constructor oluşturduk
    {
        super(fragmentManager);
        mFragmentList = new ArrayList<>();      //
        mNameList = new ArrayList<>();          //tanımalma
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        return mFragmentList.get(position); //gelen elemanımızın pozisyonuna göre ayarlama yaptık
    }

    @Override
    public int getCount()
    {
        return mFragmentList.size();    //gelen elemanımızın sayısana göre genişliği belirledik
    }

    public void addFragment(Fragment fragment, String isim){    //Listelerimize gelen fragment ve isimleri ekledik
        mFragmentList.add(fragment);
        mNameList.add(isim);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        return mNameList.get(position); // başlığın sırasına göre ayarlama yaptık
    }

}
