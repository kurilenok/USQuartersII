package org.numisoft.usquarters.fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.adapters.RecycleViewAdapter;
import org.numisoft.usquarters.models.Coin;
import org.numisoft.usquarters.models.Theme;
import org.numisoft.usquarters.utils.UpdateHelper;

/**
 * Created by kukolka on 22.08.16.
 */
@SuppressLint("ValidFragment")
public class BasicFragment extends Fragment implements RecycleViewAdapter.OnDataClickListener {

    Theme theme1;
    Theme theme2;
    View view;
    RecyclerView rvMain;
    RecycleViewAdapter rvAdapter;
    int clicked;
    RecyclerView.LayoutManager layoutManager;

    public BasicFragment() {
    }

    public BasicFragment(Theme theme1, Theme theme2) {
        this.theme1 = theme1;
        this.theme2 = theme2;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.all_fragment, container, false);
        setRetainInstance(true);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        layoutManager = new GridLayoutManager(view.getContext(), (int) (dpWidth / 160));

        rvMain = (RecyclerView) view.findViewById(R.id.rvMain);
        rvMain.setLayoutManager(layoutManager);

        setNewAdapter();

        return view;
    }

    private void setNewAdapter() {
        rvAdapter = new RecycleViewAdapter(view.getContext(), this);
        rvMain.setAdapter(rvAdapter);
    }

    public void doSomething(Coin coin) {
        rvAdapter.getCoins().set(clicked, coin);
        rvAdapter.notifyItemChanged(clicked);
        rvAdapter.notifyDataSetChanged();
        UpdateHelper.setNeedsUpdate(true);
    }

    @Override
    public void onDataClick(Coin coin, int position) {
        this.clicked = position;
        FragmentManager manager = getActivity().getSupportFragmentManager();
        PopupFragment popup = PopupFragment.getInstance(coin);
        popup.setTargetFragment(this, 0);
        popup.show(manager, "1");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            if (isVisibleToUser & UpdateHelper.isNeedsUpdate()) {
            try {
                setNewAdapter();
                UpdateHelper.setNeedsUpdate(false);
            } catch (Exception e) {
            }
        }
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        layoutManager = new GridLayoutManager(view.getContext(), (int) (dpWidth / 160));
        rvMain.setLayoutManager(layoutManager);
    }

    public Theme getTheme1() {
        return theme1;
    }

    public void setTheme1(Theme theme1) {
        this.theme1 = theme1;
    }

    public Theme getTheme2() {
        return theme2;
    }

    public void setTheme2(Theme theme2) {
        this.theme2 = theme2;
    }
}
