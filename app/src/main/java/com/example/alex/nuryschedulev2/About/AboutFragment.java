package com.example.alex.nuryschedulev2.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.alex.nuryschedulev2.R;

public class AboutFragment extends android.support.v4.app.Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String title = getResources().getStringArray(R.array.screen_array)[4];
        getActivity().setTitle(title);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, parent, false);
    }

}
