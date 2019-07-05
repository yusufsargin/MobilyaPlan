package com.yusufsargin.mpyeni;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaglanActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    LocationManager locationManager;
    LocationListener locationListener;
    TextView textView;
    FirebaseDatabase database;
    DatabaseReference myref;
    ImageView manufImage;
    TextView manufName,manufTel;
    InfoFragment customer;

    DrawerLayout drawerLayout;
    NavigationView navigationView;


    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baglan);
        final Intent ıntent = getIntent();
//        textView = findViewById(R.id.location);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        progressBar= findViewById(R.id.Progresbar);

        stopService(new Intent(BaglanActivity.this,CheckContactService.class));



        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                progressBar.setVisibility(View.VISIBLE);
                findmanufFromDatabase(location);

            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent goseetings= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(goseetings);
            }
        };

        izinisteme();

        textView = findViewById(R.id.search_textView);
        //manufImage=findViewById(R.id.manufImage);
        //manufName=findViewById(R.id.manufName);
        //manufTel=findViewById(R.id.manufTel);

        database=FirebaseDatabase.getInstance();
        myref=database.getReference("Imalatcilar");


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


         navigationView= findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        setNavigationTitle();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.controlpannel).setVisible(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //configurelocation();

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

        getSupportActionBar().setTitle("MP Bağlan");
    }

    private void checkManuf (){
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
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
                        if (user.getEmail().equals(ds.child("email"))){
                            control++;
                        }
                    }

                    if (control ==0){
                        Menu menu = navigationView.getMenu();
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
                drawerLayout.closeDrawers();
                break;
            case R.id.gallery:
                ıntent1.putExtra("fragmentname","gallery");
                startActivity(ıntent1);

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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void izinisteme() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
                },10);
                return;
            }else {
                configurelocation();
            }
        }else{

        }
    }
    //Database den veri çekilip Manuf classına atılıyor
    private void findmanufFromDatabase(final Location location){
        final List<Manuf> manufs=  new ArrayList<Manuf>();
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ;
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Manuf manuf = new Manuf(ds.child("name").getValue().toString(),
                            ds.child("latitute").getValue().toString(),
                            ds.child("longitute").getValue().toString(),
                            ds.child("ımage").getValue().toString(),
                            ds.child("telno").getValue().toString(),
                            ds.child("email").getValue().toString(),
                            ds.child("mpCode").getValue().toString());
                    manufs.add(manuf);
                }

                locationDifferance(manufs,location);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    //location farkı bulunuyor
    private void locationDifferance(List<Manuf> manufs,Location location){
        for (int i =0 ; i<manufs.size();i++){
            String l1 = manufs.get(i).latitute;
            String l2=manufs.get(i).longitute;

            Double dl1=Double.parseDouble(l1);
            Double dl2=Double.parseDouble(l2);

            LatLng latLng= new LatLng(dl1,dl2);
            Location manufLoc= new Location("ManufLocation");
            manufLoc.setLatitude(dl1);
            manufLoc.setLongitude(dl2);

            manufs.get(i).locDifferant= location.distanceTo(manufLoc)/1000;
            System.out.println(location.distanceTo(manufLoc));
        }
        findCloseToCustomer(manufs,location);
    }
    //200kmden yakın olanlar listeleniyor
    private void findCloseToCustomer(List<Manuf> manufs,Location location) {
        Random random= new Random();
        int chooseManuf= random.nextInt(manufs.size());
        int sizeofMp=0;
        for(int i=0 ;i<manufs.size();i++){
            if (manufs.get(i).locDifferant<200) {
                sizeofMp++;
            }
        }
        for (int i=0;i<manufs.size();i++){
            if (manufs.get(i).locDifferant>200) {
                manufs.remove(i);
            }
        }
        if (sizeofMp>0){
            Toast.makeText(getApplicationContext(),sizeofMp+" Tane MP Üyesi Bulundu. \n Size En Yakın Mp Üyesi Yönlendiriliyor",Toast.LENGTH_LONG).show();
            whenFound(manufs, chooseManuf);
        }else{
            whenNotFound();
        }

    }
    //seçilenle ilgili işlem burada yapılıyor.
    private void whenFound(List<Manuf>manufs,int postion){
        textView.setText("");
        locationManager.removeUpdates(locationListener);
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<String>manufsName=new ArrayList<>();
        ArrayList<String>manufsTel=new ArrayList<>();
        ArrayList<String>manufsEmail=new ArrayList<>();
        ArrayList<String>manufsTableName=new ArrayList<>();

        for(int i = 0 ; i<manufs.size();i++){
            manufsName.add(manufs.get(i).name);
            manufsTel.add(manufs.get(i).telno);
            manufsEmail.add(manufs.get(i).email);
            manufsTableName.add(manufs.get(i).manufTableName);
        }

        Bundle bundle = new Bundle();

        bundle.putStringArrayList("assingManufNameArray",manufsName);
        bundle.putStringArrayList("assingManufTelArray",manufsTel);
        bundle.putStringArrayList("assingManufEmailArray",manufsEmail);
        bundle.putStringArrayList("assignManufTableName",manufsTableName);
        customer= new InfoFragment();
        customer.setArguments(bundle);
        FragmentManager fragmentManager=(FragmentManager) getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_Container,customer);
        fragmentTransaction.addToBackStack(null);
        if (customer!=null) {
            fragmentTransaction.commitAllowingStateLoss();
        }


    }
    //Bulunamayınca burada işlem yapılıyor.
    private void whenNotFound(){
        locationManager.removeUpdates(locationListener);
        textView.setText("Bulunamadı...");
        progressBar.setVisibility(View.INVISIBLE);
        if (customer!=null){
        FragmentManager fragmentManager= (FragmentManager) getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.remove(customer);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        }

    }




    //izin kontrolü burada yapılıyor.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configurelocation();
                return;
        }
    }

    //location manager to Location listener her 1 saniyede
    private void configurelocation() {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    //Destroy olduğunda Projenin location listener kapatılıyor.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    //Gereksiz ama içerisinde foto yükleme var lazım  olur.
    private void showManuf(List<Manuf>manufs,int postion){
        //Picasso.with(getApplicationContext()).load(manufs.get(postion).ımage).into(manufImage);
        //manufName.setText(manufs.get(postion).name);
        //manufTel.setText(manufs.get(postion).tel);
    }


}
