package com.yusufsargin.mpyeni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FragmentsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int NUM = 4;

    DrawerLayout drawerLayout;
    Fragment fragment;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);


        createNavigationDrawer();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null) {
            checkManuf();
        }
        else{
            hideContolPannel();
        }
        Intent ıntent = getIntent();
        System.out.println("saDAD  " + ıntent.getStringExtra("fragmentname"));



        if (ıntent.getStringExtra("fragmentname").equals("mpnedir")){
            fragment=new MpNedirFragment();

        }else if(ıntent.getStringExtra("fragmentname").equals("bizeulas")){
            fragment=new BizeUlasFragment();

        }else if (ıntent.getStringExtra("fragmentname").equals("hakkımızda")){
            fragment=new HakkimizdaFragment();
        }
        else if(ıntent.getStringExtra("fragmentname").equals("gallery")){
            fragment=new GalleryFragment();
        }

        if (fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container, fragment).commit();
        }


        setNavigationTitle();


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


        getSupportActionBar().setTitle("MobilyaPlan");
    }

    private void hideContolPannel() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.controlpannel).setVisible(false);
    }


    private void createNavigationDrawer(){
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
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
                        if (user.getEmail().equals(ds.child("email").getValue().toString())){
                            control++;
                        }
                    }

                    if (control==0){
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
        switch (menuItem.getItemId()){
            case R.id.anasayfa:
                Intent ıntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ıntent);
                break;
            case R.id.mpNedir:
               //if (fragment.getLayoutInflater()==R.layout.mp_nedir_fragment)
                if (fragment!=null){
                    if (fragment== new MpNedirFragment()){
                        drawerLayout.closeDrawers();
                        break;
                    }else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                                new MpNedirFragment()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    }
                }else{
                    drawerLayout.closeDrawers();

                }
                break;
            case R.id.bizeulas:
                if (fragment!=null){
                    if (fragment==new BizeUlasFragment()){
                        drawerLayout.closeDrawers();
                        break;
                    }else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,new BizeUlasFragment()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    }
                }

            case R.id.hakkimizda:
                if (fragment!=null){
                    if (fragment==new HakkimizdaFragment()){
                        drawerLayout.closeDrawers();
                        break;
                    }else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container
                                ,new HakkimizdaFragment()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    }
                }
            case R.id.gallery:
                if (fragment!=null){
                    if (fragment==new GalleryFragment()){
                        drawerLayout.closeDrawers();
                        break;
                    }else     {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container
                        ,new GalleryFragment()).commit();
                        drawerLayout.closeDrawers();
                        break;
                    }
                }
            case R.id.mpBaglan:
               Intent ıntent4 = new Intent(getApplicationContext(),BaglanActivity.class);
               startActivity(ıntent4);
                break;
            case R.id.website:
                Intent ıntent2 = new Intent(getApplicationContext(),webView.class);
                ıntent2.putExtra("website","https://www.mobilyaplan.com");
                startActivity(ıntent2);
                break;

            case R.id.controlpannel:
                Intent ıntent1 = new Intent(getApplicationContext(),ControlPanel.class);
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

    private class ScreenSlide extends FragmentPagerAdapter{

        public ScreenSlide(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            Fragment fragment = new MpNedirFragment();

            switch (i){
                case 0:


            }

            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }


}
