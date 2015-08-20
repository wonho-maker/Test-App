package com.won.testapp.category;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
 * Created by wonholee on 2015-08-19.
 */
public class CategoryFragment extends Fragment {

    final static String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";

    private RecyclerView mRecyclerView;
    private CategoryListAdapter mCategoryListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<CategoryListData> mCategoryListData;

    private CategoriesData mCategoriesData;

    private ProgressDialog taskProgressDia;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance(int sectionNumber) {
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        categoryFragment.setArguments(args);

        return categoryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        taskProgressDia = new ProgressDialog(getActivity());

        mCategoryListData = new ArrayList<>();

        mCategoriesData = new CategoriesData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View categoryFragmentView = inflater.inflate(R.layout.fragment_category_list, container, false);

        mRecyclerView = (RecyclerView) categoryFragmentView.findViewById(R.id.category_list_container_recycler);

        mCategoryListAdapter = new CategoryListAdapter(mCategoryListData, CategoryListAdapter.CATEGORY);

        mCategoryListAdapter.setmCategoryListClickListener((MainActivity)getActivity());

        mRecyclerView.setAdapter(mCategoryListAdapter);

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.getAdapter().notifyItemRangeInserted(0, mCategoryListData.size());


        new CategoryTask().execute("http://lwh777.dothome.co.kr/api/categories");

        return categoryFragmentView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private class CategoryTask extends AsyncTask<String, Void, String> {

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
            mCategoriesData = new Gson().fromJson(result, CategoriesData.class);

            mCategoryListData = mCategoriesData.getCategoriesData();

            //Log.d("data", mCategoryListData.get(0).toString());

            mCategoryListAdapter.setmCategoryListData(mCategoryListData);

            mRecyclerView.getAdapter().notifyItemRangeChanged(0, mCategoryListData.size());

            if(taskProgressDia != null && taskProgressDia.isShowing()) {
                taskProgressDia.dismiss();
            }
        }
    }

}
