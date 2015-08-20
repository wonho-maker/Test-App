package com.won.testapp.book.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wonholee on 2015-08-20.
 */
public class BooksData {

    private List<BookListData> books = new ArrayList<>();

    public List<BookListData> getBooks() {
        return books;
    }

    public void setBooks(List<BookListData> books) {
        this.books = books;
    }
}
