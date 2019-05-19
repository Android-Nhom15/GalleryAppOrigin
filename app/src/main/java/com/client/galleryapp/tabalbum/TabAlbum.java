package com.client.galleryapp.tabalbum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.client.galleryapp.adapters.AlbumAdapter;
import com.client.galleryapp.resourcedata.GetResource;
import com.client.galleryapp.R;

import java.util.ArrayList;

public class TabAlbum extends Fragment {
    GridView gridView;
    SwipeRefreshLayout swipeLayout;

    ArrayList<Album> mAlbum = new ArrayList<>();
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.album_tab, container, false);
        gridView = (GridView) rootView.findViewById(R.id.album_grid_view);
        swipeLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        final GetResource getResource = new GetResource(getActivity());
        mAlbum = getResource.getAllShownImagesPath();
        final AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), R.layout.album_item, mAlbum);
        gridView.setAdapter(albumAdapter);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                // To keep animation for 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        final GetResource getResource = new GetResource(getActivity());
                        mAlbum = getResource.getAllShownImagesPath();
                        final AlbumAdapter albumAdapter = new AlbumAdapter(getActivity(), R.layout.album_item, mAlbum);
                        gridView.setAdapter(albumAdapter);
                        swipeLayout.setRefreshing(false);
                    }
                }, 2000); // Delay in millis
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

    public void onResume() {
        super.onResume();
        gridView.setOnItemClickListener(itemClickListener);
    }
    public AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity().getBaseContext(), ShowAnAlbum.class);
            intent.putExtra("img", position);
            startActivity(intent);
        }
    };
}
