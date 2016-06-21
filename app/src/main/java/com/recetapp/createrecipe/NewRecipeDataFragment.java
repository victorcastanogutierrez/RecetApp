package com.recetapp.createrecipe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recetapp.R;

public class NewRecipeDataFragment extends Fragment {

    public static NewRecipeDataFragment newInstance(String section) {

        NewRecipeDataFragment f = new NewRecipeDataFragment();
        Bundle b = new Bundle();
        b.putString("msg", section);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //De aquí podemos sacar el numero del que se ha creado
        /*View rootView = inflater.inflate(R.layout.fragment_create_recipe, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;*/
        return inflater.inflate(R.layout.fragment_create_recipe, container, false);
    }
}
