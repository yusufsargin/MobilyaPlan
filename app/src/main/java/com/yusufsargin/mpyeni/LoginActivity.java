package com.yusufsargin.mpyeni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if (user !=null){
            Intent ıntent = new Intent(getApplicationContext(),MainActivity.class);

            startActivity(ıntent);

        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                    new LoginFragment()).commit();
        }



    }


}
