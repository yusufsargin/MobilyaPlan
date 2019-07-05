package com.yusufsargin.mpyeni;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class ContactControlService extends IntentService {

    private static final String TAG = "ContactControlService";

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myref=database.getReference("musteri");
    ArrayList<String>manufsName;
    ArrayList<String>manufsTel;
    ArrayList<String>manufsEmail;
    String id;
    public ContactControlService() {
        super("CocntatcControlService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {

        manufsName=intent.getStringArrayListExtra("assingManufNameArray");
        manufsTel=intent.getStringArrayListExtra("assingManufTelArray");
        manufsEmail=intent.getStringArrayListExtra("assingManufEmailArray");
        id= intent.getStringExtra("id");

        myref= myref.child(id);

        final int choosen = intent.getIntExtra("chosen",0);
        try {
           // Thread.sleep(3600000);
            Thread.sleep(8000);
            final Random random = new Random();
            final int[] changeNumber = new int[1];
            Query query = myref.orderByChild("contact").equalTo("no");
           // myref.keepSynced(true);
            if (query!=null){


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        manufsEmail.remove(choosen);
                        manufsName.remove(choosen);
                        manufsTel.remove(choosen);


                        changeNumber[0] = random.nextInt(manufsEmail.size());


                        Log.d(TAG, "onDataChange: -------------------------------------------------------------------------------------");
                        myref.child("assingManufName").removeValue();

                        myref.child("assingManufName").setValue("yetar");
                        myref.child("assignManufEmail").setValue(manufsEmail.get(changeNumber[0]));
                        myref.child("assignManufTel").setValue(manufsTel.get(changeNumber[0]));
                        Log.d(TAG, "onDataChange: -------------------------------------------------------------------------------------");
                        Log.d(TAG, "onDataChange: asdd "+ choosen);
                        Log.d(TAG, "onDataChange: asdd"+manufsName.get(choosen));
                        //manufsName.remove(choosen);
                        Log.d(TAG, "onDataChange: asdd"+manufsName.get(changeNumber[0]));


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent ıntent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ıntent1);

            }

/*
            query = myref.child(id).orderByChild("contact").equalTo("yes");
            if (query!=null) {
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChange: adas " + "Stopp");
                        stopService(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }




}
