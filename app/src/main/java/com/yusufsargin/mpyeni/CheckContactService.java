package com.yusufsargin.mpyeni;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckContactService extends Service {

    DatabaseReference myref = FirebaseDatabase.getInstance().getReference("musteri");

    boolean result=false;
    String idtoIntent;
    int startid;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationCreate(String Text){
        String CHANNEL_ID = "contact";
        String CHANNEL_NAME="check contact";
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
        }
        Intent ıntent = new Intent(getApplicationContext(),BaglanActivity.class);
        ıntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,ıntent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.mplogo)

                .setContentTitle("MobilyaPlan")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompact = NotificationManagerCompat.from(getApplicationContext());

        managerCompact.notify(0,builder.build());
        result=true;
/*
        Notification notification = new Notification.Builder(getApplicationContext())
                .setContentTitle("MobilyaPlan")
                .setContentText("Görünüşe göre henüz size ulaşılmadı? Tekrar göndermek için tıklayınız.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(),0,ıntent,PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
                .getNotification();

        manager.notify(0,notification);*/

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String id = intent.getStringExtra("id");
        idtoIntent=id;
        startid=startId;
        /*

        idtoIntent=id;
        //Bacground bacground = new Bacground(id);
        //bacground.execute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Çalışmaya başladı");
                try {
                    Thread.sleep(1000*10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                myref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(id).child("contact").getValue().toString().equals("no")) {
                            System.out.println("Çalışmaya devam");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationCreate("Görünüşe göre henüz size ulaşılmadı? \n Tekrar göndermek için tıklayınız.");
                            }else {
                                Intent ıntent = new Intent(getApplicationContext(),BaglanActivity.class);
                                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                Notification notification = new Notification.Builder(getApplicationContext())
                                        .setContentTitle("MobilyaPlan")
                                        .setContentText("Görünüşe göre henüz size ulaşılmadı? \nTekrar göndermek için tıklayınız.")
                                        .setSmallIcon(R.drawable.mplogo)
                                        .setContentIntent(PendingIntent.getActivity(getApplicationContext(),0,ıntent,PendingIntent.FLAG_UPDATE_CURRENT))
                                        .setAutoCancel(true)
                                        .getNotification();

                                manager.notify(0,notification);
                                result=true;


                            }


                        }else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationCreate("Hizmetimiziden memnun kaldınız mı?");

                            }else{

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }).start();


        stopSelf();*/
        Bacground bacground = new Bacground(id);
        bacground.execute();

        return super.onStartCommand(intent, flags, startId);
    }




    class Bacground extends AsyncTask<Void,Void,Void>{

        String id ;
        public Bacground (String id ){
            this.id=id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            System.out.println("Çalışmaya başladı");


                    try {

                        Thread.sleep(1000*15);
                        myref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child(id).child("contact").getValue().toString().equals("no")) {
                                    System.out.println("Çalışmaya devam");
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        notificationCreate("Görünüşe göre henüz size ulaşılmadı? \nTekrar göndermek için tıklayınız.");
                                    }else {
                                        Intent ıntent = new Intent(getApplicationContext(),BaglanActivity.class);
                                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                        Notification notification = new Notification.Builder(getApplicationContext())
                                                .setContentTitle("MobilyaPlan")
                                                .setContentText("Görünüşe göre henüz size ulaşılmadı? \nTekrar göndermek için tıklayınız.")
                                                .setSmallIcon(R.drawable.mplogo)
                                                .setContentIntent(PendingIntent.getActivity(getApplicationContext(),0,ıntent,PendingIntent.FLAG_UPDATE_CURRENT))
                                                .setAutoCancel(true)
                                                .getNotification();

                                        manager.notify(0,notification);

                                    }


                                }else{
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        notificationCreate("Hizmetimiziden memnun kaldınız mı?");
                                    }else{

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    stopSelf(startid);

            return null;
        }
    }

}
