package com.client.galleryapp;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class PhotoEdit extends Activity {
    ImageView imageView;
    private static final String TAG = "MainActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_editor);
        imageView = (ImageView) findViewById(R.id.photoEditorView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final File file = (File)getIntent().getExtras().get("img");
        getImages();
        try{
            Glide.with(getApplicationContext()).load(Uri.fromFile(file))
                    .fitCenter()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(imageView);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void getImages(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add(R.drawable.filter);
        mNames.add("Havasu Falls");

        mImageUrls.add(R.drawable.filter);
        mNames.add("Trondheim");

        mImageUrls.add(R.drawable.filter);
        mNames.add("Portugal");

        mImageUrls.add(R.drawable.filter);
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add(R.drawable.filter);
        mNames.add("Mahahual");

        mImageUrls.add(R.drawable.filter);
        mNames.add("Frozen Lake");
        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
    }
}
