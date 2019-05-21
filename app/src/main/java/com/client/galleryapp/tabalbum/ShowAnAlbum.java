package com.client.galleryapp.tabalbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.client.galleryapp.FullScreenPhoto;
import com.client.galleryapp.adapters.ListAlbumAdapter;
import com.client.galleryapp.adapters.RecyclerViewAdapter;
import com.client.galleryapp.resourcedata.GetResource;
import com.client.galleryapp.R;

import java.io.File;
import java.util.ArrayList;

public class ShowAnAlbum extends Activity {
    GridView gridView;
    TextView textView;
    int selectedPos;
    ArrayList<Album> mAlbum = new ArrayList<>();
    String _AlbumName;
    String _Number;
    ArrayList<File> mPhotoViewList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_an_album);
        textView = findViewById(R.id.AlbumName);
        gridView = findViewById(R.id.gridview_an_album);
        selectedPos = (Integer) getIntent().getExtras().getInt("img");
        GetResource getResource = new GetResource(this);
        mAlbum = getResource.getAllShownImagesPath();
        mPhotoViewList = mAlbum.get(selectedPos).getImages();
        _AlbumName = mAlbum.get(selectedPos).getName();
        _Number = " (" + mAlbum.get(selectedPos).getImages().size() +" Ảnh)";
        ImagesAnAlbumAdapter adapter = new ImagesAnAlbumAdapter(this, R.layout.gridview_item_of_an_album, mPhotoViewList);
        gridView.setAdapter(adapter);
        textView.setText(_AlbumName + _Number);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.dancing_script);
        textView.setTypeface(typeface);
        textView.setTextSize(20f);
        gridView.setOnItemClickListener(itemClickListener);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initAlbumRecyclerView();
    }

    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getBaseContext(), FullScreenPhoto.class);
            intent.putExtra("SENDER_KEY", "MyFragment");
            intent.putExtra("img", position);
            intent.putExtra("list", mPhotoViewList);
            startActivity(intent);
        }
    };

    public static class ImagesAnAlbumAdapter extends BaseAdapter {
        private Context context;
        private int layout;
        private ArrayList<File> photoViewList;

        public ImagesAnAlbumAdapter(Context context, int layout, ArrayList<File> photoViewList) {
            this.context = context;
            this.layout = layout;
            this.photoViewList = photoViewList;
        }

        @Override
        public int getCount() {
            return photoViewList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }




        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_item_of_an_album, null);
            ImageView iv_photo = (ImageView)convertView.findViewById(R.id.iv_photo_an_album);

            Glide.with(context).load(Uri.fromFile(photoViewList.get(position)))
                    .centerCrop()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(iv_photo);
            return convertView;
        }


    }

    private void initAlbumRecyclerView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView list_in_an_album = findViewById(R.id.list_in_an_album);
        list_in_an_album.setLayoutManager(layoutManager);

        ListAlbumAdapter adapter = new ListAlbumAdapter(this, mAlbum, R.layout.gridview_item_of_an_album);
        list_in_an_album.setAdapter(adapter);
    }

}
