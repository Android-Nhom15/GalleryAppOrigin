package com.client.galleryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

public class TabCollage extends Fragment {
    GridView gridView;
    ArrayList<File> listFileImage = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.collage_tab, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridview_album_collage);
        GetResource getResource = new GetResource(getActivity());
        ArrayList<Album> albums = getResource.getAllShownImagesPath();
        for(Album element :albums){
            if(element.getName().equals("Download")) {
                for (File file : element.getImages()) {
                    listFileImage.add(file);
                }
            }
        }
        ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), R.layout.gridview_item_of_showall, listFileImage);
        gridView.setAdapter(imagesAdapter);
        return rootView;
    }
    public void onResume() {
        super.onResume();
        gridView.setOnItemClickListener(itemClickListener);
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity().getBaseContext(), FullScreenPhoto.class);
            intent.putExtra("SENDER_KEY", "MyFragment");
            intent.putExtra("img", position);
            intent.putExtra("list", listFileImage);
            startActivity(intent);
        }
    };
}
