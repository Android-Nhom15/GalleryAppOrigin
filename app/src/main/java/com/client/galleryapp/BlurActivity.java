package com.client.galleryapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.jgabrielfreitas.core.BlurImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class BlurActivity extends Activity {

    ImageView blurImageView;
    SeekBar blurSeekBar;
    private RenderScriptGaussianBlur blur;
    boolean isInit = false;
    Bitmap origin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blur_layout);

        blurImageView = findViewById(R.id.blurImageView);
        blurSeekBar = findViewById(R.id.blurSeekBar);

        blur = new RenderScriptGaussianBlur(BlurActivity.this);
        isInit = false;
        final File imageFile = (File)getIntent().getExtras().get("img");
        Glide.with(getApplicationContext()).load(Uri.fromFile(imageFile))
                .fitCenter()
                .placeholder(R.drawable.waitting_for_load)
                .into(blurImageView);

        blurSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radius = seekBar.getProgress();
                if (radius < 1) {
                    radius = 1;
                }

                Uri imageUri = Uri.fromFile(imageFile);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Bitmap bitmap = loadBitmap(Uri.fromFile(imageFile).toString());
                blurImageView.setImageBitmap(blur.gaussianBlur(radius, bitmap));
            }
        });

    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}
