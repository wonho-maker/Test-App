package com.won.testapp;

import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.won.testapp.book.content.table.ContentTableClickListener;
import com.won.testapp.book.content.table.ContentTableFragment;
import com.won.testapp.book.list.BookListClickListener;
import com.won.testapp.book.list.BookListFragment;
import com.won.testapp.category.CategoryFragment;
import com.won.testapp.category.CategoryListClickListener;
import com.won.testapp.category.sub.SubCategoryFragment;
import com.won.testapp.category.sub.SubCategoryListClickListener;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        CategoryListClickListener, SubCategoryListClickListener, BookListClickListener, ContentTableClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int sectionNumber;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 1:
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                CategoryFragment categoryFragment = CategoryFragment.newInstance(position + 1);

                fragmentManager.beginTransaction()
                        .replace(R.id.container, categoryFragment, "categoryFragment")
                        .commit();
                break;

        }


    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void onSectionAttached(int number) {
        sectionNumber = number;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void onSectionAttached(String title) {

        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void categoryListClick(int categoryId) {
        Log.d("cateList", ""+ categoryId);

        FragmentManager fragmentManager = getSupportFragmentManager();


        SubCategoryFragment subCategoryFragment = (SubCategoryFragment) fragmentManager.findFragmentByTag("subCategoryFragment");

        if( subCategoryFragment == null) {

            subCategoryFragment = SubCategoryFragment.newInstance(categoryId);

            fragmentManager.beginTransaction()
                    .addToBackStack("categoryFragment")
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.container, subCategoryFragment, "subCategoryFragment")
                    .commit();
        }

    }

    @Override
    public void subCategoryListClick(int categoryId) {
        Log.d("subCateList", ""+ categoryId);

        FragmentManager fragmentManager = getSupportFragmentManager();


        BookListFragment bookListFragment = (BookListFragment) fragmentManager.findFragmentByTag("bookListFragment");

        if( bookListFragment == null) {

            bookListFragment = BookListFragment.newInstance(sectionNumber, categoryId);

            fragmentManager.beginTransaction()
                    .addToBackStack("subCategoryFragment")
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.container, bookListFragment, "bookListFragment")
                    .commit();
        }
    }

    @Override
    public void bookListClick(int bookId) {
        Log.d("bookList", ""+bookId);

        FragmentManager fragmentManager = getSupportFragmentManager();


        ContentTableFragment contentTableFragment = (ContentTableFragment) fragmentManager.findFragmentByTag("contentTableFragment");

        if( contentTableFragment == null) {

            contentTableFragment = ContentTableFragment.newInstance(bookId);

            fragmentManager.beginTransaction()
                    .addToBackStack("bookListFragment")
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.container, contentTableFragment, "contentTableFragment")
                    .commit();
        }
    }


    @Override
    public void contentTableClick(int contentTableId) {

    }
}
