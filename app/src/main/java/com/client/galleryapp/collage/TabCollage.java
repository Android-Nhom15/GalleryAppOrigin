package com.client.galleryapp.collage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.client.galleryapp.FullScreenPhoto;
import com.client.galleryapp.R;
import com.client.galleryapp.SelectPhotoActivity;
import com.client.galleryapp.adapters.AlbumAdapter;
import com.client.galleryapp.adapters.ImagesAdapter;
import com.client.galleryapp.allphotos.AllPhotoAdapter;
import com.client.galleryapp.allphotos.TabAllPhoto;
import com.client.galleryapp.resourcedata.GetResource;
import com.client.galleryapp.tabalbum.Album;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class TabCollage extends Fragment {
    FloatingActionButton fab_collage;
    GridView gridView;
    SwipeRefreshLayout swipeLayout;
    ArrayList<File> coll = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collage_tab, container, false);
        fab_collage = rootView.findViewById(R.id.fab_collage);
        gridView = rootView.findViewById(R.id.gridview_album_collage);
        swipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container3);
        ArrayList<Album> mAlbum = new ArrayList<>();
        GetResource getResource = new GetResource(getActivity());
        mAlbum = getResource.getAllShownImagesPath();
        coll.clear();
        for(Album a: mAlbum){
            if(a.getName().equals("Collage"))
            coll.addAll(a.getImages());
        }

        ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), R.layout.collage_tab, coll);
        gridView.setAdapter(imagesAdapter);
        gridView.setOnItemClickListener(itemClickListener);
        fab_collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),  SelectPhotoActivity.class);
                startActivity(intent);
            }
        });
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        coll.clear();
                        ArrayList<Album> mAlbum1 = new ArrayList<>();
                        GetResource getResource1 = new GetResource(getActivity());
                        getResource1 = new GetResource(getActivity());
                        mAlbum1 = getResource1.getAllShownImagesPath();
                        for(Album a: mAlbum1){
                            if(a.getName().equals("Collage"))
                                coll.addAll(a.getImages());
                        }

                        ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), R.layout.collage_tab, coll);
                        gridView.setAdapter(imagesAdapter);
                        swipeLayout.setRefreshing(false);
                    }
                }, 800); // Delay in millis
            }
        });
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        return rootView;
    }
    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity().getBaseContext(), FullScreenPhoto.class);
            intent.putExtra("SENDER_KEY", "MyFragment");
            intent.putExtra("img", position);
            intent.putExtra("list", coll);
            startActivity(intent);
        }
    };

}

