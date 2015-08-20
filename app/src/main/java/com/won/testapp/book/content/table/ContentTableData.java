package com.won.testapp.book.content.table;

/**
 * Created by wonholee on 2015-08-20.
 */
public class ContentTableData {

    private int contentTableId;
    private int tableOrder;
    private int depth;
    private String heading;

    public int getContentTableId() {
        return contentTableId;
    }

    public void setContentTableId(int contentTableId) {
        this.contentTableId = contentTableId;
    }

    public int getTableOrder() {
        return tableOrder;
    }

    public void setTableOrder(int tableOrder) {
        this.tableOrder = tableOrder;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "ContentTableData{" +
                "contentTableId=" + contentTableId +
                ", tableOrder=" + tableOrder +
                ", depth=" + depth +
                ", heading='" + heading + '\'' +
                '}';
    }
}
