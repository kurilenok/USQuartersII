package org.numisoft.usquarters.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.numisoft.usquarters.fragments.AllFragment;
import org.numisoft.usquarters.fragments.NeedFragment;
import org.numisoft.usquarters.fragments.NotUncFragment;
import org.numisoft.usquarters.fragments.SwapFragment;
import org.numisoft.usquarters.models.Theme;

/**
 * Created by kukolka on 22.08.16.
 */
public class PageViewAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;
    Theme theme1;
    Theme theme2;

    public PageViewAdapter(FragmentManager fm, int numberOfTabs, Theme theme1, Theme theme2) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.theme1 = theme1;
        this.theme2 = theme2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllFragment(theme1, theme2);
            case 1:
                return new NeedFragment(theme1, theme2);
            case 2:
                return new SwapFragment(theme1, theme2);
            case 3:
                return new NotUncFragment(theme1, theme2);
            default:
                return new AllFragment(theme1, theme2);
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

}
