package com.client.galleryapp.allphotos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.client.galleryapp.imagecrop.Main_CropImage;
import com.client.galleryapp.resourcedata.JDate;
import com.client.galleryapp.adapters.OneMonthImageAdapter;
import com.client.galleryapp.R;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AllPhotoAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<JDate> timeline;
    int column;
    public AllPhotoAdapter(Context context, int layout, ArrayList<JDate> timeline, int col) {
        this.context = context;
        this.layout = layout;
        this.timeline = timeline;
        this.column = col;
    }

    @Override
    public int getCount() {
        return timeline.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    ViewHolder viewHolder = new ViewHolder();
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.txtDateMonth = (TextView) convertView.findViewById(R.id.txtDateMonth);
            viewHolder.gridViewOneMonth = (ExpandableHeightGridView) convertView.findViewById(R.id.gridViewOneMonth);
            viewHolder.gridViewOneMonth.setExpanded(true);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (AllPhotoAdapter.ViewHolder) convertView.getTag();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeline.get(position).getDate());
        int year = calendar.get(Calendar.YEAR);
        //Add one to month {0 - 11}
        int month = calendar.get(Calendar.MONTH);
        String[] months = new DateFormatSymbols().getMonths();
        String mDateMonth = months[month] + " - " + year;

        viewHolder.txtDateMonth.setText(mDateMonth);
        viewHolder.txtDateMonth.setTextSize(20f);
        Typeface typeface = ResourcesCompat.getFont(context, R.font.dancing_script);
        viewHolder.txtDateMonth.setTypeface(typeface);
        // Load ảnh vào gridview

        final OneMonthImageAdapter imagesAdapter = new OneMonthImageAdapter(context, timeline.get(position).getFileImage());
        viewHolder.gridViewOneMonth.setNumColumns(column);
        viewHolder.gridViewOneMonth.setAdapter(imagesAdapter);
        return convertView;
    }

    private class ViewHolder {
        TextView txtDateMonth;
        ExpandableHeightGridView gridViewOneMonth;
    }

}
