package com.recetapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.recetapp.model.Recipe;

import java.util.List;


public class WallRvAdapter extends RecyclerView.Adapter<WallRvAdapter.RecipeViewHolder>{

    private List<Recipe> recipes;

    public WallRvAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_rv_item, parent, false);
        RecipeViewHolder pvh = new RecipeViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.userImage.setImageResource(R.drawable.logo_login);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView userImage;

        RecipeViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.cv);
            userImage = (ImageView) itemView.findViewById(R.id.recipeCVImage);
        }
    }

}
