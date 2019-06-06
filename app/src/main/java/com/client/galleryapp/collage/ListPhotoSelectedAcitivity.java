package com.client.galleryapp.collage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.client.galleryapp.FullScreenPhoto;
import com.client.galleryapp.adapters.ImageSelectedAdapter;
import com.client.galleryapp.R;
import com.client.galleryapp.filtercolor.PhotoEdit;
import com.client.galleryapp.resourcedata.Photo;
import com.jcmore2.collage.CollageView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListPhotoSelectedAcitivity extends AppCompatActivity {
    Button saveCollage;
    Button cancelCollage;
    CollageView collage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_photo_selected);
        collage = (CollageView) findViewById(R.id.collage);
        saveCollage = (Button) findViewById(R.id.saveCollage);
        cancelCollage = (Button) findViewById(R.id.cancelCollage);
        List<Drawable> listRes = new ArrayList<Drawable>();


        Intent intent=getIntent();
        ArrayList<Photo> picturesSelected=intent.getParcelableArrayListExtra("listpicture");
        Drawable d;
        for(Photo f : picturesSelected){
            d = Drawable.createFromPath(f.getPath());
            listRes.add(d);
        }
        collage.createCollageDrawables(listRes);
        collage.setFixedCollage(false);
        saveCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date currentTime = Calendar.getInstance().getTime();
                DateFormat df = new SimpleDateFormat("HH:mm:ss");
                String date = df.format(currentTime.getTime());


                String filename = "collage_" + date;
                Bitmap bitmap = viewToBitmap(collage);
                try {
                    File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +"GalaryApp"+ File.separator+ "Collage");
                    if (!sd.exists()) {
                        sd.mkdirs();
                    }
                    File f = new File(sd , filename+".png");
                    FileOutputStream fos = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                    fos.flush();
                    fos.close();
                    galleryAddPic(sd+ File.separator +filename+".png");
                    Toast.makeText(ListPhotoSelectedAcitivity.this,"Đã lưu",Toast.LENGTH_LONG).show();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });
        cancelCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void galleryAddPic(String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
    public Bitmap viewToBitmap(CollageView view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

