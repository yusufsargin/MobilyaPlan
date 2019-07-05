package com.yusufsargin.mpyeni;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class InfoFragment extends Fragment  {

    static SQLiteDatabase sqldatabase;
    EditText email,telno,name;
    Spinner type;
    View view;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String wishType;
    UUID uuıd ;
    String id;
    Button delete;
    Button save;
    ArrayList<String> manufsName = new ArrayList<>();
    ArrayList<String >manufsTel=new ArrayList<>();
    ArrayList<String>manufsEmail=new ArrayList<>();
    ArrayList<String>manufsTableName=new ArrayList<>();
    int choose;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_customer_info,container,false);



        manufsName=getArguments().getStringArrayList("assingManufNameArray");
        manufsTel=getArguments().getStringArrayList("assingManufTelArray");
        manufsEmail=getArguments().getStringArrayList("assingManufEmailArray");
        manufsTableName=getArguments().getStringArrayList("assignManufTableName");

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        email= view.findViewById(R.id.email_editText);
        telno=view.findViewById(R.id.phone_editText);
        type=view.findViewById(R.id.type_spinner);
        save=view.findViewById(R.id.save_button);
        name=view.findViewById(R.id.name_editText);
        delete=view.findViewById(R.id.delete_all_button);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("musteri");
        wishType= type.getSelectedItem().toString();




        sqldatabase = getActivity().openOrCreateDatabase("information",Context.MODE_PRIVATE,null);

        sqldatabase.execSQL("CREATE TABLE IF NOT EXISTS information (name VARCHAR ,email VARCHAR ,phone VARCHAR,type INTEGER)");

        Cursor cursor = sqldatabase.rawQuery("SELECT * FROM information ",null);


        final int nameIx = cursor.getColumnIndex("name");
        int emailIx = cursor.getColumnIndex("email");
        int phoneIx = cursor.getColumnIndex("phone");
        final int typeIx = cursor.getColumnIndex("type");



        if (cursor != null && cursor.moveToFirst()) {
                name.setText(cursor.getString(nameIx));
                email.setText(cursor.getString(emailIx));
                telno.setText(cursor.getString(phoneIx));
                type.setSelection(cursor.getInt(typeIx));
               // Toast.makeText(getActivity(),"Tekrar göndermek için sadece tıklayın",Toast.LENGTH_SHORT).show();
        }





        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqldatabase.delete("information","phone="+telno.getText().toString(),null);
                name.setText("");
                email.setText("");
                telno.setText("");
                type.setSelection(0);

            }
        });


       // email.setHint(name.getHint().toString());

        //start Service

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setHint(null);
            }
        });

        telno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telno.setHint(null);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setHint(null);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (email.getText().toString().length()<2 || name.getText().toString().length() <2 || telno.getText().toString().length()<6) {
                    Toast.makeText(getActivity(),"Lütften Bütün Alanları Doldurunuz",Toast.LENGTH_LONG).show();
                }else {
                    saveCustomerInfo(name.getText().toString(),email.getText().toString(), telno.getText().toString(),wishType);
/*
                Intent openService = new Intent(getActivity(),ContactControlServiceAsyncTask.class);
                openService.putStringArrayListExtra("assingManufNameArray",manufsName);
                openService.putStringArrayListExtra("assingManufTelArray",manufsTel);
                openService.putStringArrayListExtra("assingManufEmailArray",manufsEmail);
                openService.putExtra("CustomerName",name.toString());
                openService.putExtra("useremail",email.toString());
                openService.putExtra("phonenumber",telno.toString());
                openService.putExtra("type",type.toString());
                openService.putExtra("contact","no");
                openService.putExtra("id",id);
                openService.putExtra("choosen",choose);
                getActivity().startService(openService);

*/
                    int typePosition = type.getSelectedItemPosition();
                    sqldatabase.delete("information","phone="+telno.getText().toString(),null);
                    sqldatabase.execSQL("INSERT INTO information (name,email,phone,type) VALUES ("
                            +"'"+name.getText().toString()+"'"+
                             ","
                            +"'"+email.getText().toString()+"'"
                            +","
                            +"'"+telno.getText().toString()+"'"
                            +","
                            +typePosition
                            +")");

                    Intent ıntent = new Intent(getActivity(),MainActivity.class);
                    ıntent.putExtra("close","yes");
                    startActivity(ıntent);

            }
            }
        });


    }

    private void saveCustomerInfo(final String name, final String email, final String telno, final String wishType) {
       uuıd = UUID.randomUUID();
        id = uuıd.toString();
        Random random = new Random();
       choose = random.nextInt(manufsEmail.size());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child(id).child("CustomerName").setValue(name);
                myRef.child(id).child("useremail").setValue(email);
                myRef.child(id).child("phonenumber").setValue(telno);
                myRef.child(id).child("type").setValue(wishType);
                myRef.child(id).child("assingManufName").setValue(manufsName.get(choose));
                myRef.child(id).child("assignManufEmail").setValue(manufsEmail.get(choose));
                myRef.child(id).child("assignManufTel").setValue(manufsTel.get(choose));
                myRef.child(id).child("contact").setValue("no");
                myRef.child(id).child("customerid").setValue(id);
                myRef.child(id).child("assignManufTableName").setValue(manufsTableName.get(choose));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent ıntent = new Intent(getActivity(),CheckContactService.class);
        ıntent.putExtra("id",id);
        getActivity().startService(ıntent);

    }

}
