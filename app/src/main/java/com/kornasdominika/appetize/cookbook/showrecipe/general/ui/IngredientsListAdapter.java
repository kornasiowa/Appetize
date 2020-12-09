package com.kornasdominika.appetize.cookbook.showrecipe.general.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.model.Ingredient;

import java.util.List;

public class IngredientsListAdapter extends ArrayAdapter<Ingredient> {

    private final Activity context;
    private final List<Ingredient> ingredientsList;

    private TextView tvAmount,
            tvName;

    public IngredientsListAdapter(@NonNull Activity context, @NonNull List<Ingredient> objects) {
        super(context, R.layout.list_element_ingredient, objects);
        this.context = context;
        this.ingredientsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.list_element_ingredient, null, true);

        findComponentsByIds(view);
        setComponents(position);

        return view;
    }

    private void findComponentsByIds(View view) {
        tvAmount = view.findViewById(R.id.amount);
        tvName = view.findViewById(R.id.ingredient);
    }

    private void setComponents(int position) {
        tvAmount.setText(String.format("%s", ingredientsList.get(position).getAmount()));
        tvName.setText(String.format("%s", ingredientsList.get(position).getIngredientName()));
    }
}
