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


public class ImageSelectedAdapter extends RecyclerView.Adapter<ImageSelectedAdapter.ImageItemViewHolder>{

    private Context context;
    private List<Photo> photos;

    public ImageSelectedAdapter(Context context, List<Photo> photos){
        this.context=context;
        this.photos=photos;
    }

    @NonNull
    @Override
    public ImageSelectedAdapter.ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.item_photo_selected,parent,false);
        return new ImageItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSelectedAdapter.ImageItemViewHolder holder, int position) {
         holder.bind(photos.get(position));
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ImageItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewSeleted;
        public ImageItemViewHolder(View itemView) {
            super(itemView);
            imageViewSeleted=itemView.findViewById(R.id.imageViewSelectedPhoto);
        }

        public void bind(Photo photo) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).override(200).placeholder(R.drawable.ic_launcher_background);
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_camera)
//                    .error(R.drawable.ic_send)
//                    .priority(Priority.HIGH);
            Glide.with(context)
                    .load(photo.getPath())
                    .apply(options)
                    .placeholder(R.drawable.waitting_for_load)
                    .into(imageViewSeleted);
        }
    }
}
