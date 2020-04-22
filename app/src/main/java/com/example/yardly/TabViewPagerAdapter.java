package com.example.yardly;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private Bundle myBundle;

    public TabViewPagerAdapter( FragmentManager fm, Bundle b) {
        super(fm);
        myBundle = b;

    }
    public TabViewPagerAdapter( FragmentManager fm) {
        super(fm);
        myBundle = null;

    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get( position );
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void  addFragment( Fragment frag, String title)
    {
        if( myBundle != null )
        {
            Log.i("Creando el argument", myBundle.getString("searchKey"));
            frag.setArguments( myBundle );
        }
        mFragmentList.add( frag );
        mFragmentTitles.add( title );
    }

    @Override
    public CharSequence getPageTitle( int position )
    {
        return mFragmentTitles.get( position );
    }
}
