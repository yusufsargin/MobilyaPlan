package com.yusufsargin.mpyeni;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Picasso;

public class InfoManufactorDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageView manufImage;
    TextView manufname,manufOwnerName,manufOwnerTel,manufAdress;
    NavigationView navigationView;

    Button website,ara;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_manufactor_detail);
        manufImage=findViewById(R.id.manuf_Image);
        manufname=findViewById(R.id.manuf_name_Text);
        final Intent ıntent = getIntent();
        String image= ıntent.getStringExtra("image");
        String name= ıntent.getStringExtra("nameCompany");
        manufname.setText(name);
        Picasso.with(getApplicationContext()).load(image).fit().into(manufImage);

        manufOwnerName=findViewById(R.id.manuf_ownerName_Text);
        manufOwnerTel=findViewById(R.id.manuf_ownerTel_Text);

        manufAdress=findViewById(R.id.manuf_adress_text);
        ara=findViewById(R.id.manuf_ara_button);

        fillInformation();

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makcall();
            }
        });


        website=findViewById(R.id.manuf_website_button);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle("Imalatcılarımız");

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent1 = new Intent(getApplicationContext(),webView.class);
                ıntent1.putExtra("website","https://www.mobilyaplan.com/imalatcilar");
                startActivity(ıntent1);
            }
        });

        checkManuf();

    }

    private void makcall() {
       //Buraada tel aldığın yeri değiştir.
        String number = "tel:" + manufOwnerTel.getText().toString();

        if (number.trim().length()>0){
            if (ContextCompat.checkSelfPermission(InfoManufactorDetail.this,
                    Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(InfoManufactorDetail.this,new String[] {Manifest.permission.CALL_PHONE},1);
            }else {
                String dail = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dail)));
            }
        }else {
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makcall();
            }
        }
    }

    private void fillInformation(){
        final DatabaseReference database= FirebaseDatabase.getInstance().getReference("Imalatcilar");

        database.orderByChild("name").equalTo(manufname.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    manufOwnerTel.setText(ds.child("telno").getValue().toString());
                    manufOwnerName.setText(ds.child("ilgilikisi").getValue().toString());
                    manufAdress.setText(ds.child("adress").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void checkManuf (){
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        FirebaseDatabase mydb = FirebaseDatabase.getInstance();
        DatabaseReference mydbRef = mydb.getReference("Imalatcilar");
        final Menu menu = navigationView.getMenu();
        if (user==null){

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
                        menu.findItem(R.id.controlpannel).setVisible(false);
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
                ıntent1.setClass(getApplicationContext(),MainActivity.class);
                startActivity(ıntent1);
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
            case R.id.mpBaglan:
                Intent ıntent2 = new Intent(getApplicationContext(),BaglanActivity.class);
                startActivity(ıntent2);
                break;
            case R.id.website:
                ıntent1.setClass(getApplicationContext(),webView.class);
                ıntent1.putExtra("webisite","https://www.mobilyaplan.com/");
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

}
