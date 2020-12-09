package com.kornasdominika.appetize.cookbook.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.addrecipe.ui.AddRecipeActivity;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton btnAddRecipe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        findComponentsIds(root);
        setAdapter();
        setOnClick();
        return root;
    }

    private void findComponentsIds(View view){
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        btnAddRecipe = view.findViewById(R.id.add_recipe);
    }

    private void setAdapter(){
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setOnClick(){
        btnAddRecipe.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AddRecipeActivity.class));
        });
    }

}