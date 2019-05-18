package com.client.galleryapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

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
                    listOfAllAlbum.get(listOfAllAlbum.size() - 1).add(f);
                }

//                cursor.moveToNext();
            }

            return listOfAllAlbum;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<JDate> getImageByDate(){
        try {
            ArrayList<JDate> listImageByDate = new ArrayList<>();

            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);


            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

                File f = new File(absolutePathOfImage);

                boolean exist = false;
                for (int i = 0; i < listImageByDate.size(); i++)
                {
                    if (listImageByDate.get(i).getDate().getMonth() == (new Date(f.lastModified())).getMonth()
                            && listImageByDate.get(i).getDate().getYear() == (new Date(f.lastModified())).getYear())
                    {
                        listImageByDate.get(i).getFileImage().add(f);
                        exist = true;
                        break;
                    }
                }
                if (!exist)
                {
                    JDate jdate = new JDate();
                    Date date = new Date(f.lastModified());
                    jdate.setDate(date);
                    jdate.getFileImage().add(f);
                    listImageByDate.add(jdate);
                }
            }

            for (int i = 0; i < listImageByDate.size() - 1; i++)
            {
                for (int j = i + 1; j <listImageByDate.size(); j++)
                {
                    if (listImageByDate.get(i).getDate().compareTo(listImageByDate.get(j).getDate()) > 0)
                    {
                        Collections.swap(listImageByDate,i,j);
                    }
                }
            }

            return listImageByDate;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
