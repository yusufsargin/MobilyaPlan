package com.yusufsargin.mpyeni;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    TextView kayitol,resetSifre;
    EditText email,sifre;
    Button devamet,girisyap;

    FirebaseAuth mAuth;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.login_fragment,container,false);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        resetSifre=view.findViewById(R.id.sifreReset_textview);

        girisyap=view.findViewById(R.id.girisyap);
        mAuth=FirebaseAuth.getInstance();
        email=view.findViewById(R.id.login_email_edittext);
        sifre=view.findViewById(R.id.login_sifre_edittext);
        kayitol=view.findViewById(R.id.kayitol_textview);
        devamet=view.findViewById(R.id.button);

        kayitol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new kayitOlFragment()).
                        addToBackStack(null).
                        commit();
            }
        });

        devamet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signInAnonymously().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Toast.makeText(getActivity(),"Hoşgeldiniz",Toast.LENGTH_LONG).show();
                        Intent ıntent = new Intent(getActivity(),MainActivity.class);
                        startActivity(ıntent);
                    }
                });


            }
        });

        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(email.getText().toString(),sifre.getText().toString())
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Intent ıntent = new Intent(getActivity(),MainActivity.class);
                        startActivity(ıntent);
                    }
                });

            }
        });

        resetSifre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_Container,
                        new passwordResetFragment()).
                        addToBackStack(null)
                        .commit();
            }
        });



    }
}

