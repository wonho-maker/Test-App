package com.won.testapp.book.content.table;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class ContentTableFragment extends Fragment{

    final static String BOOK_ID = "BOOK_ID";

    private RecyclerView mRecyclerView;
    private ContentTableAdapter mContentTableAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    private List<ContentTableData> mContentTableData;

    private ContentTablesData mContentTablesData;

    private ProgressDialog taskProgressDia;

    private int bookId;

    public ContentTableFragment() {

    }

    public static ContentTableFragment newInstance(int bookId) {
        ContentTableFragment contentTableFragment = new ContentTableFragment();
        Bundle args = new Bundle();
        args.putInt(BOOK_ID, bookId);
        contentTableFragment.setArguments(args);

        return contentTableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskProgressDia = new ProgressDialog(getActivity());

        mContentTableData = new ArrayList<>();

        mContentTablesData = new ContentTablesData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View categoryFragmentView = inflater.inflate(R.layout.fragment_book_content_table, container, false);

        mRecyclerView = (RecyclerView) categoryFragmentView.findViewById(R.id.book_content_table_container_recycler);

        mContentTableAdapter = new ContentTableAdapter(mContentTableData);

        mContentTableAdapter.setmContentTableClickListener((MainActivity) getActivity());

        mRecyclerView.setAdapter(mContentTableAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        //mRecyclerView.getAdapter().notifyItemRangeInserted(0, mContentTableData.size());

        new ContentTableTask().execute("http://lwh777.dothome.co.kr/api/books/" + bookId );

        return categoryFragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        bookId = getArguments().getInt(BOOK_ID);


    }



    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("카테고리");
        super.onDestroy();


    }

    private class ContentTableTask extends AsyncTask<String, Void, String> {

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

            //Log.d("result", result);
            mContentTablesData = new Gson().fromJson(result, ContentTablesData.class);

            mContentTableData = mContentTablesData.getContentTable();

            //mContentTableData.get(0).toString();

            //Log.d("data", mContentTableData.get(0).toString());

            mContentTableAdapter.setmContentTableData(mContentTableData);

            mRecyclerView.getAdapter().notifyItemRangeChanged(0, mContentTableData.size());

            ((MainActivity) getActivity()).getSupportActionBar().setTitle(mContentTablesData.getTitle());

            if(taskProgressDia != null && taskProgressDia.isShowing()) {
                taskProgressDia.dismiss();
            }
        }
    }
}
