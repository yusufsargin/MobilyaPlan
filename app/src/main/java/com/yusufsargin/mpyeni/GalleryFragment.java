package com.yusufsargin.mpyeni;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    RecyclerView recyclerView;
    GalleryRecycleviewAdapter adapter;
    StaggeredGridLayoutManager manager;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("gallery");
    View view;
    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.gallery_fragment,container,false);
        context=getActivity();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayList<GalleryFromDatabase> gallery = new ArrayList<>();

        recyclerView= view.findViewById(R.id.recycleviewGallery);
        manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gallery.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    gallery.add(new GalleryFromDatabase(
                            ds.child("baslik").getValue().toString(),
                            ds.child("baslikfoto").getValue().toString(),
                            ds.child("weburl").getValue().toString(),
                            ds.child("satinalurl").getValue().toString()
                    ));
                    adapter= new GalleryRecycleviewAdapter(gallery,context);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(manager);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });



    }
}
