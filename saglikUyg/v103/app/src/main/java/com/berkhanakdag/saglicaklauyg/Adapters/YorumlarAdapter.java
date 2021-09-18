package com.berkhanakdag.saglicaklauyg.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.berkhanakdag.saglicaklauyg.AnasayfaActivity;
import com.berkhanakdag.saglicaklauyg.R;
import com.squareup.picasso.Picasso;

import java.net.ContentHandler;
import java.util.ArrayList;

public class YorumlarAdapter extends RecyclerView.Adapter<YorumlarAdapter.YorumHolder> {


    private ArrayList<String> yorumId;
    private ArrayList<String> kullaniciId;
    private ArrayList<String> gonderiId;
    private ArrayList<String> kullaniciAd;
    private ArrayList<String> yorum;
    private ArrayList<String> kullaniciFoto;
    SunucuAdres sunucuAdres;
    Context context;
    public YorumlarAdapter(ArrayList<String> yorumId, ArrayList<String> kullaniciId, ArrayList<String> gonderiId, ArrayList<String> kullaniciAd, ArrayList<String> yorum, ArrayList<String> kullaniciFoto) {
        this.yorumId = yorumId;
        this.kullaniciId = kullaniciId;
        this.gonderiId = gonderiId;
        this.kullaniciAd = kullaniciAd;
        this.yorum = yorum;
        this.kullaniciFoto = kullaniciFoto;
    }

    @NonNull
    @Override
    public YorumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.nav_yorumlar,parent,false);
        context=parent.getContext();

        return new YorumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YorumHolder holder, int position) {

        sunucuAdres = new SunucuAdres();
        if(kullaniciAd.get(position).equals(AnasayfaActivity.getGelenMail()))
        {
            holder.txtKullanici.setTextColor(ContextCompat.getColor(context,R.color.ozel3));
        }
        else{
            holder.txtKullanici.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

        holder.txtKullanici.setText(kullaniciAd.get(position));
        holder.txtYorum.setText(yorum.get(position));
        String uri =sunucuAdres.getSunucuIp()+""+kullaniciFoto.get(position);

        Picasso.get().load(uri).resize(35,35).into(holder.imgKullanici);
    }

    @Override
    public int getItemCount() {
        return yorumId.size();
    }

    public class YorumHolder extends RecyclerView.ViewHolder {

        TextView txtKullanici,txtYorum;
        ImageView imgKullanici;
        public YorumHolder(@NonNull View itemView) {
            super(itemView);

            txtKullanici=itemView.findViewById(R.id.txtYorumKullanici);
            txtYorum=itemView.findViewById(R.id.txtYorum);
            imgKullanici=itemView.findViewById(R.id.imgYorum);
        }
    }
}
