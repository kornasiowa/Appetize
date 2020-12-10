package com.kornasdominika.appetize.cookbook.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.showrecipe.ui.ShowRecipeActivity;
import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public class RecipesListAdapter extends ArrayAdapter<Recipe> {

    private final Activity context;
    private final List<Recipe> recipesList;

    private TextView tvTitle,
            tvTime,
            tvCalories;
    private ImageView ivStar,
            background;

    public RecipesListAdapter(@NonNull Activity context, @NonNull List<Recipe> objects) {
        super(context, R.layout.list_element_recipe, objects);
        this.context = context;
        this.recipesList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.list_element_recipe, null, true);

        findComponentsByIds(view);
        setComponents(position);
       // setOnClick(view, position);

        return view;
    }

    private void findComponentsByIds(View view) {
        tvTitle = view.findViewById(R.id.title);
        tvTime = view.findViewById(R.id.time);
        tvCalories = view.findViewById(R.id.calories);
        ivStar = view.findViewById(R.id.fav_star);
        background = view.findViewById(R.id.recipe_background);
    }

    private void setComponents(int position) {
        tvTitle.setText(String.format("%s", recipesList.get(position).getName()));
        tvTime.setText(String.format("%s", recipesList.get(position).getCookingTime()));
        tvCalories.setText(String.format("%s", recipesList.get(position).getCalories()));

        boolean isFavourite = recipesList.get(position).isFavorite();
        if (isFavourite) {
            ivStar.setImageResource(R.drawable.ic_star);
        }

        String image = recipesList.get(position).getImage();
        if (image != null) {
            Glide.with(getContext()).load(image).into(background);
        }
    }

}
