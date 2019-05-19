package com.client.galleryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.bumptech.glide.Glide;
import com.client.galleryapp.filtercoler.PhotoEdit;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;

public class FullScreenPhoto extends Activity {
    int selectedPos;
    ViewPager viewPager;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        viewPager = findViewById(R.id.imageViewFullScreen);
        final String sender=this.getIntent().getExtras().getString("SENDER_KEY");
        selectedPos = getIntent().getExtras().getInt("img");
        edit =(Button) findViewById(R.id.editPhoto);
        final ArrayList<File> fileImages = (ArrayList<File>) getIntent().getExtras().get("list");
        if(sender != null)
        {
            this.receiveData(selectedPos, fileImages);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                Intent intent = new Intent(getBaseContext().getApplicationContext(), PhotoEdit.class);
                intent.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                int pos = viewPager.getCurrentItem();
                intent.putExtra("img", fileImages.get(viewPager.getCurrentItem()));
                startActivity(intent);
            }
        });
    }
    private void receiveData(int selectedPos, ArrayList<File> ListImage) {
        try {
            SliderAdapter adapter = new SliderAdapter(this, ListImage);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(selectedPos);
        } catch (NullPointerException e) {
            System.out.print("Caught the NullPointerException");
        }
    }

    public class SliderAdapter extends PagerAdapter {
        private Context mContext;
        private ArrayList<File> mPhotoViewList;
        private LayoutInflater inflater;

        SliderAdapter(Context context, ArrayList<File> PhotoViewList) {
            mContext = context;
            mPhotoViewList = PhotoViewList;
    }

        @Override
        public int getCount() {
            return mPhotoViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            inflater =(LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.photo_full,container,false);
            PhotoView photoView = view.findViewById(R.id.full_img);

            Glide.with(mContext).load(Uri.fromFile(mPhotoViewList.get(position)))
                    .fitCenter()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(photoView);
            container.addView(view,0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((PhotoView) object);
        }
    }
}
