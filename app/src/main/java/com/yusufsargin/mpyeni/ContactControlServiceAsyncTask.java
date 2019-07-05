package com.yusufsargin.mpyeni;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class ContactControlServiceAsyncTask extends Service {
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myref=database.getReference("musteri");

    ArrayList<String>manufName;
    ArrayList<String>manufTel;
    ArrayList<String>manufEmail;
    String name;
    String email;
    String telno;
    String type;
    String  contact;
    String customerid;
    Integer choosen;
    Manuf manufs;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manufName= intent.getStringArrayListExtra("assingManufNameArray");
        manufTel=intent.getStringArrayListExtra("assingManufTelArray");
        manufEmail=intent.getStringArrayListExtra("assingManufEmailArray");
        name=intent.getStringExtra("CustomerName");
        email=intent.getStringExtra("useremail");
        telno=intent.getStringExtra("phonenumber");
        type=intent.getStringExtra("type");
        contact=intent.getStringExtra("contact");
        customerid=intent.getStringExtra("customerid");
        choosen=intent.getIntExtra("choosen",0);

        //myref.keepSynced(true);
        CustomerInformationClass customer = new CustomerInformationClass(manufName,manufTel,manufEmail,name,email,telno,type,contact,customerid);




        manufs= new Manuf(manufName,manufEmail,manufTel,customerid);

        BacgorundControl control = new BacgorundControl();
        control.execute(customer);


        return super.onStartCommand(intent, flags, startId);
    }

    class BacgorundControl extends AsyncTask<CustomerInformationClass,Void,Void> {



        @Override
        protected Void doInBackground(final CustomerInformationClass... customerInformationClasses) {

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


                /*
                Query query = myref.child(customerInformationClasses[0].customerid.toString()).orderByChild("contact").equalTo("no");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        manufEmail.remove(choosen);
                        manufName.remove(choosen);
                        manufTel.remove(choosen);


                        changeNumber[0] = random.nextInt(manufEmail.size());

                        myref.child(customerInformationClasses[0].customerid).child("assignManufName").setValue(manufs.manufName.get(changeNumber[0]));
                        myref.child(customerInformationClasses[0].customerid).child("assignManufEmail").setValue(manufs.manufEmail.get(changeNumber[0]));
                        myref.child(customerInformationClasses[0].customerid).child("assignManufTel").setValue(manufs.manufTel.get(changeNumber[0]));
                        // myref.child(customerInformationClasses[0].customerid).child(customerInformationClasses[0].telno).setValue("Tek Phone");
                        // myref.child(customerInformationClasses[0].customerid).child(customerInformationClasses[0].name).setValue("Tek İsim");
                        // myref.child(customerInformationClasses[0].customerid).child(customerInformationClasses[0].email).setValue("Tek Email");
                        // myref.child(customerInformationClasses[0].customerid).child(customerInformationClasses[0].type).setValue("Type");
                        // myref.child(customerInformationClasses[0].customerid).child(customerInformationClasses[0].contact).setValue("Yes");
                        // myref.child(customerInformationClasses[0].customerid).child(customerInformationClasses[0].customerid).setValue("id");

                        //myref.child(customerInformationClasses[0].customerid).child("assingManufName").setValue(customerInformationClasses[0].name);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
            final Random random = new Random();
            final int[] changeNumber = new int[1];


            Query query = myref.child(customerid);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        if (ds.child("contact").getValue().equals("no")){

                            manufEmail.remove(choosen);
                            manufName.remove(choosen);
                            manufTel.remove(choosen);


                            changeNumber[0] = random.nextInt(manufEmail.size());

                            myref.child(customerid).child("assignManufName").setValue(manufName.get(changeNumber[0]));
                            myref.child(customerid).child("assignManufEmail").setValue(manufEmail.get(changeNumber[0]));
                            myref.child(customerid).child("assignManufTel").setValue(manufTel.get(changeNumber[0]));
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            System.out.println("çalışsaıd   ");
            //Intent ıntent1 = new Intent(getApplicationContext(),MainActivity.class);
            //startActivity(ıntent1);


            return null;
        }
    }
}
