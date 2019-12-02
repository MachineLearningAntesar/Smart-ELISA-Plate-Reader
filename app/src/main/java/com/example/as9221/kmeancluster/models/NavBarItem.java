package com.example.as9221.kmeancluster.models;

import com.example.as9221.kmeancluster.R;

/**
 * Created by albertlo on 7/10/17.
 */

public enum NavBarItem {
    HELP(R.id.help),
    ABOUTUS(R.id.about_us),
    TERMS(R.id.user_terms),
    CHECKSYMPTOM(R.id.symptom),
    /*PROFESSIONAL(R.id.professional)*/;


    private int itemId;
    NavBarItem(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }

    public static NavBarItem fromViewId(int viewId) {
        for(NavBarItem navBarItem : NavBarItem.values()) {
            if (navBarItem.getItemId() == viewId) {
                return navBarItem;
            }
        }
        throw new IllegalStateException("Cannot find viewType");
    }
}
