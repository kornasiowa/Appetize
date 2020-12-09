package com.kornasdominika.appetize.cookbook.home.appetizers.ui;

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
import com.kornasdominika.appetize.cookbook.home.appetizers.utils.Appetizers;
import com.kornasdominika.appetize.cookbook.home.appetizers.utils.IAppetizers;
import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public class AppetizersFragment extends Fragment implements IAppetizersFragment {

    private IAppetizers appetizers;

    private ListView listView;
    private TextView tvMessage;

    public AppetizersFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appetizers, container, false);
        appetizers = new Appetizers(this);

        listView = view.findViewById(R.id.lv_appetizers);
        tvMessage = view.findViewById(R.id.message);

        appetizers.getAllAppetizersList("appetizer");

        return view;
    }

    public void setListAdapter(List<Recipe> recipesList){
        RecipesListAdapter adapter = new RecipesListAdapter(getActivity(), recipesList);
        listView.setAdapter(adapter);
    }

    public void checkIfRecipesExists(boolean isRecipesExists, String message) {
        if (isRecipesExists) {
            tvMessage.setVisibility(View.INVISIBLE);
        } else {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }
}