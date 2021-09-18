package com.berkhanakdag.saglicaklauyg.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berkhanakdag.saglicaklauyg.GdetayActivity;
import com.berkhanakdag.saglicaklauyg.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class GonderilerimAdapter extends RecyclerView.Adapter<GonderilerimAdapter.GonderilerimHolder> {

    private ArrayList<String> gonderiId;
    private ArrayList<String> gonderiFoto;
    private ArrayList<String> gonderiBaslik;
    private ArrayList<String> gonderiAciklama;
    private ArrayList<String> gonderiKategoriId;
    private ArrayList<String> gonderiKadSoyad;
    private ArrayList<String> gonderiKid;
    private Context context;

    public GonderilerimAdapter(ArrayList<String> gonderiId, ArrayList<String> gonderiFoto, ArrayList<String> gonderiBaslik, ArrayList<String> gonderiAciklama,
                               ArrayList<String> gonderiKategoriId, ArrayList<String> gonderiKadSoyad, ArrayList<String> gonderiKid, ArrayList<String> gonderiKcinsiyet,
                               ArrayList<String> gonderiKyas, ArrayList<String> gonderiKmail) {
        this.gonderiId = gonderiId;
        this.gonderiFoto = gonderiFoto;
        this.gonderiBaslik = gonderiBaslik;
        this.gonderiAciklama = gonderiAciklama;
        this.gonderiKategoriId = gonderiKategoriId;
        this.gonderiKadSoyad = gonderiKadSoyad;
        this.gonderiKid = gonderiKid;
        this.gonderiKcinsiyet = gonderiKcinsiyet;
        this.gonderiKyas = gonderiKyas;
        this.gonderiKmail = gonderiKmail;
    }

    private ArrayList<String> gonderiKcinsiyet;
    private ArrayList<String> gonderiKyas;
    private ArrayList<String> gonderiKmail;

    @NonNull
    @Override
    public GonderilerimHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.nav_gonderiler,parent,false);
        context=parent.getContext();
        return new GonderilerimHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GonderilerimHolder holder, int position) {
        
        SunucuAdres sunucuAdres=new SunucuAdres();
        holder.txtgonderiBaslik.setText(gonderiBaslik.get(position));
        holder.txtgonderiAdSoyad.setText(gonderiKadSoyad.get(position));
        holder.txtgonderiCinsiyetYas.setText(gonderiKcinsiyet.get(position)+"/"+gonderiKyas.get(position));
        holder.txtgonderiKullaniciAd.setText(gonderiKmail.get(position));
        holder.txtgonderiKategori.setText(gonderiKategoriId.get(position));
        Picasso.get().load(sunucuAdres.getSunucuIp()+"/saglikAPI/"+gonderiFoto.get(position)).resize(150,150).centerCrop().into(holder.gonderiImg);

        holder.yorumImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detayGonder(position);
            }
        });
        holder.gonderiImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detayGonder(position);
            }
        });
        holder.txtgonderiBaslik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detayGonder(position);
            }
        });
        holder.txtgonderiKullaniciAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detayGonder(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gonderiId.size();
    }

    public class GonderilerimHolder extends RecyclerView.ViewHolder {

        ImageView gonderiImg,kullanıcıImg,yorumImg;
        TextView txtgonderiBaslik,txtgonderiAciklama,txtgonderiAdSoyad,txtgonderiCinsiyetYas,txtgonderiKategori,txtgonderiKullaniciAd,txtgonderiYorumSayisi;

        public GonderilerimHolder(@NonNull View itemView) {
            super(itemView);
            txtgonderiBaslik = itemView.findViewById(R.id.gonderilerPaylasimBaslik);
            gonderiImg = itemView.findViewById(R.id.gonderilerPaylasimFoto);
            kullanıcıImg=itemView.findViewById(R.id.gonderiKullaniciFoto);
            yorumImg=itemView.findViewById(R.id.gonderilerYorumBtn);
            txtgonderiAdSoyad = itemView.findViewById(R.id.gonderilerPaylasimAdSoyad);
            txtgonderiCinsiyetYas = itemView.findViewById(R.id.gonderilerPaylasimCinsiyetYas);
            txtgonderiKategori = itemView.findViewById(R.id.gonderilerPaylasimKategori);
            txtgonderiKullaniciAd = itemView.findViewById(R.id.gonderilerPaylasimKulAdi);
            txtgonderiYorumSayisi = itemView.findViewById(R.id.gonderilerYorumSayisi);

        }
    }

    private void detayGonder(int pos)
    {
        Intent intent=new Intent(context, GdetayActivity.class);
        intent.putExtra("gBaslik",gonderiBaslik.get(pos));
        intent.putExtra("gId",gonderiId.get(pos));
        intent.putExtra("gFoto",gonderiFoto.get(pos));
        intent.putExtra("gAciklama",gonderiAciklama.get(pos));
        context.startActivity(intent);
    }
}
