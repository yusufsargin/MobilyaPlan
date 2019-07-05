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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HizmetlerRecycleviewAdapter extends RecyclerView.Adapter<HizmetlerRecycleviewAdapter.MyViewHolder> {
    Context context;
    View view ;
    ArrayList<String > title ;
    ArrayList<String>image;

    public HizmetlerRecycleviewAdapter(Context context,ArrayList<String >title,ArrayList<String>image){
        this.context=context;
        this.title=title;
        this.image=image;
    }

    @NonNull
    @Override
    public HizmetlerRecycleviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.recycleview_hizmetler_design,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);


        myViewHolder.ımageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(context,webView.class);
                ıntent.putExtra("website","https://www.mobilyaplan.com/cnc-cizimleri");
                context.startActivity(ıntent);
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HizmetlerRecycleviewAdapter.MyViewHolder myViewHolder, int i) {
        if (image.get(i).equals("none")){
            myViewHolder.ımageView.setImageResource(R.drawable.cardviewbackground);
        }else{
            Picasso.with(context).load(image.get(i).toString()).into(myViewHolder.ımageView);
        }
        myViewHolder.title.setText(title.get(i));

    }

    @Override
    public int getItemCount() {
        return title.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ımageView ;
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ımageView=itemView.findViewById(R.id.cardview_Image_recycle_hizmetler);
            title=itemView.findViewById(R.id.cardview_Textview_hizmetler);
        }
    }
}
