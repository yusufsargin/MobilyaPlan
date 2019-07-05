package com.yusufsargin.mpyeni;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryRecycleviewAdapter extends RecyclerView.Adapter<GalleryRecycleviewAdapter.Myviewholder> {

    ArrayList<GalleryFromDatabase>gallery;
    View view;
    ViewGroup viewGroup;
    Context context;

    public GalleryRecycleviewAdapter(ArrayList<GalleryFromDatabase> gallery,Context context) {
        this.gallery = gallery;
        this.context=context;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_recycleview_layout,viewGroup,false);
        Myviewholder myviewholder = new Myviewholder(view);
        this.viewGroup=viewGroup;
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder myviewholder, final int i) {
        Picasso.with(context).load(gallery.get(i).baslikfoto).into(myviewholder.ımageView);
        myviewholder.textView.setText(gallery.get(i).baslik);
        myviewholder.satinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(context,webView.class);
                ıntent.putExtra("website",gallery.get(i).satinalurl);
                context.startActivity(ıntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gallery.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        ImageView ımageView ;
        TextView textView;
        Button satinal;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            ımageView= itemView.findViewById(R.id.product_imageview);
            satinal=itemView.findViewById(R.id.satinal_button);
            textView=itemView.findViewById(R.id.product_name_textview);
        }
    }
}
