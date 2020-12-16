package com.kornasdominika.appetize.cookbook.shoppinglistdetails.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.model.Item;

import java.util.List;

public class ItemsListAdapter extends ArrayAdapter<Item> {

    private final Activity context;
    private final List<Item> itemList;

    private TextView tvItem;
    private CheckBox chbBought;

    public ItemsListAdapter(@NonNull Activity context, @NonNull List<Item> objects) {
        super(context, R.layout.list_element_item, objects);
        this.context = context;
        this.itemList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.list_element_item, null, true);

        findComponentsByIds(view);
        setComponents(position);

        return view;
    }

    private void findComponentsByIds(View view) {
        tvItem = view.findViewById(R.id.item);
        chbBought = view.findViewById(R.id.check_box);
    }

    private void setComponents(int position) {
        tvItem.setText(String.format("%s", itemList.get(position).getItemName()));
        chbBought.setChecked(itemList.get(position).isBought());
    }

}
