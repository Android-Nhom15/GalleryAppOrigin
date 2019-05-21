package com.client.galleryapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.client.galleryapp.FullScreenPhoto;
import com.client.galleryapp.R;
import com.client.galleryapp.tabalbum.Album;
import com.client.galleryapp.tabalbum.ShowAnAlbum;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.ViewHolder> {


    private Context mContext;
    private ArrayList<Album> mAlbums;
    private int mlayout;

    TextView textView;
    GridView gridView;


    public ListAlbumAdapter(Context mContext, ArrayList<Album> mAlbums, int mlayout) {
        this.mContext = mContext;
        this.mAlbums = mAlbums;
        this.mlayout = mlayout;
    }

    @NonNull
    @Override
    public ListAlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.album_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAlbumAdapter.ViewHolder viewHolder, final int i) {
        Glide.with(mContext).load(Uri.fromFile(mAlbums.get(i).getImages().get(0)))
                .fitCenter()
                .placeholder(R.drawable.waitting_for_load)
                .into(viewHolder.mIcon);

        viewHolder.mAlbumName.setText(mAlbums.get(i).getName());
        viewHolder.mAlbumName.setTextSize(18f);
        Typeface typeface = ResourcesCompat.getFont(mContext, R.font.dancing_script);
        viewHolder.mAlbumName.setTypeface(typeface);
        viewHolder.mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView = ((Activity)mContext).findViewById(R.id.AlbumName);
                gridView = ((Activity)mContext).findViewById(R.id.gridview_an_album);

                ShowAnAlbum.ImagesAnAlbumAdapter adapter = new ShowAnAlbum.ImagesAnAlbumAdapter(mContext, R.layout.gridview_item_of_an_album, mAlbums.get(i).getImages());
                gridView.setAdapter(adapter);
                String _Number = " (" + mAlbums.get(i).getImages().size() +" Ảnh)";
                textView.setTextSize(20f);
                textView.setText(mAlbums.get(i).getName() + _Number);
                Typeface typeface = ResourcesCompat.getFont(mContext, R.font.dancing_script);
                textView.setTypeface(typeface);

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(mContext, FullScreenPhoto.class);
                        intent.putExtra("SENDER_KEY", "MyFragment");
                        intent.putExtra("img", position);
                        intent.putExtra("list", mAlbums.get(i).getImages());
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mIcon;
        TextView mAlbumName;
        TextView mNumberOfPhotos;

        public ViewHolder(View view) {
            super(view);
            mIcon = itemView.findViewById(R.id.icon);
            mAlbumName = itemView.findViewById(R.id.AlbumName);
            mNumberOfPhotos = itemView.findViewById(R.id.NumberOfPhotos);
        }
    }
}
