package com.client.galleryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

public class TabAlbum extends Fragment {
    GridView gridView;
    ArrayList<String> listAlbum = new ArrayList<>();
    ArrayList<File> imagesInAnAlbum = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_tab, container, false);
        gridView = (GridView) rootView.findViewById(R.id.album_grid_view);
        listAlbum.add("Camera");
        listAlbum.add("Camera");
        listAlbum.add("Camera");
        listAlbum.add("Camera");
        listAlbum.add("Camera");
        listAlbum.add("Camera");
        listAlbum.add("Camera");
        listAlbum.add("Camera");

        GetResource getResource = new GetResource(getActivity());
        imagesInAnAlbum = getResource.getAllShownImagesPath();

        AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), R.layout.album_item, listAlbum);
        gridView.setAdapter(albumAdapter);
        return rootView;
    }
    public void onResume() {
        super.onResume();
        gridView.setOnItemClickListener(itemClickListener);
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity().getBaseContext(), ShowAnAlbum.class);
            intent.putExtra("img", position);
            intent.putExtra("list", imagesInAnAlbum);
            startActivity(intent);
        }
    };
}