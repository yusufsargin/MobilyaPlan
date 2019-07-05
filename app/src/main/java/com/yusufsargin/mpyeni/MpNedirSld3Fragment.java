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

public class MpNedirSld3Fragment extends Fragment {
    View view;
    Button ileriButton,GeriButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.mp_nedir_sld_three,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ileriButton=view.findViewById(R.id.ileriButton);
        GeriButton=view.findViewById(R.id.geriButton);

        ileriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent =new Intent(getActivity(),MainActivity.class);
                startActivity(ıntent);
            }
        });
        GeriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MpNedirFragment.viewPager.setCurrentItem(MpNedirFragment.viewPager.getCurrentItem()-1,true);
            }
        });

    }
}
