package org.numisoft.usquarters.fragments;

import android.annotation.SuppressLint;

import org.numisoft.usquarters.models.Theme;

/**
 * Created by kukolka on 22.08.16.
 */
@SuppressLint("ValidFragment")
public class AllFragment extends BasicFragment {

    public AllFragment(Theme theme1, Theme theme2) {
        super(theme1, theme2);
    }

    public AllFragment() {
        super();
    }
}