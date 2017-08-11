package com.wangyd.firstcode.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyd on 2017/8/10.
 */

public class MenuItem {

    private final String title;
    private List<Item> items = new ArrayList<>();
    private boolean isOpen = false;

    public MenuItem(String title, List<Item> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public static class Item {

        private final String name;
        private Class cls;

        public Item(String name, Class cls) {
            this.name = name;
            this.cls = cls;
        }

        public Class getCls() {
            return cls;
        }

        public String getName() {
            return name;
        }
    }

}
