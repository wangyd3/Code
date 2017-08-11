package com.wangyd.firstcode.ch06.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by wangyd on 2017/6/14.
 */

public class Category extends DataSupport {
    private int id;
    private String categoryName;
    private int categoryCode;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
