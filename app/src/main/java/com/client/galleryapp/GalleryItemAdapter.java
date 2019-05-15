package com.client.galleryapp;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.GalleryItemViewHolder> {

    public interface ItemSelectedChangeListener{
        void onItemSelectedChange(int number);
    }

    private ItemSelectedChangeListener listener;

    private List<Photo> pictures;
    private Context context;

    private List<Photo> picturesSelected;

    int count = 0;

    public GalleryItemAdapter(Context context, List<Photo> pictures,ItemSelectedChangeListener listener) {
        this.context = context;
        this.pictures = pictures;
        this.listener=listener;
        this.picturesSelected = new ArrayList<>();
    }

    @NonNull
    @Override
    public GalleryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_gallery_photo, parent, false);
        return new GalleryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryItemViewHolder holder, int position) {
        holder.bind(pictures.get(position), position);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }


    public class GalleryItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPictureItem;
        TextView textViewSelectCount;
        ConstraintLayout constraintLayoutItemGallery;
        ImageView imageViewButton;

        public GalleryItemViewHolder(View itemView) {
            super(itemView);
            imageViewPictureItem = itemView.findViewById(R.id.imageViewPictureItem);
            textViewSelectCount = itemView.findViewById(R.id.item_gallery_selected_count);
            constraintLayoutItemGallery = itemView.findViewById(R.id.item_gallery);
        }

        public void bind(final Photo picture, final int position) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).override(200, 200).placeholder(R.drawable.ic_launcher_background);
            Glide.with(context)
                    .load(picture.getPath())
                    .apply(options)
                    .into(imageViewPictureItem);
            if (picture.getSelectedCount() > 0) {
                textViewSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_selected));
            } else {
                textViewSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_not_selected));
            }

            constraintLayoutItemGallery.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    int removeSelectCount;
                    picture.setPosition(position);
                    if (picture.getSelectedCount() > 0) {
                        count--;
                        textViewSelectCount.setText("");
                        textViewSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_not_selected));
                        //remove object from hashmap
                        //picturesSelectedMap.remove(picture);

                        picturesSelected.remove(picture);
                        //cập nhật lại vị trí
                        for (Photo pictureUpdate : pictures) {
                            if (pictureUpdate.getSelectedCount() > picture.getSelectedCount()) {
                                pictureUpdate.setSelectedCount(pictureUpdate.getSelectedCount() - 1);
                                notifyItemChanged(pictureUpdate.getPosition());
                            }
                        }
                        removeSelectCount=picture.getSelectedCount();
                        picture.setSelectedCount(0);

                    } else {
                        count++;
                        picture.setSelectedCount(count);
                        //add object to hashmap
                        picturesSelected.add(picture);
                        //picturesSelected.add(picture);
                        textViewSelectCount.setText(picture.getSelectedCount() + "");
                        textViewSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_selected));
                    }

                    listener.onItemSelectedChange(picturesSelected.size());
                }
            });
        }


    }

    //get all picture selected
    public ArrayList<Photo> getAllPictureSelected(){


        Collections.sort(picturesSelected, new Comparator<Photo>() {
            @Override
            public int compare(Photo o1, Photo o2) {
                return o1.getSelectedCount()>=o2.getSelectedCount()?1:-1;
            }
        });



        return (ArrayList<Photo>) picturesSelected;
    }
}