package com.client.galleryapp;

import java.io.File;
import java.util.ArrayList;

public class  Album{
    private String name;
    private ArrayList<File> images;

    public Album() {
        images = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<File> getImages() {
        return images;
    }

    public void setImages(ArrayList<File> images) {
        this.images = images;
    }

    public void add(File f)
    {
        this.images.add(f);
    }
}