package com.recetapp.createrecipegragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class CreateRecipePageAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public CreateRecipePageAdapter (FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0: return new TabRecipeDataFragment();
            default: return new TabRecipeStepFragment();
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


    public void setmNumOfTabs(int mNumOfTabs) {
        this.mNumOfTabs = mNumOfTabs;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) object);
        trans.commit();
    }

}
