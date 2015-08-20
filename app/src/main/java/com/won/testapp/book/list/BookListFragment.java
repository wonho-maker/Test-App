package com.won.testapp.book.list;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.won.testapp.MainActivity;
import com.won.testapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonholee on 2015-08-20.
 */
public class BookListFragment extends Fragment {

    public static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    public final static String SUB_CATEGORY_ID = "SUB_CATEGORY_ID";

    private RecyclerView mRecyclerView;

    private BookListAdapter mBookListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<BookListData> mBookListData;

    private BooksData mBooksData;

    private ProgressDialog taskProgressDia;

    private int subCategoryId;

    public static BookListFragment newInstance(int sectionNumber, int subCategoryId) {

        Bundle args = new Bundle();

        BookListFragment fragment = new BookListFragment();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putInt(SUB_CATEGORY_ID, subCategoryId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskProgressDia = new ProgressDialog(getActivity());

        mBookListData = new ArrayList<>();

        mBooksData = new BooksData();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View bookFragmentView = inflater.inflate(R.layout.fragment_book_list, container, false);

        mRecyclerView = (RecyclerView) bookFragmentView.findViewById(R.id.book_list_container_recycler);

        mBookListAdapter = new BookListAdapter(mBookListData);

        mBookListAdapter.setmBookListClickListener((MainActivity) getActivity());

        mRecyclerView.setAdapter(mBookListAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        new BookTask().execute("http://lwh777.dothome.co.kr/api/categories/subs/" + subCategoryId + "/books");

        return bookFragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        subCategoryId = getArguments().getInt(SUB_CATEGORY_ID);

        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }


    private class BookTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            taskProgressDia.setTitle("loading...");
            taskProgressDia.setCancelable(false);
            taskProgressDia.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder sb = new StringBuilder();

            HttpClient httpClient = new DefaultHttpClient();

            String result = "";

            try {

                HttpGet httpGet = new HttpGet(urls[0]);

                HttpResponse response = httpClient.execute(httpGet);

                BufferedReader bufReader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent(), "utf-8"));

                String line = null;

                while(((line = bufReader.readLine()) != null)) {
                    result += line;
                }

            } catch(Exception e) {

                httpClient.getConnectionManager().shutdown();
            }

            result = result.replace("<meta charset=\"utf-8\">", "");

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("result", result);
            mBooksData = new Gson().fromJson(result, BooksData.class);

            mBookListData = mBooksData.getBooks();

            //Log.d("data", mCategoryListData.get(0).toString());

            mBookListAdapter.setmBookListData(mBookListData);

            mRecyclerView.getAdapter().notifyItemRangeChanged(0, mBookListData.size());

            if(taskProgressDia != null && taskProgressDia.isShowing()) {
                taskProgressDia.dismiss();
            }
        }


    }
}
