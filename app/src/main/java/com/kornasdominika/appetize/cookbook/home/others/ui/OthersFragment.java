package com.kornasdominika.appetize.cookbook.home.others.ui;

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
import com.kornasdominika.appetize.cookbook.home.others.utils.IOthers;
import com.kornasdominika.appetize.cookbook.home.others.utils.Others;
import com.kornasdominika.appetize.model.Recipe;

import java.util.List;


public class OthersFragment extends Fragment implements IOthersFragment{

    private IOthers others;

    private ListView listView;
    private TextView tvMessage;

    public OthersFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        others = new Others(this);

        listView = view.findViewById(R.id.lv_others);
        tvMessage = view.findViewById(R.id.message);

        others.getAllOthersList("other");

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