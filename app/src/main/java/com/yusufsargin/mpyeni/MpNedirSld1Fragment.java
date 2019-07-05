package com.yusufsargin.mpyeni;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MpNedirSld1Fragment extends Fragment {
    View view;
    Button ileriButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.mp_nedir_sld_one,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ileriButton=view.findViewById(R.id.ileriButton);


        ileriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MpNedirFragment.viewPager.setCurrentItem(MpNedirFragment.viewPager.getCurrentItem()+1,true);

            }
        });
    }
}
