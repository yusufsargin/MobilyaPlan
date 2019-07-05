package com.yusufsargin.mpyeni;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HakkimizdaFragment extends Fragment {
    View view;
    TextView description_title,description_designer;
    CircleImageView rifat,yusuf;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.hakkimizda,container,false);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        yusuf=view.findViewById(R.id.tasarimci_yusuf);
        rifat=view.findViewById(R.id.tasarimci_rifat);
        description_title=view.findViewById(R.id.title_designer_name_textview);
        description_designer=view.findViewById(R.id.description_designer_textview);

        yusuf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description_title.setText("Yusuf Sargın");
                description_designer.setText(getString(R.string.yusuf_description));

            }
        });

        rifat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description_title.setText("Rıfat Sargın");
                description_designer.setText(getString(R.string.rifat_description));

            }
        });


    }
}
