package com.yusufsargin.mpyeni;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;



public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    List<yeniurunler>urunler;
    Context context;


    public RecycleAdapter(List<yeniurunler>urunler,Context context){
        this.urunler=urunler;
        this.context=context;

    }

    public RecycleAdapter() {
    }


    @NonNull
    @Override
    public RecycleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_yeni,viewGroup,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.MyViewHolder myViewHolder, int i) {

        myViewHolder.textView.setText(urunler.get(i).title);
        Picasso.with(context).load(urunler.get(i).ımage).into(myViewHolder.ımageView);

    }

    @Override
    public int getItemCount() {
        return urunler.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView ımageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ımageView=itemView.findViewById(R.id.ımageview);
            textView=itemView.findViewById(R.id.textview);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"Yükleniyor Bekleyiniz",Toast.LENGTH_LONG).show();
            Intent ıntent = new Intent(context,webView.class);
            ıntent.putExtra("website",urunler.get(getAdapterPosition()).webUrl);
            context.startActivity(ıntent);

        }
    }




}
