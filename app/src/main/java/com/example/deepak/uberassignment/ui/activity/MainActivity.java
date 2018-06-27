package com.example.deepak.uberassignment.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.deepak.uberassignment.R;
import com.example.deepak.uberassignment.ui.fragment.ImageListFragment;

public class MainActivity extends AppCompatActivity {

    private ImageListFragment mImageListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
// get fragment manager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        mImageListFragment = (ImageListFragment) fm.findFragmentByTag(ImageListFragment.TAG);
        if (mImageListFragment == null) {
            mImageListFragment = ImageListFragment.newInstance();
            ft.add(R.id.fragment_container, mImageListFragment, ImageListFragment.TAG).commit();
        }


    }
}
