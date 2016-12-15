package org.numisoft.usquarters.fragments;

import android.annotation.SuppressLint;

import org.numisoft.usquarters.models.Theme;

/**
 * Created by kukolka on 10/18/2016.
 */
@SuppressLint("ValidFragment")
public class SwapFragment extends BasicFragment {

    public SwapFragment(Theme theme1, Theme theme2) {
        super(theme1, theme2);
    }

    public SwapFragment() {
        super();
    }
}
