package com.client.galleryapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.client.galleryapp.R;
import com.client.galleryapp.tabalbum.Album;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Album> albumList;

    public AlbumAdapter(Context context, int layout, ArrayList<Album> albumList) {
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
        CircleImageView mIcon;
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
            viewHolder.mIcon = (CircleImageView) convertView.findViewById(R.id.icon);
            viewHolder.mNumberOfPhotos = (TextView) convertView.findViewById(R.id.NumberOfPhotos);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            Glide.with(context).load(Uri.fromFile(albumList.get(position).getImages().get(0)))
                    .centerCrop()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(viewHolder.mIcon);
        viewHolder.mAlbumName.setText(albumList.get(position).getName());
        Typeface typeface = ResourcesCompat.getFont(context, R.font.dancing_script);
        viewHolder.mAlbumName.setTypeface(typeface);
        viewHolder.mAlbumName.setTextSize(20f);
        String k = String.valueOf(albumList.get(position).getImages().size());
        viewHolder.mNumberOfPhotos.setText(k);
        viewHolder.mNumberOfPhotos.setTypeface(typeface);
        return convertView;
    }
}
