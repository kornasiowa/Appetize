package com.kornasdominika.appetize.cookbook.showrecipe.preparation.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.showrecipe.ui.ShowRecipeActivity;
import com.kornasdominika.appetize.cookbook.showrecipe.preparation.utils.IPreparation;
import com.kornasdominika.appetize.cookbook.showrecipe.preparation.utils.Preparation;
import com.kornasdominika.appetize.model.Recipe;


public class PreparationFragment extends Fragment implements IPreparationFragment {

    private IPreparation preparation;

    private ListView lvSteps;

    public PreparationFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preparation, container, false);
        preparation = new Preparation(this);

        long rid = ((ShowRecipeActivity) getActivity()).getRidFromParentFragment();

        findComponentsIds(view);
        preparation.getRecipe(rid);

        return view;
    }

    private void findComponentsIds(View view) {
        lvSteps = view.findViewById(R.id.lv_steps);
    }

    @Override
    public void setListView(Recipe recipe) {
        StepsListAdapter adapter = new StepsListAdapter(getActivity(), recipe.getStepList());
        lvSteps.setAdapter(adapter);
    }
}