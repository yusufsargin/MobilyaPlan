package com.yusufsargin.mpyeni;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.UUID;

public class kayitOlFragment extends Fragment {

    View view;

    EditText isim,soyisim,email,sifre,companyname,phonenumber;
    Button kayitol;
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.signup_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        phonenumber=view.findViewById(R.id.phonenumber_edittext_signupfragment);
        companyname=view.findViewById(R.id.companyname_edittext_signupfragment);
        isim=view.findViewById(R.id.isim_edittext);
        soyisim=view.findViewById(R.id.soyisim_edittext);
        email=view.findViewById(R.id.email_edittext_signinFragment);
        sifre=view.findViewById(R.id.sifre_edittext_signinFragment);
        kayitol=view.findViewById(R.id.kayityap_siginFragment);
        mAuth=FirebaseAuth.getInstance();

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),sifre.getText().toString())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference("bekleyenkayıtlar");
                        final UUID uuıd = UUID.randomUUID();
                        myref.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                myref.child(uuıd.toString()).child("email").setValue(email.getText().toString());
                                myref.child(uuıd.toString()).child("sifre").setValue(sifre.getText().toString());
                                myref.child(uuıd.toString()).child("isim").setValue(isim.getText().toString());
                                myref.child(uuıd.toString()).child("soyisim").setValue(soyisim.getText().toString());
                                myref.child(uuıd.toString()).child("sirketismi").setValue(companyname.getText().toString());
                                myref.child(uuıd.toString()).child("telno").setValue(phonenumber.getText().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        notificationCreate("Üyeliğiniz için size bilgi verilecektir. Şimdi giriş yapabilirsin. \nTeşekkür ederiz MobilyaPlan ailesine hoşgeldin "+isim.getText().toString());
                        //Toast.makeText(getActivity(),"Üyeliğiniz için size bilgi verilecektir \nTeşekkür ederiz MobilyaPlan ailesine hoşgeldiniz."+ isim.toString(),Toast.LENGTH_LONG).show();
                        //Intent ıntent = new Intent(getActivity(),MainActivity.class);
                        //startActivity(ıntent);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new LoginFragment())
                                .addToBackStack(null).commit();
                    }
                });


            }
        });
    }
    private void notificationCreate(String Text){
        String CHANNEL_ID = "contact";
        String CHANNEL_NAME="check contact";
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
        }
        Intent ıntent = new Intent(getActivity(),BaglanActivity.class);
        ıntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),0,ıntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),CHANNEL_ID)
                .setSmallIcon(R.drawable.mplogo)

                .setContentTitle("MobilyaPlan")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompact = NotificationManagerCompat.from(getActivity());

        managerCompact.notify(0,builder.build());

        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            Notification notificationlast = new Notification.Builder(getActivity())
                    .setContentTitle("MobilyaPlan")
                    .setContentText("Görünüşe göre henüz size ulaşılmadı? Tekrar göndermek için tıklayınız.")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(PendingIntent.getActivity(getActivity(),0,ıntent,PendingIntent.FLAG_UPDATE_CURRENT))
                    .setAutoCancel(true)
                    .getNotification();

            manager.notify(0,notificationlast);
        }
/*
        */

    }
}
