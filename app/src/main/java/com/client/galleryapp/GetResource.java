package com.client.galleryapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

public class GetResource {
    private Context context;
    public GetResource(Context context){
        this.context = context;
    }

    public ArrayList<File> getAllShownImagesPath() {

        try {
            ArrayList<File> listOfAllImages = new ArrayList<>();

            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);


            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                File tmp = new File(absolutePathOfImage);
                listOfAllImages.add(tmp);
            }

            return listOfAllImages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
