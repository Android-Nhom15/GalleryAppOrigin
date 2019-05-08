package com.client.galleryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ImagesAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<File> photoViewList;
    Bitmap bitmap;

    public ImagesAdapter(Context context, int layout, ArrayList<File> photoViewList) {
        this.context = context;
        this.layout = layout;
        this.photoViewList = photoViewList;
    }

    public ImagesAdapter(Context context, ArrayList<File> photoViewList) {
        this.context = context;
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
//        ViewHolder viewHolder;
//        if (convertView == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(layout, null);
//            viewHolder.photo = (ImageView) convertView.findViewById(R.id.iv_photo);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//        BitmapFactory.Options options = new BitmapFactory.Options();
//
//        options.inSampleSize = 8;
//        bitmap = BitmapFactory.decodeFile(photoViewList.get(position).toString(),options);
//
//        viewHolder.photo.setImageBitmap(bitmap);
//        return convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.gridview_item_of_showall, null);
        ImageView iv_photo = (ImageView)convertView.findViewById(R.id.iv_photo);

        Glide.with(context).load(Uri.fromFile(photoViewList.get(position)))
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(iv_photo);


        return convertView;
    }

}
