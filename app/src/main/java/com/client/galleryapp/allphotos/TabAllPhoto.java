package com.client.galleryapp.allphotos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.ListView;

import com.client.galleryapp.resourcedata.GetResource;
import com.client.galleryapp.resourcedata.JDate;
import com.client.galleryapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TabAllPhoto extends Fragment {
    ListView gridView;
    SwipeRefreshLayout swipeLayout;
    ArrayList<JDate> timeline;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_show_by_date, container, false);
        gridView = (ListView) rootView.findViewById(R.id.gridViewAllMonth);
        swipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        final GetResource getResource = new GetResource(getActivity());
        timeline = getResource.getImageByDate();
        Collections.sort(timeline, new DateComparator());
        final AllPhotoAdapter timelineAdapter = new AllPhotoAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline);
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
                        final AllPhotoAdapter timelineAdapter = new AllPhotoAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline);
                        gridView.setAdapter(timelineAdapter);
                        swipeLayout.setRefreshing(false);
                    }
                }, 800); // Delay in millis
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
