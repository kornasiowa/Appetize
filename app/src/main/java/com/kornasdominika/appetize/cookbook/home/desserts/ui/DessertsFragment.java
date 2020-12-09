package com.kornasdominika.appetize.cookbook.home.desserts.ui;

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
import com.kornasdominika.appetize.cookbook.home.RecipesListAdapter;
import com.kornasdominika.appetize.cookbook.home.desserts.utils.Desserts;
import com.kornasdominika.appetize.cookbook.home.desserts.utils.IDesserts;
import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public class DessertsFragment extends Fragment implements IDessertsFragment{

    private IDesserts desserts;

    private ListView listView;
    private TextView tvMessage;

    public DessertsFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_desserts, container, false);
        desserts = new Desserts(this);

        listView = view.findViewById(R.id.lv_desserts);
        tvMessage = view.findViewById(R.id.message);

        desserts.getAllDessertsList("dessert");

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