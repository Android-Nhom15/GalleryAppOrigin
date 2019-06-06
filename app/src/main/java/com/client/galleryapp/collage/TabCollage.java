package com.client.galleryapp.collage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.client.galleryapp.resourcedata.GetResource;
import com.client.galleryapp.tabalbum.Album;

import java.io.File;
import java.util.ArrayList;

public class TabCollage extends Fragment {
    FloatingActionButton fab_collage;
    GridView gridView;
    ArrayList<File> coll = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collage_tab, container, false);
        fab_collage = rootView.findViewById(R.id.fab_collage);
        gridView = rootView.findViewById(R.id.gridview_album_collage);
        ArrayList<Album> mAlbum = new ArrayList<>();
        final GetResource getResource = new GetResource(getActivity());
        mAlbum = getResource.getAllShownImagesPath();
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

