package com.yusufsargin.mpyeni;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MpNedirFragment extends Fragment {


    ImageView ımageView;

    static ViewPager viewPager;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return view=inflater.inflate(R.layout.mp_nedir_fragment,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager);

        SlideScreen slideScreen = new SlideScreen(getChildFragmentManager());
        viewPager.setAdapter(slideScreen);
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
    }


    private class SlideScreen extends FragmentPagerAdapter {
        Fragment fragment ;

        public SlideScreen(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0:
                    return fragment=new MpNedirSld1Fragment();
                case 1:
                    return fragment=new MpNedirSld2Fragment();
                case 2:
                    return fragment=new MpNedirSld3Fragment();

            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }


    }




}
