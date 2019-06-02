package com.client.galleryapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.client.galleryapp.filtercolor.PhotoEdit;

import com.bumptech.glide.Glide;
import com.client.galleryapp.imagecrop.Main_CropImage;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FullScreenPhoto extends Activity {
    int selectedPos;
    SliderAdapter adapter;
    ViewPager viewPager;
    ArrayList<File> fileImages;
    Button edit;
    Button del;
    Button crop;
    Button detail;
    FrameLayout frameLayout;
    FrameLayout frameLayout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        frameLayout = (FrameLayout) findViewById(R.id.full_screen);
        frameLayout1 = (FrameLayout) findViewById(R.id.TabOption);
        viewPager = findViewById(R.id.imageViewFullScreen);
        final String sender=this.getIntent().getExtras().getString("SENDER_KEY");
        selectedPos = getIntent().getExtras().getInt("img");
        edit =(Button) findViewById(R.id.editPhoto);
        del = (Button) findViewById(R.id.deletePhoto);
        crop = (Button) findViewById(R.id.cropPhoto);
        detail = (Button) findViewById(R.id.detailPhoto);
        fileImages = (ArrayList<File>) getIntent().getExtras().get("list");
        Typeface typeface = ResourcesCompat.getFont(getBaseContext().getApplicationContext(), R.font.dancing_script);
        edit.setTypeface(typeface);
        del.setTypeface(typeface);
        detail.setTypeface(typeface);
        crop.setTypeface(typeface);

        if(sender != null)
        {
            this.receiveData(selectedPos, fileImages);
        }
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v) {
                    Intent intent = new Intent(getBaseContext().getApplicationContext(), BlurActivity.class);
                    intent.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                    int pos = viewPager.getCurrentItem();
                    intent.putExtra("img", fileImages.get(viewPager.getCurrentItem()));

                    startActivity(intent);
                }
            });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                AlertDialogDeleteImage(selectedPos);
            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                Toast.makeText(getBaseContext().getApplicationContext(),"day roi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext().getApplicationContext(), Main_CropImage.class);
                intent.setFlags(Intent. FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("imgcrop", fileImages.get(viewPager.getCurrentItem()));
                startActivity(intent);

            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                int pos = viewPager.getCurrentItem();
                //showExif(fileImages.get(pos).toURI());
                ExifInterface exifInterface = null;
                try {
                    exifInterface = new ExifInterface(fileImages.get(pos).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String exif="Chi Tiết : " ;

                exif+= "\n\nTên: "+fileImages.get(pos).getName() ;


                exif+= "\n\nĐường dẫn: \n"+fileImages.get(pos).getPath();

                exif+= "\n\nDung Lượng: "+fileImages.get(pos).length()+" KB";

                Date lastModDate = new Date(fileImages.get(pos).lastModified());


                Calendar calendar = Calendar.getInstance();
                calendar.setTime(lastModDate);
                int year = calendar.get(Calendar.YEAR);
                //Add one to month {0 - 11}
                int month = calendar.get(Calendar.MONTH);
                month+=1;
                //String[] months = new DateFormatSymbols().getMonths();

                int day = calendar.get(Calendar.DATE);

                String mDateMonth = day+"/" +month+ "/" + year;

                exif+= "\n\nNgày: "+ mDateMonth;


                if(Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH))  == 0 || Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH))==0)
                {
                    exif += "\n\nKích Thước: Chưa rõ";
                }
                else
                {
                    exif += "\n\nKích Thước: " +
                            exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH) +"x" +exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
                }

                Toast.makeText(getApplicationContext(),
                        exif,
                        Toast.LENGTH_LONG).show();
            }
        });
    }



    private void receiveData(int selectedPos, ArrayList<File> ListImage) {
        try {
            adapter = new SliderAdapter(this, ListImage);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(selectedPos);
        } catch (NullPointerException e) {
            System.out.print("Caught the NullPointerException");
        }
    }

    private void AlertDialogDeleteImage (int position)
    {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(FullScreenPhoto.this);
        myBuilder
                .setTitle("Cảnh báo!")
                .setMessage("Bạn Có Chắc Chắn Xóa Ảnh?")
                .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichOne) {
                        int pos = viewPager.getCurrentItem();
                        deleteImage( fileImages.get(pos).toString());
                        fileImages.remove(pos);
                        adapter.notifyDataSetChanged();
                        adapter = new SliderAdapter(getBaseContext().getApplicationContext(), fileImages);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(selectedPos);
                    }
                })
                .setPositiveButton("Không", null)
                .show();
    }

    public void deleteImage(String file_dj_path) {
        //String file_dj_path = Environment.getExternalStorageDirectory() + "/ECP_Screenshots/abc.jpg";
        File fdelete = new File(file_dj_path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Toast.makeText(this,"Đã xóa ảnh", Toast.LENGTH_LONG).show();
                callBroadCast();
                galleryAddPic(file_dj_path);            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
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
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick( View v) {
                    if (frameLayout1.getVisibility() == View.INVISIBLE)
                        frameLayout1.setVisibility(View.VISIBLE);
                    else
                        frameLayout1.setVisibility(View.INVISIBLE);
                }
            });



            return view;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((PhotoView) object);
        }
    }
}