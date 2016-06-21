package com.recetapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.recetapp.util.FacebookUtil;
import com.recetapp.util.UserManager;
import com.recetapp.util.UserUtil;
import com.recetapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class WallActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpFabBt();
        setUpDrawer(toolbar);
        setUpUserData();
        setUpRv();
    }

    private void setUpRv() {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        //Layout Manager
        LinearLayoutManager llm = new LinearLayoutManager(WallActivity.this);
        rv.setLayoutManager(llm);

        //Adapter
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("ASD", "Tomate 1", "Los tomates tomatiles tomatantes 1"));
        recipes.add(new Recipe("ASD", "Tomate 2", "Los tomates tomatiles tomatantes 2"));
        recipes.add(new Recipe("ASD", "Tomate 3", "Los tomates tomatiles tomatantes 3"));
        recipes.add(new Recipe("ASD", "Tomate 4", "Los tomates tomatiles tomatantes 4"));
        recipes.add(new Recipe("ASD", "Tomate 5", "Los tomates tomatiles tomatantes 5"));
        recipes.add(new Recipe("ASD", "Tomate 6", "Los tomates tomatiles tomatantes 6"));
        recipes.add(new Recipe("ASD", "Tomate 7", "Los tomates tomatiles tomatantes 7"));
        recipes.add(new Recipe("ASD", "Tomate 8", "Los tomates tomatiles tomatantes 8"));
        recipes.add(new Recipe("ASD", "Tomate 9", "Los tomates tomatiles tomatantes 9"));
        WallRvAdapter rvAdapter = new WallRvAdapter(recipes);
        rv.setAdapter(rvAdapter);
    }

    private void setUpDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        DrawerArrowDrawable drawerArrow = new DrawerArrowDrawable(this);
        toolbar.setNavigationIcon(drawerArrow);
    }

    private void setUpFabBt() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WallActivity.this, CreateRecipeActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUpUserData() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        if(FacebookUtil.isFacebookLoggedIn()) {
            //User image
            ImageView userImage = (ImageView) hView.findViewById(R.id.userImage);
            FacebookUtil.getUserPicture(WallActivity.this,
                    AccessToken.getCurrentAccessToken().getUserId()).into(userImage);
        }
        TextView userHeaderName = (TextView) hView.findViewById(R.id.userHeaderName);
        userHeaderName.setText(UserManager.getManager().getUser().getName());
        TextView userHeaderEmail = (TextView) hView.findViewById(R.id.userHeaderEmail);
        userHeaderEmail.setText(UserManager.getManager().getUser().getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_close_session) {
            UserUtil.logOut();
            finish();
            Intent i = new Intent(WallActivity.this, MainActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
