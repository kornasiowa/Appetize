package com.kornasdominika.appetize.cookbook.showrecipe.general.ui;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.showrecipe.ui.ShowRecipeActivity;
import com.kornasdominika.appetize.cookbook.showrecipe.general.utils.General;
import com.kornasdominika.appetize.cookbook.showrecipe.general.utils.IGeneral;
import com.kornasdominika.appetize.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.kornasdominika.appetize.cookbook.showrecipe.general.utils.General.isFavorite;

public class GeneralFragment extends Fragment implements IGeneralFragment {

    private IGeneral general;

    private ImageView ivRecipeImage,
            ivStar,
            ivShoppingList;
    private TextView tvTitle,
            tvCategory,
            tvTime,
            tvCalories,
            tvPortions;
    private ListView lvIngredients;

    private long rid;
    private String listName;
    private List<String> itemsList;

    public GeneralFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general, container, false);
        general = new General(this);

        rid = ((ShowRecipeActivity) getActivity()).getRidFromParentFragment();

        findComponentsIds(view);
        general.getRecipe(rid);
        general.getUserShoppingLists();
        setOnClick();

        return view;
    }

    private void findComponentsIds(View view) {
        ivRecipeImage = view.findViewById(R.id.recipe_image);
        ivStar = view.findViewById(R.id.fav_star);
        tvTitle = view.findViewById(R.id.title);
        tvCategory = view.findViewById(R.id.category);
        tvTime = view.findViewById(R.id.time);
        tvCalories = view.findViewById(R.id.calories);
        tvPortions = view.findViewById(R.id.portions);
        ivShoppingList = view.findViewById(R.id.add_shopping_list);
        lvIngredients = view.findViewById(R.id.lv_ingredients);

        ivRecipeImage.setClipToOutline(true);
    }

    @Override
    public void setComponentsView(Recipe recipe) {
        tvTitle.setText(recipe.getName());
        tvCategory.setText(recipe.getCategory());
        tvTime.setText(String.valueOf(recipe.getCookingTime()));
        tvCalories.setText(String.valueOf(recipe.getCalories()));
        tvPortions.setText(String.valueOf(recipe.getPortions()));
    }

    @Override
    public void setImagesViews(Recipe recipe) {
        if (recipe.isFavorite()) {
            ivStar.setImageResource(R.drawable.ic_star);
        }

        if (recipe.getImage() != null) {
            Glide.with(getContext()).load(recipe.getImage()).into(ivRecipeImage);
        }
    }

    @Override
    public void setListView(Recipe recipe) {
        IngredientsListAdapter adapter = new IngredientsListAdapter(getActivity(), recipe.getIngredientList());
        lvIngredients.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void setOnClick() {
        ivShoppingList.setOnClickListener(view -> {
            createShoppingListDialog();
        });

        ivStar.setOnClickListener(view -> makeRecipeFavorite());
    }

    private void makeRecipeFavorite() {
        if (isFavorite) {
            isFavorite = false;
            ivStar.setImageResource(R.drawable.ic_star_border);
        } else {
            isFavorite = true;
            ivStar.setImageResource(R.drawable.ic_star);
        }
        general.updateRecipeAsFavorite(rid, isFavorite);
    }

    private void createShoppingListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_shopping_list, null);
        builder.setTitle("Add items to your shopping list");

        configureSpinnerForDialog(view);
        configureListViewForDialog(view);
        setDialogButtons(builder);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void configureSpinnerForDialog(View view) {
        Spinner spinner = view.findViewById(R.id.shopping_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, General.shoppingListsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).toString().equalsIgnoreCase("No shopping lists found")) {
                    listName = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void configureListViewForDialog(View view) {
        itemsList = new ArrayList<>();

        ListView listView = view.findViewById(R.id.lv_recipe_items);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_multiple_choice, General.items);
        listView.setAdapter(adapter);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            String item = (String) adapterView.getItemAtPosition(i);
            if (!itemsList.contains(item)) {
                itemsList.add(item);
            } else {
                itemsList.remove(item);
            }
        });
    }

    private void setDialogButtons(AlertDialog.Builder builder) {
        builder.setPositiveButton("Add", (dialogInterface, i) -> {
            general.updateShoppingList(listName, itemsList);
            dialogInterface.dismiss();
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
    }

}