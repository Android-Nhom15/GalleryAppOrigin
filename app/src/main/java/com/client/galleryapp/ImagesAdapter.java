package com.client.galleryapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.photo = (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bitmap = BitmapFactory.decodeFile(photoViewList.get(position).toString());
        viewHolder.photo.setImageBitmap(bitmap);
        return convertView;
    }
}
