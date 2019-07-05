package com.yusufsargin.mpyeni;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class webView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private static final String TAG = "webView";

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    WebView webView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent ıntent = getIntent();
        String webUrl= ıntent.getStringExtra("website");
        progressBar=findViewById(R.id.Progresbar);

        webView = findViewById(R.id.webView);

        webView.loadUrl(webUrl);
        progressBar.setMax(100);



        try {

            WebSettings webSettings =  webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);

                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }catch (Exception e ){
            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
        }


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawerlayout);

        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setNavigationTitle();

        checkManuf();

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
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

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

        getSupportActionBar().setTitle("MP Web");
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

                Intent ıntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(ıntent);
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
            case R.id.gallery:
                ıntent1.putExtra("fragmentname","gallery");
                startActivity(ıntent1);
                break;
            case R.id.website:
                drawerLayout.closeDrawers();
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
