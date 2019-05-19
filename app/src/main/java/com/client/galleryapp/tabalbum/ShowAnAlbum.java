package com.client.galleryapp.tabalbum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
        ImagesAnAlbumAdapter adapter = new ImagesAnAlbumAdapter(this, R.layout.gridview_item_of_an_album, mPhotoViewList);
        gridView.setAdapter(adapter);
        textView.setText(_AlbumName);
        gridView.setOnItemClickListener(itemClickListener);
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
    public class ImagesAnAlbumAdapter extends BaseAdapter {
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

        private class ViewHolder {
            ImageView photo;
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

}
