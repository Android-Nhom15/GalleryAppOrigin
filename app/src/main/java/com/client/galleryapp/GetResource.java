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

    public ArrayList<Album> getAllShownImagesPath() {

        try {
            ArrayList<Album> listOfAllAlbum = new ArrayList<>();

            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);


            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

                File f = new File(absolutePathOfImage);

                String parent = f.getParent().substring(f.getParent().lastIndexOf('/')+1);

                boolean checkExist = false;
                for (int i = 0; i < listOfAllAlbum.size(); i++) {
                    if (parent.compareTo(listOfAllAlbum.get(i).getName()) == 0) {
                        listOfAllAlbum.get(i).add(f);
                        checkExist = true;
                        break;
                    }
                }
                if (!checkExist) {
                    Album a = new Album();
                    a.setName(parent);
                    listOfAllAlbum.add(a);
                }
            }

            return listOfAllAlbum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
