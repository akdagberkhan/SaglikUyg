package com.berkhanakdag.saglicaklauyg.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class KayitOlViewAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentList;
    private  ArrayList<String> mBaslikList;

    public KayitOlViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
        mFragmentList=new ArrayList<>();
        mBaslikList=new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    public void addFragment(Fragment fragment,String isim)
    {
        mFragmentList.add(fragment);
        mBaslikList.add(isim);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mBaslikList.get(position);
    }

}
