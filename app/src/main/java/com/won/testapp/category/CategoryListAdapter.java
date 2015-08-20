package com.won.testapp.category;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.won.testapp.R;
import com.won.testapp.category.sub.SubCategoryListClickListener;

import java.util.List;

/**
 * Created by wonholee on 2015-08-20.
 */
public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder>{

    public static final int CATEGORY = 10;
    public static final int SUB_CATEGORY = 11;

    private List<CategoryListData> mCategoryListData;
    private CategoryListClickListener mCategoryListClickListener;
    private SubCategoryListClickListener mSubCategoryListClickListener;

    private int listType;

    public CategoryListAdapter(List<CategoryListData> mCategoryListData, int listType) {
        this.mCategoryListData = mCategoryListData;
        this.listType = listType;
    }

    @Override
    public CategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_list_item, parent, false);

        ViewHolder viewHolderItem = new ViewHolder(view, viewType);

        return viewHolderItem;
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.ViewHolder viewHolder, int position) {

        final CategoryListData item = mCategoryListData.get(position);

        viewHolder.categoryName.setText(item.getCategoryName());

        if(!viewHolder.containerView.hasOnClickListeners()) {
            viewHolder.containerView.setOnClickListener(new CategoryListClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return mCategoryListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View containerView;

        TextView categoryName;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            containerView = (View) itemView.findViewById(R.id.fragment_category_list_item_linear_container);
            categoryName = (TextView) itemView.findViewById(R.id.fragment_category_list_item_textview_name);


        }
    }

    public void setmCategoryListData(List<CategoryListData> mCategoryListData) {
        this.mCategoryListData = mCategoryListData;
    }

    private class CategoryListClick implements View.OnClickListener {

        int position;

        @Override
        public void onClick(View view) {

            if(listType == CATEGORY) {

                if(mCategoryListClickListener != null) {
                    int categoryId = mCategoryListData.get(position).getCategoryId();
                    mCategoryListClickListener.categoryListClick(categoryId);
                } else {
                    Log.e("CategoryListAdpter", "mCategoryListClickListener is not implement");
                }

            } else if(listType == SUB_CATEGORY) {

                if(mSubCategoryListClickListener != null) {
                    int categoryId = mCategoryListData.get(position).getCategoryId();
                    mSubCategoryListClickListener.subCategoryListClick(categoryId);
                } else {
                    Log.e("CategoryListAdpter", "mSubCategoryListClickListener is not implement");
                }

            }


        }

        public CategoryListClick(int position) {
            this.position = position;
        }
    }


    public void setmCategoryListClickListener(CategoryListClickListener mCategoryListClickListener) {
        this.mCategoryListClickListener = mCategoryListClickListener;
    }

    public void setmSubCategoryListClickListener(SubCategoryListClickListener mSubCategoryListClickListener) {
        this.mSubCategoryListClickListener = mSubCategoryListClickListener;
    }
}
