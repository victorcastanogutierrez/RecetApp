package com.recetapp.createrecipegragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CreateRecipePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public CreateRecipePageAdapter (FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
        fragments.add(new TabRecipeDataFragment());
        fragments.add(new TabRecipeStepFragment(1));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addNewStep(int position) {
        fragments.add(new TabRecipeStepFragment(position));
    }

    public void removeStep(int position) {
        fragments.remove(position);
    }
}
