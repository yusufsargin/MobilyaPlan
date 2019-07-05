package com.yusufsargin.mpyeni;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListViewWaitingCustomerAdapter extends RecyclerView.Adapter<ListViewWaitingCustomerAdapter.MyViewHolder> {
    final DatabaseReference myref = FirebaseDatabase.getInstance().getReference("musteri");
    List<CustomerFromDatabase>customer;
    Context context;
    Dialog myDialog;
    boolean contactChange;
    TextView customertel;

    public ListViewWaitingCustomerAdapter (List<CustomerFromDatabase>customer,Context context,boolean contactChange){
        this.customer=customer;
        this.context=context;
        this.contactChange=contactChange;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.waiting_customer_listview,viewGroup,false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);


        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.dialog_customerinfo_controlpannel);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myViewHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final boolean[] canShow = {false};

                TextView customerName = myDialog.findViewById(R.id.customerName);
                customertel= myDialog.findViewById(R.id.customerTel_textview);
                Button closeDialog = myDialog.findViewById(R.id.close_dialog);
                customerName.setText(customer.get(myViewHolder.getAdapterPosition()).CustomerName);
                customertel.setText(customer.get(myViewHolder.getAdapterPosition()).phonenumber);
                Button makeCall = myDialog.findViewById(R.id.make_call_button);


                final DatabaseReference myManufRef = FirebaseDatabase.getInstance().getReference("Imalatcilar");

                if (contactChange==false){

                    Query query=myManufRef.orderByChild("email").equalTo(customer.get(myViewHolder.getAdapterPosition()).assignManufEmail.toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String mpcode="";
                            int coins=0;
                            for (DataSnapshot ds : dataSnapshot.getChildren()){
                                mpcode  = ds.child("mpCode").getValue().toString();
                                coins=Integer.parseInt(ds.child("MpPara").getValue().toString());
                            }
                            if (coins<=0){
                                myDialog.dismiss();
                                customertel.setText("");
                                Toast.makeText(context,"Yeterli Paranız Yok",Toast.LENGTH_LONG).show();
                                canShow[0] =false;

                            }else{
                                coins--;
                                myManufRef.child(mpcode).child("MpPara").setValue(coins);
                                canShow[0] =true;
                                setContact(myViewHolder);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    makeCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            makcall();
                        }
                    });

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });

                }else{
                    makeCall.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent ıntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+customertel));
                            context.startActivity(ıntent);
                        }
                    });

                    closeDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });

                }
                myDialog.show();

            }

        });


        return myViewHolder;
    }


    private void makcall() {
        if (ContextCompat.checkSelfPermission(context,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED){
            //Buraada tel aldığın yeri değiştir.
            String number = "tel:" + customertel.getText().toString();
            String dail = "tel:" + number;
            context.startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dail)));
        }
    }





    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.textView.setText(customer.get(i).CustomerName);


    }

    @Override
    public int getItemCount() {

        return customer.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

        LinearLayout item_contact;
        TextView textView,customerid;
        DatabaseReference myref = FirebaseDatabase.getInstance().getReference("musteri");

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            item_contact=(LinearLayout) itemView.findViewById(R.id.item_contact);
            textView=itemView.findViewById(R.id.customerName);
            customerid  =itemView.findViewById(R.id.customerid);
        }


    }


    private void setContact(final MyViewHolder myViewHolder){
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myref.child(customer.get(myViewHolder.getAdapterPosition()).customerid.toString()).child("contact").setValue("yes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
