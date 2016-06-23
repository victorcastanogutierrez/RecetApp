package com.recetapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.recetapp.createrecipegragments.CreateRecipePageAdapter;
import com.recetapp.createrecipegragments.RecipeStepsListener;


public class CreateRecipeActivity extends AppCompatActivity implements RecipeStepsListener {

    private TabLayout tabLayout;
    private CreateRecipePageAdapter adapter;
    private TabLayout.Tab currentTab;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setUpLabLayout();

        viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new CreateRecipePageAdapter (getSupportFragmentManager());


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void setUpLabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentTab = tab;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                currentTab = tab;
            }
        });
    }

    @Override
    public void addStep() {
        adapter.addNewStep(adapter.getCount());
        adapter.notifyDataSetChanged();
        tabLayout.addTab(tabLayout.newTab().setText("Tab "+(adapter.getCount())));
    }

    @Override
    public void removeStep() {
        adapter.destroyItem(tabLayout,currentTab.getPosition(),adapter.getItem(currentTab.getPosition()));
        adapter.removeStep(currentTab.getPosition());
        adapter.notifyDataSetChanged();
        TabLayout.Tab oldTab = currentTab;
        tabLayout.setScrollPosition(currentTab.getPosition()-1,0f,true); // move back before removing
        viewPager.setCurrentItem(currentTab.getPosition()-1);
        tabLayout.removeTab(oldTab);
    }
}
