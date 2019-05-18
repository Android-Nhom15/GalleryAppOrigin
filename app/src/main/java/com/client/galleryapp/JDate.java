package com.client.galleryapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class JDate {
    private Date date;
    private ArrayList<File> fileImage;

    public JDate(Date date, ArrayList<File> fileImage) {
        this.date = date;
        this.fileImage = fileImage;
    }

    public JDate(JDate jdate){
        this.date = jdate.getDate();
        this.fileImage = jdate.getFileImage();
    }

    public JDate() {
        fileImage = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<File> getFileImage() {
        return fileImage;
    }

    public void setFileImage(ArrayList<File> fileImage) {
        this.fileImage = fileImage;
    }

}
