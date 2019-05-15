package com.client.galleryapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class TabTimeline extends Fragment {
    ListView gridView;
    ArrayList<JDate> timeline;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_show_by_date, container, false);
        gridView = (ListView) rootView.findViewById(R.id.gridViewAllMonth);
        GetResource getResource = new GetResource(getActivity());
        timeline = getResource.getImageByDate();
        TimelineAdapter timelineAdapter = new TimelineAdapter(getActivity().getApplicationContext(), R.layout.item_showbydate, timeline);
        gridView.setAdapter(timelineAdapter);
        return rootView;
    }
}
