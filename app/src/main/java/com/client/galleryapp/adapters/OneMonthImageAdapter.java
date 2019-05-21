package com.client.galleryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.client.galleryapp.FullScreenPhoto;
import com.client.galleryapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class OneMonthImageAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<File> fileImages;

    public OneMonthImageAdapter(Context context, ArrayList<File> fileImages) {
        this.context = context;
        this.fileImages = fileImages;
    }

    @Override
    public int getCount() {
        return fileImages.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_2_showbydate, null);
        ImageView iv_photo = (ImageView) convertView.findViewById(R.id.imageViewByDate);
        Glide.with(context).load(Uri.fromFile(fileImages.get(position)))
                .centerCrop()
                .placeholder(R.drawable.waitting_for_load)
                .into(iv_photo);

        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FullScreenPhoto.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("SENDER_KEY", "MyFragment");
                intent.putExtra("img", position);
                intent.putExtra("list", fileImages);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
