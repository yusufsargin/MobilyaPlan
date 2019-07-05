package com.yusufsargin.mpyeni;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    RecyclerView listViewWaiting,listviewContact;
    DrawerLayout drawerLayout;
    FirebaseDatabase database;
    DatabaseReference manufRef,customerRef;
    TextView tl;
    Button buyMpCoins,contactWithMp;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_panel);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tl=findViewById(R.id.mp_tl_textview);
        listViewWaiting= findViewById(R.id.recycleWaitingCustomer);
        listviewContact=findViewById(R.id.recycleContactCustomer);
        database=FirebaseDatabase.getInstance();

        manufRef=database.getReference("Imalatcilar");
        customerRef=database.getReference("musteri");
        buyMpCoins=findViewById(R.id.tl_buy_button);
        contactWithMp=findViewById(R.id.error_button);

        navigationView = findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

       // Menu menu = navigationView.getMenu();
        //menu.findItem(R.id.controlpannel).setVisible(false);

        contactWithMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorClicked();
            }
        });

        buyMpCoins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyTlClicked();
            }
        });


        setNavigationTitle();


        if (ContextCompat.checkSelfPermission(ControlPanel.this,
                Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ControlPanel.this,new String[] {Manifest.permission.CALL_PHONE},1);
        }

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

        getSupportActionBar().setTitle("Control Paneli");
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
                ıntent1.putExtra("webisite","https://www.mobilyaplan.com/");
                startActivity(ıntent1);
                break;
            case R.id.controlpannel:
                drawerLayout.closeDrawers();
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
        refleshManufCoins();
        refleshWatingCustomer();
        refleshContactedCustomer();
    }


    private void refleshManufCoins(){
        DatabaseReference manuf = database.getReference("Imalatcilar");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        manuf.orderByChild("email").equalTo(user.getEmail().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    tl.setText(ds.child("MpPara").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

            }
        });


    }

    private void refleshWatingCustomer(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = customerRef.orderByChild("assignManufEmail").equalTo(user.getEmail().toString());




        query.addValueEventListener(new ValueEventListener() {

            List<CustomerFromDatabase> customer = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                customer.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("contact").getValue().toString().equals("no")){
                        customer.add(new CustomerFromDatabase(
                                ds.child("CustomerName").getValue().toString(),
                                ds.child("assignManufEmail").getValue().toString(),
                                ds.child("assignManufTel").getValue().toString(),
                                ds.child("contact").getValue().toString().trim(),
                                ds.child("customerid").getValue().toString(),
                                ds.child("phonenumber").getValue().toString(),
                                ds.child("type").getValue().toString(),
                                ds.child("useremail").getValue().toString(),
                                ds.child("assingManufName").getValue().toString()));


                    }
                }
                for (int i =0 ;i<customer.size();i++){
                    System.out.println("asdada "+ customer.get(i).assignManufEmail.equals(user.getEmail()));
                    if (customer.get(i).assignManufEmail.equals(user.getEmail())){
                        continue;
                    }else{
                        customer.remove(i);
                    }
                }

                ListViewWaitingCustomerAdapter listViewWaitingCustomerAdapter = new ListViewWaitingCustomerAdapter(customer,ControlPanel.this,false);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                listViewWaiting.setLayoutManager(linearLayoutManager);
                listViewWaiting.setHasFixedSize(true);
                listViewWaiting.setAdapter(listViewWaitingCustomerAdapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void refleshContactedCustomer(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Query query = customerRef.orderByChild("assignManufEmail").equalTo(user.getEmail().toString());

        query.addValueEventListener(new ValueEventListener() {
            List<CustomerFromDatabase> customer = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // customer.clear();
                customer.clear();


                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if (ds.child("contact").getValue().toString().equals("yes")){
                        customer.add(new CustomerFromDatabase(
                                ds.child("CustomerName").getValue().toString(),
                                ds.child("assignManufEmail").getValue().toString(),
                                ds.child("assignManufTel").getValue().toString(),
                                ds.child("contact").getValue().toString().trim(),
                                ds.child("customerid").getValue().toString(),
                                ds.child("phonenumber").getValue().toString(),
                                ds.child("type").getValue().toString(),
                                ds.child("useremail").getValue().toString(),
                                ds.child("assingManufName").getValue().toString()));


                    }
                }
                for (int i =0 ;i<customer.size();i++){
                    System.out.println("asdada "+ customer.get(i).assignManufEmail.equals(user.getEmail()));
                    if (customer.get(i).assignManufEmail.equals(user.getEmail())){
                        continue;
                    }else{
                        customer.remove(i);

                    }
                }

                ListViewWaitingCustomerAdapter listViewWaitingCustomerAdapter = new ListViewWaitingCustomerAdapter(customer,ControlPanel.this,true);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                listviewContact.setLayoutManager(linearLayoutManager);
                listviewContact.setHasFixedSize(true);
                listviewContact.setAdapter(listViewWaitingCustomerAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void errorClicked(){
        Intent ıntent1 = new Intent(getApplicationContext(),FragmentsActivity.class);
        ıntent1.putExtra("fragmentname","bizeulas");
        startActivity(ıntent1);
    }

    private void buyTlClicked(){
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Dialog mydialog = new Dialog(ControlPanel.this);
        mydialog.setContentView(R.layout.dialog_buy_mpcoin);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView closeDialog = mydialog.findViewById(R.id.close_Button);
        final EditText tlAmount = mydialog.findViewById(R.id.tl_Edittext);
        Button sendToMp = mydialog.findViewById(R.id.send_button);


        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
            }
        });

        sendToMp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] toemail = new String[] {"yusufsargin9@gmail.com"};

                intent.putExtra(Intent.EXTRA_EMAIL,toemail);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Mobilyacı Tl istiyor DİKKAT");
                intent.putExtra(Intent.EXTRA_TEXT, user.getEmail()+" Email uzantılı mobilya plan üyesi "+tlAmount.getText().toString()+ " Adet MpParası satın almak istemektedir. \n \n \n \n Lütfen hiç birşeye dokunmadan sadece göderin. \n Teşekkür ederiz");

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent,"Email Gönder..."));
                mydialog.dismiss();
            }
        });

        mydialog.show();






    }


}
