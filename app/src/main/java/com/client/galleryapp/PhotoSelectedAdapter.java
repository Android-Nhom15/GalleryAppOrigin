package com.client.galleryapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PhotoSelectedAdapter extends RecyclerView.Adapter<PhotoSelectedAdapter.PhotoViewHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotoSelectedAdapter(Context context, List<Photo> photos){
        this.context = context;
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotoSelectedAdapter.PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_photo_selected, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoSelectedAdapter.PhotoViewHolder holder, int position) {
        holder.bind(photos.get(position));
    }

    @Override
    public int getItemCount() { return photos.size();}

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        ImageView photoViewSelected;
        public  PhotoViewHolder(View itemView) {
            super(itemView);
            photoViewSelected = itemView.findViewById(R.id.imageViewSelectedPhoto);
        }

        public void bind(Photo photo) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).override(200);
            Glide.with(context)
                    .load(photo.getPath())
                    .apply(options)
                    .into(photoViewSelected);
        }
    }
}
