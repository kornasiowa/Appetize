package com.kornasdominika.appetize.cookbook.favorite.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.favorite.utils.Favorite;
import com.kornasdominika.appetize.cookbook.favorite.utils.IFavorite;
import com.kornasdominika.appetize.cookbook.home.RecipesListAdapter;
import com.kornasdominika.appetize.cookbook.showrecipe.ui.ShowRecipeActivity;
import com.kornasdominika.appetize.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements IFavoriteFragment {

    private IFavorite favorite;

    private ListView listView;
    private TextView tvMessage;

    private List<Recipe> recipeList;

    public FavoriteFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favorite = new Favorite(this);
        recipeList = new ArrayList<>();

        listView = view.findViewById(R.id.lv_favourites);
        tvMessage = view.findViewById(R.id.message);

        favorite.getAllFavoritesList();
        setOnClick();

        return view;
    }

    @Override
    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public void setListAdapter(List<Recipe> recipesList) {
        RecipesListAdapter adapter = new RecipesListAdapter(getActivity(), recipesList);
        listView.setAdapter(adapter);
    }

    @Override
    public void checkIfRecipesExists(boolean isRecipesExists, String message) {
        if (isRecipesExists) {
            tvMessage.setVisibility(View.INVISIBLE);
        } else {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }

    private void setOnClick() {
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getContext(), ShowRecipeActivity.class);
            intent.putExtra("RID", recipeList.get(i).getRid());
            intent.putExtra("IMAGE", recipeList.get(i).getImage());
            startActivityForResult(intent, 6);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6) {
            if (resultCode == Activity.RESULT_OK) {
                favorite.getAllFavoritesList();
            }
        }
    }
}