package com.kornasdominika.appetize.cookbook.showrecipe.general.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.showrecipe.ui.ShowRecipeActivity;
import com.kornasdominika.appetize.cookbook.showrecipe.general.utils.General;
import com.kornasdominika.appetize.cookbook.showrecipe.general.utils.IGeneral;
import com.kornasdominika.appetize.model.Recipe;


public class GeneralFragment extends Fragment implements IGeneralFragment {

    private IGeneral general;

    private ImageView ivRecipeImage,
            ivStar,
            ivShoppingList;
    private TextView tvTitle,
            tvCategory,
            tvTime,
            tvCalories,
            tvPortions;
    private ListView lvIngredients;

    private long rid;
    private boolean isFavorite;

    public GeneralFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        general = new General(this);

        rid = ((ShowRecipeActivity) getActivity()).getRidFromParentFragment();

        findComponentsIds(view);
        general.getRecipe(rid);
        setOnClick();

        return view;
    }

    private void findComponentsIds(View view) {
        ivRecipeImage = view.findViewById(R.id.recipe_image);
        ivStar = view.findViewById(R.id.fav_star);
        tvTitle = view.findViewById(R.id.title);
        tvCategory = view.findViewById(R.id.category);
        tvTime = view.findViewById(R.id.time);
        tvCalories = view.findViewById(R.id.calories);
        tvPortions = view.findViewById(R.id.portions);
        ivShoppingList = view.findViewById(R.id.add_shopping_list);
        lvIngredients = view.findViewById(R.id.lv_ingredients);

        ivRecipeImage.setClipToOutline(true);
    }

    @Override
    public void setComponentsView(Recipe recipe) {
        tvTitle.setText(recipe.getName());
        tvCategory.setText(recipe.getCategory());
        tvTime.setText(String.valueOf(recipe.getCookingTime()));
        tvCalories.setText(String.valueOf(recipe.getCalories()));
        tvPortions.setText(String.valueOf(recipe.getPortions()));
    }

    @Override
    public void setImagesViews(Recipe recipe) {
        if (recipe.isFavorite()) {
            ivStar.setImageResource(R.drawable.ic_star);
        }

        if (recipe.getImage() != null) {
            Glide.with(getContext()).load(recipe.getImage()).into(ivRecipeImage);
        }
    }

    @Override
    public void setListView(Recipe recipe) {
        IngredientsListAdapter adapter = new IngredientsListAdapter(getActivity(), recipe.getIngredientList());
        lvIngredients.setAdapter(adapter);
    }

    @Override
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    private void setOnClick() {
        ivShoppingList.setOnClickListener(view -> {

        });

        ivStar.setOnClickListener(view -> makeRecipeFavorite());
    }

    private void makeRecipeFavorite() {
        if (isFavorite) {
            isFavorite = false;
            ivStar.setImageResource(R.drawable.ic_star_border);
        } else {
            isFavorite = true;
            ivStar.setImageResource(R.drawable.ic_star);
        }
        general.updateRecipeAsFavorite(rid, isFavorite);
    }
}