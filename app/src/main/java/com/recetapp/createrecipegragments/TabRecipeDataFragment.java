package com.recetapp.createrecipegragments;

/**
 * Created by Victor on 22/06/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recetapp.R;

public class TabRecipeDataFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.tab_fragment_recipedata, container, false);
        return view;
    }
}
