package com.won.testapp.book.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.won.testapp.R;

import java.util.List;

/**
 * Created by wonholee on 2015-08-20.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {

    private List<BookListData> mBookListData;
    private BookListClickListener mBookListClickListener;
    //private listener


    public BookListAdapter(List<BookListData> mBookListData) {
        this.mBookListData = mBookListData;
    }

    @Override
    public BookListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_list_item, parent, false);

        ViewHolder viewHolderItem = new ViewHolder(view, viewType);

        return  viewHolderItem;
    }

    @Override
    public void onBindViewHolder(BookListAdapter.ViewHolder viewHolder, int position) {

        final BookListData item = mBookListData.get(position);

        viewHolder.titleTextView.setText(item.getTitle());


        viewHolder.authorTextView.setText(item.getAuthor());

        if(!viewHolder.containerView.hasOnClickListeners()) {

            viewHolder.containerView.setOnClickListener(new BookListClick(position));
        }
    }


    @Override
    public int getItemCount() {
        return mBookListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View containerView;

        TextView titleTextView;
        TextView authorTextView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            containerView = (View) itemView.findViewById(R.id.fragment_book_list_item_linear_container);
            titleTextView = (TextView) itemView.findViewById(R.id.fragment_book_list_item_textview_title);
            authorTextView = (TextView) itemView.findViewById(R.id.fragment_book_list_item_textview_author);

        }


    }

    public void setmBookListData(List<BookListData> mBookListData) {
        this.mBookListData = mBookListData;
    }

    private class BookListClick implements View.OnClickListener {

        private int position;

        public BookListClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {

            if(mBookListClickListener != null) {
                int bookId = mBookListData.get(position).getBookId();
                mBookListClickListener.bookListClick(bookId);
            } else {
                Log.e("BookListAdapter", "mBookListClickListener is not implement");
            }
        }
    }

    public void setmBookListClickListener(BookListClickListener mBookListClickListener) {
        this.mBookListClickListener = mBookListClickListener;
    }
}
