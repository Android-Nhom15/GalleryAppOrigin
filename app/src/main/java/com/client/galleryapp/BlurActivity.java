package com.client.galleryapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.client.galleryapp.filtercolor.PhotoEdit;
import com.jgabrielfreitas.core.BlurImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BlurActivity extends AppCompatActivity{

    ImageView blurImageView;
    SeekBar blurSeekBar;
    MenuItem btnSave;
    private RenderScriptGaussianBlur blur;
    boolean isInit = false;
    Bitmap origin;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        btnSave = menu.findItem(R.id.btnSave);
        btnSave.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSave:

                final File file = (File)getIntent().getExtras().get("img");

                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String date = df.format(currentTime.getTime());

                String filename = file.getName().substring(0,file.getName().lastIndexOf(".")) + "_" + date;

                Bitmap bitmap = ((BitmapDrawable) blurImageView.getDrawable()).getBitmap();
                try {
                    File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"GalaryApp"+ File.separator+ "PhotoFilter");
                    if (!sd.exists()) {
                        sd.mkdirs();
                    }
                    File f = new File(sd , filename+".png");
                    FileOutputStream fos = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                    fos.flush();
                    fos.close();
                    galleryAddPic(sd+ File.separator +filename+".png");
                    Toast.makeText(BlurActivity.this,"Đã lưu",Toast.LENGTH_LONG).show();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

                return true;
            case R.id.btnCancel:
                blurSeekBar.setProgress(0);

                return true;
        }
        return false;
    }

    private void galleryAddPic(String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar1, menu);
        return true;
    }

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
                if (progress == 0){
                    Uri imageUri = Uri.fromFile(imageFile);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    blurImageView.setImageBitmap(blur.gaussianBlur(0, bitmap));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int radius = seekBar.getProgress();
                if (radius == 0) {
                    //radius = 1;
                    btnSave.setVisible(false);

                }
                else{
                    btnSave.setVisible(true);
                }

                Uri imageUri = Uri.fromFile(imageFile);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                blurImageView.setImageBitmap(blur.gaussianBlur(radius, bitmap));
            }
        });

    }


}
