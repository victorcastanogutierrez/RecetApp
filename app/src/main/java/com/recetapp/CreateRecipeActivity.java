package com.recetapp;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.recetapp.createrecipe.SectionsPagerAdapter;

public class CreateRecipeActivity extends Activity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter();

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //Create the initial pages
        LayoutInflater inflater = CreateRecipeActivity.this.getLayoutInflater();
        FrameLayout fl = (FrameLayout) inflater.inflate (R.layout.fragment_create_recipe, null);
        mSectionsPagerAdapter.addView (fl, 0);

        fl = (FrameLayout) inflater.inflate (R.layout.fragment_createstep_recipe, null);
        mSectionsPagerAdapter.addView (fl, 0);
        setUpNextStepButton(fl);
    }

    private void setUpNextStepButton(final FrameLayout view) {
        Button btNextStep = (Button) view.findViewById(R.id.btNextStep);
        btNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make((View) view.findViewById(R.id.rrLay), "Añadido nuevo paso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                LayoutInflater inflater = CreateRecipeActivity.this.getLayoutInflater();
                FrameLayout fl = (FrameLayout) inflater.inflate (R.layout.fragment_createstep_recipe, null);
                mSectionsPagerAdapter.addView (fl, 0);
                setUpNextStepButton(fl);
            }
        });
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to add a view to the ViewPager.
    public void addView (View newPage)
    {
        int pageIndex = mSectionsPagerAdapter.addView (newPage);
        // You might want to make "newPage" the currently displayed page:
        mViewPager.setCurrentItem (pageIndex, true);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    public void removeView (View defunctPage)
    {
        int pageIndex = mSectionsPagerAdapter.removeView (mViewPager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == mSectionsPagerAdapter.getCount())
            pageIndex--;
        mViewPager.setCurrentItem (pageIndex);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage ()
    {
        return mSectionsPagerAdapter.getView (mViewPager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow)
    {
        mViewPager.setCurrentItem (mSectionsPagerAdapter.getItemPosition (pageToShow), true);
    }
}
