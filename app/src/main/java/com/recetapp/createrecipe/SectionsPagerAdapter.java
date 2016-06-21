package com.recetapp.createrecipe;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class SectionsPagerAdapter extends PagerAdapter {

    private List<View> views;

    public SectionsPagerAdapter() {
        views = new ArrayList<>();
    }

    @Override
    public int getItemPosition (Object object)
    {
        int index = views.indexOf (object);
        if (index == -1)
            return POSITION_NONE;
        else
            return index;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = views.get (position);
        container.addView (v);
        return v;
    }

    @Override
    public boolean isViewFromObject (View view, Object object)
    {
        return view == object;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    public int addView (View v)
    {
        return addView (v, views.size());
    }

    public int addView (View v, int position)
    {
        views.add (v);
        notifyDataSetChanged();
        return position;
    }

    public int removeView (ViewPager pager, View v)
    {
        return removeView (pager, views.indexOf (v));
    }

    public int removeView (ViewPager pager, int position)
    {
        pager.setAdapter (null);
        views.remove (position);
        pager.setAdapter (this);
        return position;
    }

    public View getView (int position)
    {
        return views.get (position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Datos de la receta";
            default:
                return "Ingredientes ("+position+")";
        }
    }
}
