package com.client.galleryapp.collage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.client.galleryapp.FullScreenPhoto;
import com.client.galleryapp.adapters.ImageSelectedAdapter;
import com.client.galleryapp.R;
import com.client.galleryapp.resourcedata.Photo;

import java.util.ArrayList;

public class ListPhotoSelectedAcitivity extends AppCompatActivity {
    private RecyclerView recyclerViewImageSelected;
    ImageSelectedAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo_selected);
        recyclerViewImageSelected=findViewById(R.id.recyclerViewSelected);

        Intent intent=getIntent();
        ArrayList<Photo> picturesSelected=intent.getParcelableArrayListExtra("listpicture");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewImageSelected.setLayoutManager(layoutManager);

        adapter=new ImageSelectedAdapter(this,picturesSelected);
        recyclerViewImageSelected.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(adapter!=null){
            recyclerViewImageSelected=null;
            adapter=null;
            Runtime.getRuntime().gc();
        }
    }
}

