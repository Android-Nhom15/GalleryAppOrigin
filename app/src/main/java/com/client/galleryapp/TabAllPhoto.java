package com.client.galleryapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

public class TabAllPhoto extends Fragment {
    GridView gridView;
    ArrayList<File> listFileImage = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.all_photo_tab, container, false);
        gridView = (GridView) rootView.findViewById(R.id.all_photo_gridview);
        GetResource getResource = new GetResource(getActivity());
        listFileImage = getResource.getAllShownImagesPath();
        ImagesAdapter imagesAdapter = new ImagesAdapter(getActivity(), R.layout.gridview_item_of_showall, listFileImage, R.id.iv_photo);
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
