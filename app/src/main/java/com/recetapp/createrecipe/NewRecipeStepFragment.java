package com.recetapp.createrecipe;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.recetapp.R;

public class NewRecipeStepFragment extends Fragment {


    private static final String ARG_SECTION_NUMBER = "section_number";

    public NewRecipeStepFragment() {
    }


    public static NewRecipeStepFragment newInstance(String section) {

        NewRecipeStepFragment f = new NewRecipeStepFragment();
        Bundle b = new Bundle();
        b.putString("msg", section);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //De aquí podemos sacar el numero del que se ha creado
        View rootView = inflater.inflate(R.layout.fragment_createstep_recipe, container, false);
        /*TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;*/



        setUpBtNextStep(rootView);
        return rootView;
    }

    private void setUpBtNextStep(final View view) {
        Button btNextStep = (Button) view.findViewById(R.id.btNextStep);
        btNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make((View) view.findViewById(R.id.rrLay), "Añadido nuevo paso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }
}
