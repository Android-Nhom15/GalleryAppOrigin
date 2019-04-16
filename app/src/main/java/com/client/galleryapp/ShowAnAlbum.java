package com.client.galleryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class ShowAnAlbum extends AppCompatActivity {
    GridView gridView;
    int selectedPos;
    ArrayList<Album> mAlbum = new ArrayList<>();
    ArrayList<File> mPhotoViewList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_an_album);
        gridView = findViewById(R.id.gridview_an_album);

        selectedPos = getIntent().getExtras().getInt("img");
        mAlbum = (ArrayList<Album>) getIntent().getExtras().get("list");
        mPhotoViewList = mAlbum.get(selectedPos).getImages();
        ImagesAdapter adapter = new ImagesAdapter(this, R.layout.gridview_item_of_an_album, mPhotoViewList , R.id.iv_photo_an_album);
        gridView.setAdapter(adapter);
    }
    public void onResume() {
        super.onResume();
        gridView.setOnItemClickListener(itemClickListener);
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getBaseContext(), FullScreenPhoto.class);
            intent.putExtra("SENDER_KEY", "MyFragment");
            intent.putExtra("img", position);
            intent.putExtra("list", mPhotoViewList);
            startActivity(intent);
        }
    };
}
