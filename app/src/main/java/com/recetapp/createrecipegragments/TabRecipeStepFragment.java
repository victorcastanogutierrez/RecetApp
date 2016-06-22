package com.recetapp.createrecipegragments;

/**
 * Created by Victor on 22/06/2016.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.recetapp.CreateRecipeActivity;
import com.recetapp.R;

public class TabRecipeStepFragment extends Fragment {

    private RecipeStepsListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_recipestep, container, false);

        setUpNewStepButton(view);
        setUpRemoveStepButton(view);
        return view;
    }

    private void setUpRemoveStepButton(View view) {
        Button removeStepBt = (Button) view.findViewById(R.id.removeStepBt);
        final Object obj = this;
        removeStepBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.removeStep(obj);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RecipeStepsListener) context;
        } catch (ClassCastException castException) {
            // Class does not implements RecipeStepListener
        }
    }

    public void setUpNewStepButton(View view) {
        Button newStepBt = (Button) view.findViewById(R.id.newStepBt);
        newStepBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addStep();
            }
        });
    }
}
