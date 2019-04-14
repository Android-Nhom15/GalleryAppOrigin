package com.client.galleryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<String> albumList;

    public AlbumAdapter(Context context, int layout, ArrayList<String> albumList) {
        this.context = context;
        this.layout = layout;
        this.albumList = albumList;
    }

    @Override
    public int getCount() {
        return albumList.size();
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
        TextView mAlbumName;
        TextView mNumberOfPhotos;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);//??
            viewHolder.mAlbumName = (TextView) convertView.findViewById(R.id.AlbumName);
            viewHolder.mNumberOfPhotos = (TextView) convertView.findViewById(R.id.NumberOfPhotos);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mAlbumName.setText(albumList.get(position));
        viewHolder.mNumberOfPhotos.setText("7");
        return convertView;
    }
}
