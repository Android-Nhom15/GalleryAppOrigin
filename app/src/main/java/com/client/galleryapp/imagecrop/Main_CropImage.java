package com.client.galleryapp.imagecrop;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.client.galleryapp.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main_CropImage extends Activity {
    ImageView imageView;
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_crop_image);
        imageView = (ImageView) findViewById(R.id.imgcrop);
        try{
            file = (File)getIntent().getExtras().get("imgcrop");
            Glide.with(this).load(Uri.fromFile(file))
                    .fitCenter()
                    .placeholder(R.drawable.waitting_for_load)
                    .into(imageView);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                .setType("image/*"), 1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK) {
            Uri uri = Uri.fromFile(file);
            if(uri!=null)
                startCrop(uri);
        }else if(requestCode== UCrop.REQUEST_CROP && resultCode==RESULT_OK) {
            Uri uriResult = UCrop.getOutput(data);
            if (uriResult != null) {
                imageView.setImageURI(uriResult);
                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String date = df.format(currentTime.getTime());
                String filename = file.getName().substring(0, file.getName().lastIndexOf(".")) + "_" + date;

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                try {
                    File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "GalaryApp" +File.separator+"PhotoCrop");
                    if (!sd.exists()) {
                        sd.mkdirs();
                    }
                    File f = new File(sd, filename + ".png");
                    FileOutputStream fos = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    galleryAddPic(sd + File.separator + filename + ".png");
                    Toast.makeText(Main_CropImage.this, "Đã lưu", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void galleryAddPic(String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    private void startCrop(@NonNull Uri uri){
        String desFileName = "test.jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir() , desFileName)));
        uCrop.withAspectRatio(1,1);
        uCrop.withMaxResultSize(450,450);
        uCrop.withOptions(getCropOption());
        uCrop.start(Main_CropImage.this);
    }

    private UCrop.Options getCropOption() {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(70);
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarTitle("GalleryApp");
        return options;
    }
}
