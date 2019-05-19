package com.client.galleryapp.collage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.client.galleryapp.R;
import com.client.galleryapp.SelectPhotoActivity;

public class TabCollage extends Fragment {
    FloatingActionButton fab_collage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collage_tab, container, false);
        fab_collage = rootView.findViewById(R.id.fab_collage);
        fab_collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(),  SelectPhotoActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

}

