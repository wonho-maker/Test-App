package com.won.testapp.book.content.table;

import java.util.List;

/**
 * Created by wonholee on 2015-08-20.
 */
public class ContentTablesData {

    private int bookId;
    private String author;
    private String title;

    private List<ContentTableData> contentTable;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContentTableData> getContentTable() {
        return contentTable;
    }

    public void setContentTable(List<ContentTableData> contentTable) {
        this.contentTable = contentTable;
    }
}
