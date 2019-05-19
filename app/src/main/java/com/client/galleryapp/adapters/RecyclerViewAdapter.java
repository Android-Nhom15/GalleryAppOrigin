package com.client.galleryapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.client.galleryapp.R;
import com.mukesh.image_processing.ImageProcessor;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();
    private Context mContext;
    private File mFile;

    ImageView photoEditorView;

    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<Integer> imageUrls, File file) {
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
        mFile = file;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_view, parent, false);
        photoEditorView = ((Activity)mContext).findViewById(R.id.photoEditorView);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.image.setImageResource(mImageUrls.get(position));
        holder.name.setText(mNames.get(position));

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int Original = 0;
                final int SnowEffect = 1;
                final int GrayScale = 2;
                final int Brightness = 3;
                final int Tint = 4;
                Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));

                ImageProcessor imageProcessor = new ImageProcessor();

                Bitmap bitmap;
                bitmap = ((BitmapDrawable) photoEditorView.getDrawable()).getBitmap();

                switch (position)
                {
                    case Original:

                        Glide.with(mContext).load(Uri.fromFile(mFile))
                                .fitCenter()
                                .placeholder(R.drawable.waitting_for_load)
                                .into(photoEditorView);
                        break;
                    case SnowEffect:
                        bitmap = imageProcessor.applySnowEffect(bitmap);
                        photoEditorView.setImageBitmap(bitmap);
                        break;

                    case GrayScale:
                        bitmap = imageProcessor.doGreyScale(bitmap);
                        photoEditorView.setImageBitmap(bitmap);
                        break;

                    case Brightness:
                        bitmap = imageProcessor.doBrightness(bitmap,30);
                        photoEditorView.setImageBitmap(bitmap);
                        break;

                    case Tint:
                        bitmap = imageProcessor.tintImage(bitmap,10);
                        photoEditorView.setImageBitmap(bitmap);
                        break;


                    default:
                        break;
                }

                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgFilterView);
            name = itemView.findViewById(R.id.txtFilterName);
        }
    }
}