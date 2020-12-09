package com.kornasdominika.appetize.cookbook.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.kornasdominika.appetize.cookbook.home.appetizers.ui.AppetizersFragment;
import com.kornasdominika.appetize.cookbook.home.desserts.ui.DessertsFragment;
import com.kornasdominika.appetize.cookbook.home.maincourses.ui.MainCoursesFragment;
import com.kornasdominika.appetize.cookbook.home.others.ui.OthersFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabTitles = new String[]{"Appetizers", "Desserts", "Main courses", "Others"};

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AppetizersFragment();
            case 1:
                return new DessertsFragment();
            case 2:
                return new MainCoursesFragment();
            case 3:
                return new OthersFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
