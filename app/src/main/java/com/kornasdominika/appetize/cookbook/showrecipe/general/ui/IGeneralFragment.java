package com.kornasdominika.appetize.cookbook.showrecipe.general.ui;

import com.kornasdominika.appetize.model.Recipe;

public interface IGeneralFragment {

    void setComponentsView(Recipe recipe);

    void setImagesViews(Recipe recipe);

    void setListView(Recipe recipe);
}
