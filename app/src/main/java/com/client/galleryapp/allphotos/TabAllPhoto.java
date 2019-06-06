package com.client.galleryapp.allphotos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.client.galleryapp.adapters.OneMonthImageAdapter;
import com.client.galleryapp.imagecrop.Main_CropImage;
import com.client.galleryapp.resourcedata.GetResource;
import com.client.galleryapp.resourcedata.JDate;
import com.client.galleryapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TabAllPhoto extends Fragment {
    ListView gridView;
    int numColumn = 3;
    FloatingActionButton fab_plus;
    FloatingActionButton fab_minus;
    SwipeRefreshLayout swipeLayout;
    AllPhotoAdapter timelineAdapter;
    ArrayList<JDate> timeline;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_show_by_date, container, false);
        gridView = (ListView) rootView.findViewById(R.id.gridViewAllMonth);
        fab_plus = (FloatingActionButton) rootView.findViewById(R.id.fab_plus);
        fab_minus = (FloatingActionButton) rootView.findViewById(R.id.fab_minus);
        swipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        final GetResource getResource = new GetResource(getActivity());
        timeline = getResource.getImageByDate();
        Collections.sort(timeline, new DateComparator());
        timelineAdapter = new AllPhotoAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline,3);
        gridView.setAdapter(timelineAdapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        timeline = getResource.getImageByDate();
                        Collections.sort(timeline, new DateComparator());
                        final AllPhotoAdapter timelineAdapter = new AllPhotoAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline, numColumn);
                        gridView.setAdapter(timelineAdapter);
                        swipeLayout.setRefreshing(false);
                    }
                }, 800); // Delay in millis
            }
        });

        fab_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                if(numColumn<4) {
                    numColumn+=1;
                    timelineAdapter = new AllPhotoAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline, numColumn);
                    gridView.setAdapter(timelineAdapter);
                }

            }
        });
        fab_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                if(numColumn>2) {
                    numColumn-=1;
                    timelineAdapter = new AllPhotoAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline, numColumn);
                    gridView.setAdapter(timelineAdapter);
                }
            }
        });
        // Scheme colors for animation
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
        return rootView;
    }
    public class DateComparator implements Comparator<JDate>
    {
        public int compare(JDate left, JDate right) {
            return -left.getDate().compareTo(right.getDate());
        }
    }
}
