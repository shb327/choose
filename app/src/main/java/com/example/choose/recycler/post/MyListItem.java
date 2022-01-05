package com.example.choose.recycler.post;

import java.util.ArrayList;

public class MyListItem{

    public MyListItem() { }

    public static ArrayList<MyListItem> createContactsList(int numContacts) {
        ArrayList<MyListItem> items = new ArrayList<MyListItem>();
        for (int i = 1; i <= numContacts; i++) {
            items.add(new MyListItem());
        }
        return items;
    }
}