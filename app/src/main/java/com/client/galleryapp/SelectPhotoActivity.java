package com.client.galleryapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.client.galleryapp.collage.GalleryItemAdapter;
import com.client.galleryapp.collage.ListPhotoSelectedAcitivity;
import com.client.galleryapp.resourcedata.Photo;
import com.client.galleryapp.utils.ConstantDataManager;
import com.client.galleryapp.utils.Libraries;

import java.io.File;
import java.util.ArrayList;

public class SelectPhotoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGallery;
    private ArrayList<Photo> pictures;
    GalleryItemAdapter adapter;
    Handler handler;

    private ImageView imageViewButtonSend;
    private TextView textViewSelectedCount;
    private ConstraintLayout constraintLayoutSend;
    private FloatingActionButton buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageViewButtonSend = findViewById(R.id.button_send);
        textViewSelectedCount = findViewById(R.id.textViewSeletedCount);
        constraintLayoutSend = findViewById(R.id.layoutSend);
        buttonDelete = findViewById(R.id.fab_minus);
        buttonDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialogDeleteImage();
            }
        });
        imageViewButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetail = new Intent(SelectPhotoActivity.this, ListPhotoSelectedAcitivity.class);
                ArrayList<Photo> picturesSelected = adapter.getAllPictureSelected();
                intentDetail.putParcelableArrayListExtra("listpicture", picturesSelected);
                startActivity(intentDetail);
            }
        });

        pictures = new ArrayList<>();

        recyclerViewGallery = findViewById(R.id.recyclerViewGallery);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new GalleryItemAdapter(this, pictures, new GalleryItemAdapter.ItemSelectedChangeListener() {
            @Override
            public void onItemSelectedChange(int number) {
                if (number > 0) {
                    if(number == 4){
                        imageViewButtonSend.setVisibility(View.VISIBLE);
                    } else {
                        imageViewButtonSend.setVisibility(View.INVISIBLE);
                    }
                    constraintLayoutSend.setVisibility(View.VISIBLE);
                    textViewSelectedCount.setText(number + " /4" );
                } else {
                    constraintLayoutSend.setVisibility(View.GONE);
                }
            }
        });
        recyclerViewGallery.setAdapter(adapter);

        int sidePadding = getResources().getDimensionPixelOffset(R.dimen.sidePadding);
        int topPadding = getResources().getDimensionPixelOffset(R.dimen.topPadding);
        recyclerViewGallery.addItemDecoration(new RecyclerDecoration(sidePadding, topPadding));

        handler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(SelectPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Libraries.requestPermissionStorage(SelectPhotoActivity.this);
        } else {
            new Thread() {

                @Override
                public void run() {
                    Looper.prepare();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            pictures.clear();
                            pictures.addAll(Photo.getGalleryPhotos(SelectPhotoActivity.this));
                            adapter.notifyDataSetChanged();
                        }
                    });
                    Looper.loop();
                }


            }.start();
        }
    }
    private void AlertDialogDeleteImage ()
    {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(SelectPhotoActivity.this);
        myBuilder
                .setTitle("Cảnh báo!")
                .setMessage("Bạn Có Chắc Chắn Xóa Ảnh?")
                .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichOne) {
                        ArrayList<Photo> picturesSelected = adapter.getAllPictureSelected();
                        for(int i = 0 ; i < picturesSelected.size(); i++ ) {
                            Log.e("sadsad",Integer.toString(i));
                            File FileDeletePath = new File(picturesSelected.get(i).getPath());
                            File FilePhoto = new File(picturesSelected.get(i).toString());
                            deleteImage(picturesSelected.get(i).getPath());
                        }
                    }
                })
                .setPositiveButton("Không", null)
                .show();
    }
    public void deleteImage(String file_dj_path) {
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_dj_path);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(fdelete)));
                finish();
                startActivity(getIntent());
            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
            }
        } else {
            Log.e("-->", "file does not exists :" + file_dj_path);
        }
    }

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new Thread() {

                    @Override
                    public void run() {
                        Looper.prepare();
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                pictures.clear();
                                pictures.addAll(Photo.getGalleryPhotos(SelectPhotoActivity.this));
                                adapter.notifyDataSetChanged();
                            }
                        });
                        Looper.loop();
                    }
                }.start();

            } else {
                //deny
                Libraries.requestPermissionStorageDeny(SelectPhotoActivity.this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
