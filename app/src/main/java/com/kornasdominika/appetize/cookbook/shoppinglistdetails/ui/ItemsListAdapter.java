package com.kornasdominika.appetize.cookbook.shoppinglistdetails.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.shoppinglistdetails.utils.ShoppingListDetails;
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

        showCheckBox();

        changeItemStatusWithCheckBox(position);
        return view;
    }

    private void findComponentsByIds(View view) {
        tvItem = view.findViewById(R.id.item);
        chbBought = view.findViewById(R.id.check_box);
    }

    private void setComponents(int position) {
        tvItem.setText(String.format("%s", itemList.get(position).getItemName()));
        if (itemList.get(position).isBought()) {
            tvItem.setPaintFlags(tvItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tvItem.setTextColor(ContextCompat.getColor(context, R.color.primary_light));
        }
        chbBought.setChecked(itemList.get(position).isBought());
    }

    private void showCheckBox() {
        if (ShoppingListDetailsActivity.isActionMode) {
            chbBought.setVisibility(View.VISIBLE);
        } else {
            chbBought.setVisibility(View.GONE);
        }
    }

    private void changeItemStatusWithCheckBox(int position) {
        chbBought.setTag(position);

        chbBought.setOnCheckedChangeListener((compoundButton, b) -> {
            int position1 = (int) compoundButton.getTag();
            ShoppingListDetails.itemList.get(position1).setBought(!ShoppingListDetails.itemList.get(position1).isBought());
        });
    }

}
