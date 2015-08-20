package com.won.testapp.book;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.won.testapp.R;

/**
 * Created by wonholee on 2015-08-20.
 */
public class BookFragment extends Fragment {

    final static String CONTENT_ID = "CONTENT_ID";

    private int contentId;

    private BookContentsData mBookContentsData;

    private ProgressDialog taskProgressDia;

    public static BookFragment newInstance(int contentId) {

        BookFragment bookFragment = new BookFragment();
        Bundle args = new Bundle();
        args.putInt(CONTENT_ID, contentId);
        bookFragment.setArguments(args);

        return bookFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View bookFragmentView = inflater.inflate(R.layout.fragment_book_content, container, false);

        new BookTask().execute("http://lwh777.dothome.co.kr/api/books/contents/" + contentId);

        return bookFragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        contentId = getArguments().getInt(CONTENT_ID);
    }

    private class BookTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}
