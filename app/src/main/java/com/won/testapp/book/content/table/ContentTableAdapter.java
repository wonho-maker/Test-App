package com.won.testapp.book.content.table;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.won.testapp.MainActivity;
import com.won.testapp.R;


import java.util.List;

/**
 * Created by wonholee on 2015-08-20.
 */
public class ContentTableAdapter extends RecyclerView.Adapter<ContentTableAdapter.ViewHolder> {

    private List<ContentTableData> mContentTableData;
    private ContentTableClickListener mContentTableClickListener;


    public ContentTableAdapter(List<ContentTableData> mContentTableData) {

        this.mContentTableData = mContentTableData;
    }

    @Override
    public ContentTableAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_content_table_item, parent, false);

        ViewHolder viewHolderItem = new ViewHolder(view, viewType);

        return  viewHolderItem;
    }

    @Override
    public void onBindViewHolder(ContentTableAdapter.ViewHolder viewHolder, int position) {


        final ContentTableData item = mContentTableData.get(position);

        viewHolder.titleTextView.setText(item.getTableOrder() +". "+item.getHeading());

        if(!viewHolder.containerView.hasOnClickListeners()) {

            viewHolder.containerView.setOnClickListener(new ContentTableClick(position));
        }
    }

    @Override
    public int getItemCount() {
        return mContentTableData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        View containerView;

        TextView titleTextView;

        public ViewHolder(View itemView, int viewType) {

            super(itemView);

            containerView = (View) itemView.findViewById(R.id.fragment_book_content_table_item_linear_container);
            titleTextView = (TextView) itemView.findViewById(R.id.fragment_book_content_table_item_textview_title);

        }
    }

    public void setmContentTableData(List<ContentTableData> mContentTableData) {
        this.mContentTableData = mContentTableData;
    }

    private class ContentTableClick implements View.OnClickListener {

        int position;

        public ContentTableClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {

            if(mContentTableClickListener != null) {
                int contentTableId = mContentTableData.get(position).getContentTableId();
                mContentTableClickListener.contentTableClick(contentTableId);
            }
        }
    }

    public void setmContentTableClickListener(ContentTableClickListener mContentTableClickListener) {
        this.mContentTableClickListener = mContentTableClickListener;
    }
}
