package com.cbt.weixin6.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.weixin6.R;

/**
 * Created by caobotao on 16/1/2.
 */
public class SecondFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_tab, container, false);
        return view;
    }
}
