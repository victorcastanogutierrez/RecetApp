package com.recetapp.createrecipegragments;

/**
 * Created by Victor on 22/06/2016.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.recetapp.CreateRecipeActivity;
import com.recetapp.R;

public class TabRecipeStepFragment extends Fragment {

    private RecipeStepsListener listener;
    private int position;

    public TabRecipeStepFragment(int position) {
        this.position = position;
    }

    public TabRecipeStepFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_recipestep, container, false);
        setUpNewStepButton(view);
        setUpRemoveStepButton(view);
        setUpTxPaso(view);
        setUpScrollingView(view);
        return view;
    }

    private void setUpRemoveStepButton(View view) {
        Button removeStepBt = (Button) view.findViewById(R.id.removeStepBt);
        final Object obj = this;
        removeStepBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.removeStep();
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

    public void setUpTxPaso(View view) {
        TextView txStep = (TextView) view.findViewById(R.id.txStep);
        txStep.setText(position+"");
    }

    public void setUpScrollingView(View view) {

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
