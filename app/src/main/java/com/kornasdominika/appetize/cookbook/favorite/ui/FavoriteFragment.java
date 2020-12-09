package com.kornasdominika.appetize.cookbook.favorite.ui;

import android.os.Build;
import android.os.Bundle;

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
import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public class FavoriteFragment extends Fragment implements IFavoriteFragment {

    private IFavorite favorite;

    private ListView listView;
    private TextView tvMessage;

    public FavoriteFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        favorite = new Favorite(this);

        listView = view.findViewById(R.id.lv_favourites);
        tvMessage = view.findViewById(R.id.message);

        favorite.getAllFavoritesList();

        return view;
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
}