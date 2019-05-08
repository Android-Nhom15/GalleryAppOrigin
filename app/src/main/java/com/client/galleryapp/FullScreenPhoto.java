package com.client.galleryapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class FullScreenPhoto extends Activity {
    int selectedPos;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        viewPager = findViewById(R.id.imageViewFullScreen);
        final String sender=this.getIntent().getExtras().getString("SENDER_KEY");
        if(sender != null)
        {
            this.receiveData();
        }
    }

    private void receiveData() {
        selectedPos = getIntent().getExtras().getInt("img");
        try {
            SliderAdapter adapter = new SliderAdapter(this);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(selectedPos);
        } catch (NullPointerException e) {
            System.out.print("Caught the NullPointerException");
        }
    }

    public class SliderAdapter extends PagerAdapter {
        private Context mContext;
        private ArrayList<File> mPhotoViewList;

        SliderAdapter(Context context) {
            mContext = context;
            mPhotoViewList = (ArrayList<File>) getIntent().getExtras().get("list");
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
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(mContext).load(Uri.fromFile(mPhotoViewList.get(position)))
                    .fitCenter()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(imageView);
            container.addView(imageView, 0);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}
