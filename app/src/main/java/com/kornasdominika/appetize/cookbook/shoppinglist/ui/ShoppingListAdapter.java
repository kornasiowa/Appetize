package com.kornasdominika.appetize.cookbook.shoppinglist.ui;

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
import com.kornasdominika.appetize.model.ShoppingList;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {

    private final Activity context;
    private final List<ShoppingList> shoppingList;

    private TextView tvName,
            tvCount;

    public ShoppingListAdapter(@NonNull Activity context, @NonNull List<ShoppingList> objects) {
        super(context, R.layout.list_element_shopping_list, objects);
        this.context = context;
        this.shoppingList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.list_element_shopping_list, null, true);

        findComponentsByIds(view);
        setComponents(position);

        return view;
    }

    private void findComponentsByIds(View view) {
        tvName = view.findViewById(R.id.name);
        tvCount = view.findViewById(R.id.list_count);
    }

    private void setComponents(int position) {
        tvName.setText(String.format("%s", shoppingList.get(position).getName()));
        tvCount.setText(String.format("%s", shoppingList.get(position).getItemsList().size()));
    }

}
