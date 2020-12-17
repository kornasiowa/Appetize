package com.kornasdominika.appetize.cookbook.shoppinglist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.kornasdominika.appetize.R;
import com.kornasdominika.appetize.cookbook.shoppinglist.utils.IShopping;
import com.kornasdominika.appetize.cookbook.shoppinglist.utils.Shopping;
import com.kornasdominika.appetize.cookbook.shoppinglistdetails.ui.ShoppingListDetailsActivity;
import com.kornasdominika.appetize.model.ShoppingList;

import java.util.List;

public class ShoppingFragment extends Fragment implements IShoppingFragment {

    private IShopping shopping;

    private EditText edName;
    private TextView tvHeader,
            tvMessage;
    private ImageView ivCreateList,
            ivClose;
    private ListView lvShoppingLists;

    private String name;
    private boolean editMode = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shopping, container, false);
        shopping = new Shopping(this);

        findComponentsIds(root);
        setOnClick();
        shopping.getAllUserShoppingLists();

        return root;
    }

    private void findComponentsIds(View view) {
        edName = view.findViewById(R.id.list_name);
        tvHeader = view.findViewById(R.id.header);
        tvMessage = view.findViewById(R.id.message);
        ivCreateList = view.findViewById(R.id.create_shopping_list);
        ivClose = view.findViewById(R.id.close);
        lvShoppingLists = view.findViewById(R.id.lv_shopping_list);
    }

    private void setOnClick() {
        ivClose.setOnClickListener(view -> {
            changeAddingView();
        });

        ivCreateList.setOnClickListener(view -> {
            createShoppingList();
        });

        lvShoppingLists.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), ShoppingListDetailsActivity.class);
            intent.putExtra("LID", Shopping.shoppingLists.get(i).getLid());
            startActivityForResult(intent, 11);
        });
    }

    private boolean checkIfNameNotEmpty() {
        name = String.valueOf(edName.getText());
        return !TextUtils.isEmpty(name);
    }

    private void createShoppingList() {
        if (editMode) {
            if (checkIfNameNotEmpty()) {
                shopping.checkIfNameAlreadyExist(name);
            } else {
                showMessage("Name required.");
            }
        } else {
            changeAddingView();
        }
    }

    @Override
    public void changeAddingView() {
        if (editMode) {
            tvHeader.setVisibility(View.VISIBLE);
            edName.setVisibility(View.GONE);
            edName.setText("");
            ivClose.setVisibility(View.GONE);
            ivCreateList.setImageResource(R.drawable.ic_add_shopping);
            editMode = false;
        } else {
            tvHeader.setVisibility(View.GONE);
            edName.setVisibility(View.VISIBLE);
            ivClose.setVisibility(View.VISIBLE);
            ivCreateList.setImageResource(R.drawable.ic_save);
            editMode = true;
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListAdapter(List<ShoppingList> shoppingList) {
        ShoppingListAdapter adapter = new ShoppingListAdapter(getActivity(), shoppingList);
        lvShoppingLists.setAdapter(adapter);
    }

    @Override
    public void checkIfShoppingListsExists(boolean isShoppingListsExists, String message) {
        if (isShoppingListsExists) {
            tvMessage.setVisibility(View.INVISIBLE);
        } else {
            tvMessage.setText(message);
            tvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                shopping.getAllUserShoppingLists();
            }
        }
    }
}