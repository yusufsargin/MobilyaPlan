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

public class BizeUlasFragment extends Fragment {

    View view;
    Button send;
    EditText subject,mainText;
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.bizeulas_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subject = view.findViewById(R.id.email_Subject);
        mainText=view.findViewById(R.id.email_mainText);
        textView=view.findViewById(R.id.email_to_text);
        send= view.findViewById(R.id.send_Button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String[] toemail = new String[] {"mobilyaplan@gmail.com"};

                intent.putExtra(Intent.EXTRA_EMAIL,toemail);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,mainText.getText().toString());

                intent.setType("message/rfc822");

                startActivity(Intent.createChooser(intent,"Email Gönder..."));
            }
        });


    }
}
