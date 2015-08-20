package com.won.testapp.category;


import java.util.ArrayList;

/**
 * Created by wonholee on 2015-08-20.
 */
public class CategoriesData {

    //@SerializedName("categories")
    private ArrayList<CategoryListData> categories = new ArrayList();

    public ArrayList getCategoriesData() {
        return categories;
    }

    public void setCategoriesData(ArrayList categoriesData) {
        this.categories = categoriesData;
    }


}
