package com.kornasdominika.appetize.cookbook.home.maincourses.ui;

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
import com.kornasdominika.appetize.cookbook.home.maincourses.utils.IMainCourses;
import com.kornasdominika.appetize.cookbook.home.maincourses.utils.MainCourses;
import com.kornasdominika.appetize.model.Recipe;

import java.util.List;

public class MainCoursesFragment extends Fragment implements IMainCoursesFragment{

    private IMainCourses mainCourses;

    private ListView listView;
    private TextView tvMessage;

    public MainCoursesFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_courses, container, false);
        mainCourses = new MainCourses(this);

        listView = view.findViewById(R.id.lv_main_courses);
        tvMessage = view.findViewById(R.id.message);

        mainCourses.getAllMainCoursesList("main course");

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