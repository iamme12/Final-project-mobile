package org.daou.personaldashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView functionRecycler = (RecyclerView) inflater.
                inflate(R.layout.fragment_dashboard, container, false);

        String[] functionNames = new String[Images.sushi.length];
        for (int i = 0; i < functionNames.length; i++) {
            functionNames[i] = Images.sushi[i].getName();
        }
        int[] functionImages = new int[Images.sushi.length];
        for (int i = 0; i < Images.sushi.length; i++) {
            functionImages[i] = Images.sushi[i].getImageResourceId()
            ;
        }

        CaptionedImagesAdapter captionedImagesAdapter = new
                CaptionedImagesAdapter(functionNames, functionImages);
        functionRecycler.setAdapter(captionedImagesAdapter);

        LinearLayoutManager layoutManager = new
                LinearLayoutManager(getActivity());
        functionRecycler.setLayoutManager(layoutManager);

        return functionRecycler;
    }
}
