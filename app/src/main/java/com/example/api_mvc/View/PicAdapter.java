package com.example.api_mvc.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api_mvc.Classe.Pics;
import com.example.api_mvc.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.MyViewholder> {

    private ArrayList<Pics> pics;

    public PicAdapter(){
        this.pics = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pics, parent, false);

        return new MyViewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        Pics pic = pics.get(position);
        holder.titulo.setText(pic.getTitle());
        Picasso.get()
                .load(pic.getUrl())
                .resize(200, 200)
                .centerCrop()
                .into(holder.imagem);
    }

    @Override
    public int getItemCount() {
        return pics.size();
    }

    static class MyViewholder extends RecyclerView.ViewHolder {

        TextView titulo;
        ImageView imagem;

        MyViewholder(View view) {
            super(view);

            titulo = view.findViewById(R.id.titulo_pic_recycler);
            imagem = view.findViewById(R.id.image_pic_recycler);

        }
    }

    public void setPics(ArrayList<Pics> pics){
        this.pics = pics;
        notifyDataSetChanged();
    }

}