package com.kornasdominika.appetize.cookbook.shoppinglistdetails.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.shoppinglistdetails.utils.IShoppingListDetails;
import com.kornasdominika.appetize.cookbook.shoppinglistdetails.utils.ShoppingListDetails;
import com.kornasdominika.appetize.model.Item;
import com.kornasdominika.appetize.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailsActivity extends AppCompatActivity implements IShoppingListDetailsActivity {

    private IShoppingListDetails shoppingListDetails;

    private Toolbar toolbar;
    private TextView tvListName,
            tvListCount;
    private ListView lvItems;
    private EditText etItemName;
    private ImageView ivAddItem;

    private long lid;
    private String item;
    private List<Item> itemsList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_details);
        shoppingListDetails = new ShoppingListDetails(this);

        itemsList = new ArrayList<>();

        lid = getShoppingListId();

        findComponentsIds();
        setOnClick();
        shoppingListDetails.getShoppingListDetails(lid);

    }

    private void findComponentsIds() {
        toolbar = findViewById(R.id.toolbar);
        tvListName = findViewById(R.id.list_name);
        tvListCount = findViewById(R.id.list_count);
        lvItems = findViewById(R.id.lv_items);
        etItemName = findViewById(R.id.item_name);
        ivAddItem = findViewById(R.id.add_item);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setOnClick() {
        toolbar.setNavigationOnClickListener(view -> {
            finishCurrentActivity();
        });

        ivAddItem.setOnClickListener(view -> {
            if (checkIfNameNotEmpty()) {
                shoppingListDetails.addNewItemToList(lid, item);

            } else {
                showMessage("Item required.");
            }
        });

    }

    private boolean checkIfNameNotEmpty() {
        item = String.valueOf(etItemName.getText());
        return !TextUtils.isEmpty(item);
    }

    public void clear(){
        etItemName.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_delete) {
            createDeletingDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDeletingDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete shopping list");
        dialog.setMessage("This operation is irreversible. Are you sure you want to delete the shopping list?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            shoppingListDetails.deleteShoppingList(lid);
        });

        dialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private long getShoppingListId() {
        return getIntent().getLongExtra("LID", 0);
    }

    @Override
    public void setComponentsView(ShoppingList shoppingList) {
        tvListName.setText(shoppingList.getName());
        tvListCount.setText(String.valueOf(shoppingList.getItemsList().size()));
    }

    @Override
    public void setListAdapter(List<Item> list) {
        ItemsListAdapter adapter = new ItemsListAdapter(this, list);
        lvItems.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishCurrentActivity() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishCurrentActivity();
    }
}