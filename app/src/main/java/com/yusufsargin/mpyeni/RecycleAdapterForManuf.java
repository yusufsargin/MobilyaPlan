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

import java.util.List;

public class RecycleAdapterForManuf extends RecyclerView.Adapter<RecycleAdapterForManuf.MyViewHolder> {

    List<ManufOnMainActivity>manufs;
    Context context;
    View view;

    public RecycleAdapterForManuf(List<ManufOnMainActivity>manufs,Context context){
        this.manufs=manufs;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manuf_mainactivity,viewGroup,false);
        MyViewHolder myViewHolder= new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Picasso.with(context).load(manufs.get(i).ımage).into(myViewHolder.manufimage);
        myViewHolder.manufname.setText(manufs.get(i).name);

        myViewHolder.manufimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(context,InfoManufactorDetail.class);

                ıntent.putExtra("image",manufs.get(i).ımage);
                ıntent.putExtra("nameCompany",manufs.get(i).name);
                ıntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(ıntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return manufs.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView manufimage;
        TextView manufname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            manufimage= itemView.findViewById(R.id.manuf_Image);
            manufname=itemView.findViewById(R.id.manuf_name_Text);

        }
    }


}
