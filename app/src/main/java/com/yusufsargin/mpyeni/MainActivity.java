package com.yusufsargin.mpyeni;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{




    RecyclerView recyclerView,manufRecycleView;
    FirebaseDatabase database;
    DatabaseReference myRef,manufRef,customerRef;
    StorageReference storageReference;
    FirebaseStorage storage;
    static ImageView btn;
    ImageView mutfakHesabi,DuvarHesabi,MerdivenHesabi;
    RecycleAdapterForManuf adapterForManuf;
    List<ManufOnMainActivity>manuf;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Button gallery;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Intent ıntent = getIntent();
        if (ıntent.getStringExtra("close").equals("yes")){
            btn.setVisibility(View.INVISIBLE);
        }


*/


        mutfakHesabi=findViewById(R.id.mutfakHesabi);
        DuvarHesabi=findViewById(R.id.duvarHesabi);
        MerdivenHesabi=findViewById(R.id.merdivenHesabi);


        mutfakHesabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(),webView.class);
                ıntent.putExtra("website","https://www.mobilyaplan.com/mutfak-dolabi-maliyet-hesabi");
                startActivity(ıntent);
            }
        });

        DuvarHesabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(),webView.class);
                ıntent.putExtra("website","https://www.mobilyaplan.com/duvar-tasarimi");
                startActivity(ıntent);
            }
        });

        MerdivenHesabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent =new Intent(getApplicationContext(),webView.class);
                ıntent.putExtra("website","https://www.mobilyaplan.com/ahsap-merdiven-hesabi");
                startActivity(ıntent);
            }
        });

        gallery=findViewById(R.id.gallery_button);


        database=FirebaseDatabase.getInstance();
        myRef=database.getReference().child("yeniurunler");
        manufRef=database.getReference().child("Imalatcilar");
        customerRef=database.getReference().child("musteri");
        customerRef.keepSynced(true);
        myRef.keepSynced(true);
        manufRef.keepSynced(true);
        recyclerView=findViewById(R.id.recycleYeniler);
        manufRecycleView=findViewById(R.id.recycleImalatcilar);
        btn =findViewById(R.id.mpBaglan_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(),BaglanActivity.class);
                startActivity(ıntent);
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent=new Intent(getApplicationContext(),FragmentsActivity.class);
                ıntent.putExtra("fragmentname","gallery");
                startActivity(ıntent);
            }
        });

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Mobilya Plana Hoşgeldiniz");

        checkManuf();


        setNavigationTitle();
        createHizmetlerimiz();

    }
    private void setNavigationTitle(){
        View view = navigationView.getHeaderView(0);
        TextView nav_text = view.findViewById(R.id.customerName);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            nav_text.setText(user.getEmail());
        }else{
            nav_text.setText("Misafir");
        }

        getSupportActionBar().setTitle("Mobilya Plana Hoşgeldiniz");
    }

    private void checkManuf (){
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        FirebaseDatabase mydb = FirebaseDatabase.getInstance();
        DatabaseReference mydbRef = mydb.getReference("Imalatcilar");

        if (user==null){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.controlpannel).setVisible(false);
        }else{

            mydbRef.addValueEventListener(new ValueEventListener() {
                int control;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        if (user.getEmail().equals(ds.child("email").getValue().toString())){
                            control++;
                        }
                    }

                    if (control ==0){
                        Menu menu = navigationView.getMenu();
                        menu.findItem(R.id.controlpannel).setVisible(false);
                    }else{
                        btn.setVisibility(View.INVISIBLE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuNavigation(menuItem);

        return true;
    }
    private void menuNavigation(MenuItem menuItem){
        Intent ıntent1 = new Intent(getApplicationContext(),FragmentsActivity.class);
        switch (menuItem.getItemId()){
            case R.id.anasayfa:
                drawerLayout.closeDrawers();
                break;
            case R.id.mpNedir:
                ıntent1.putExtra("fragmentname","mpnedir");
                startActivity(ıntent1);
                break;
            case R.id.bizeulas:
                ıntent1.putExtra("fragmentname","bizeulas");
                startActivity(ıntent1);
                break;
            case R.id.hakkimizda:
                ıntent1.putExtra("fragmentname","hakkımızda");
                startActivity(ıntent1);
                break;
            case R.id.gallery:
                ıntent1.putExtra("fragmentname","gallery");
                startActivity(ıntent1);
                break;
            case R.id.mpBaglan:
                Intent ıntent2 = new Intent(getApplicationContext(),BaglanActivity.class);
                startActivity(ıntent2);
                break;
            case R.id.website:
                ıntent1.setClass(getApplicationContext(),webView.class);
                ıntent1.putExtra("website","https://www.mobilyaplan.com");
                startActivity(ıntent1);
                break;
            case R.id.controlpannel:
                ıntent1.setClass(getApplicationContext(),ControlPanel.class);
                startActivity(ıntent1);
                break;
            case R.id.logout:
                FirebaseAuth kullanici= FirebaseAuth.getInstance();
                kullanici.signOut();
                Intent ıntent3 = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(ıntent3);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        refleshYeniRecycle();
        refleshManufRecycle();


    }

    private void createHizmetlerimiz(){
        final RecyclerView hizmetler = findViewById(R.id.recyclerViewHizmetler);
        DatabaseReference hizRef = FirebaseDatabase.getInstance().getReference("hizmetlerimiz");

        hizRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> title = new ArrayList<>();
                ArrayList<String>image = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    title.add(ds.child("title").getValue().toString());
                    if (!ds.child("image").getValue().toString().equals("none")){
                        image.add(ds.child("image").getValue().toString());
                    }else{
                        image.add("none");
                    }
                }

                HizmetlerRecycleviewAdapter adapter = new HizmetlerRecycleviewAdapter(MainActivity.this,title,image);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                hizmetler.setLayoutManager(linearLayoutManager);
                hizmetler.setHasFixedSize(true);
                hizmetler.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    private void refleshYeniRecycle() {
        myRef.addValueEventListener(new ValueEventListener() {
            List<yeniurunler> urunler = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                urunler.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    urunler.add(new yeniurunler(ds.child("title").getValue().toString(), ds.child("ımage").getValue().toString(), ds.child("webUrl").getValue().toString()));


                }

                RecycleAdapter recycleAdapter = new RecycleAdapter(urunler, getApplicationContext());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(recycleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Yeni Ürünler Yüklenemedi",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void refleshManufRecycle(){

        manuf= new ArrayList<>();
        adapterForManuf = new RecycleAdapterForManuf(manuf,getApplicationContext());
        manufRef.addValueEventListener(new ValueEventListener() {
            String control;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                manuf.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    manuf.add(new ManufOnMainActivity(ds.child("name").getValue().toString(),ds.child("ımage").getValue().toString(),ds.child("reklamhakki").getValue().toString()));

                }
                for (int i=0 ;i< manuf.size();i++){
                    if (manuf.get(i).reklamhakki.equals("no")){
                        manuf.remove(i);
                        adapterForManuf.notifyDataSetChanged();
                    }
                }

                if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
                    manufRecycleView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                }else{
                    manufRecycleView.setLayoutManager(new GridLayoutManager(getApplicationContext(),4));
                }
               // manufRecycleView.hasFixedSize(true);
                manufRecycleView.setAdapter(adapterForManuf);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Imalatcılar Yüklenemedi",Toast.LENGTH_LONG).show();
            }
        });
    }


}


