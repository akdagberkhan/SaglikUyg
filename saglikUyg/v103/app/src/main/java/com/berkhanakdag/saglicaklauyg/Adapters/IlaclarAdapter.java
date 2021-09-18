package com.berkhanakdag.saglicaklauyg.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.berkhanakdag.saglicaklauyg.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class IlaclarAdapter extends RecyclerView.Adapter<IlaclarAdapter.IlaclarHolder> {

    private ArrayList<String> ilacAdList,ilacKullanim,ilacKullanimA,baslangicT;
    private SunucuAdres sunucuAdres;
    private Context mContext;

    public IlaclarAdapter(ArrayList<String> ilacAdList, ArrayList<String> ilacKullanim, ArrayList<String> ilacKullanimA, ArrayList<String> baslangicT) {
        this.ilacAdList = ilacAdList;
        this.ilacKullanim = ilacKullanim;
        this.ilacKullanimA = ilacKullanimA;
        this.baslangicT = baslangicT;
    }

    @NonNull
    @Override
    public IlaclarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.nav_ilaclar,parent,false);
        mContext=parent.getContext();
        return new IlaclarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IlaclarHolder holder, int position) {

        holder.txtIlacAd.setText(ilacAdList.get(position).toUpperCase());
        holder.txtKullanimA.setText("Kullanım Aralığı: "+ilacKullanimA.get(position));
        holder.txtKullanim.setText("Kullanım Şekli: "+ilacKullanim.get(position));
        holder.txtBaslangicTarih.setText("Başlangıç Tarihi: "+baslangicT.get(position));


    }

    @Override
    public int getItemCount() {
        return ilacAdList.size();
    }

    public class IlaclarHolder extends RecyclerView.ViewHolder {

        TextView txtIlacAd,txtKullanim,txtKullanimA,txtBaslangicTarih;
        public IlaclarHolder(@NonNull View itemView) {
            super(itemView);

            txtIlacAd = itemView.findViewById(R.id.txtIlacAd);
            txtKullanim=itemView.findViewById(R.id.txtKullanim);
            txtKullanimA=itemView.findViewById(R.id.txtKullanimAralik);
            txtBaslangicTarih=itemView.findViewById(R.id.txtBaslangicTarih);
        }
    }
}
