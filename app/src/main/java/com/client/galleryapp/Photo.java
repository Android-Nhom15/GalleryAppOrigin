package com.client.galleryapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;

public class Photo implements Parcelable {
    private String path;
    private int selectedCount;
    private int position;

    public Photo() {
    }

    protected Photo(Parcel source) {
        this.path = source.readString();
        selectedCount = source.readInt();
        position = source.readInt();
    }

    public  static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }
    public boolean equals(Object obj) {
        return this.getSelectedCount()== ((Photo)obj).getSelectedCount();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static ArrayList<Photo> getGalleryPhotos(Context context) {
        ArrayList<Photo> photos = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media._ID;

        Cursor cursorPhotos = context.getContentResolver().query(uri, columns, null, null, orderBy);
        if (cursorPhotos != null && cursorPhotos.getCount() > 0) {
            while (cursorPhotos.moveToNext()) {
                Photo photo=new Photo();

                int indexPath=cursorPhotos.getColumnIndex(MediaStore.MediaColumns.DATA);
                photo.setPath(cursorPhotos.getString(indexPath));
                photos.add(photo);
            }
        }
        Collections.reverse(photos);
        return photos;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(selectedCount);
        dest.writeInt(position);
    }


}

