package org.numisoft.usquarters.fragments;


import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.models.Coin;
import org.numisoft.usquarters.models.CoinDao;

import static android.content.Context.MODE_PRIVATE;
import static org.numisoft.usquarters.utils.Constants.SHARED_PREF;
import static org.numisoft.usquarters.utils.Constants.VIEW_MODE;

/**
 * Created by kukolka on 10/31/2016.
 */

public class PopupFragment extends DialogFragment implements View.OnClickListener {

    private Coin coin;
    TextView tvUnc, tvAUnc, tvFine, tvGood;
    Button bDeleteUnc, bDeleteAUnc, bDeleteFine, bDeleteGood;
    SharedPreferences preferences;

    private static PopupFragment instance = null;

    public PopupFragment() {
    }

    public static PopupFragment getInstance(Coin coin) {
        if (instance == null)
            instance = new PopupFragment();
        Bundle args = new Bundle();
        args.putSerializable("coin", coin);
        instance.setArguments(args);
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        coin = (Coin) getArguments().getSerializable("coin");

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));

        View view = inflater.inflate(R.layout.popup, container);

        CardView cv = (CardView) view.findViewById(R.id.cvItem);
        cv.setClipToOutline(true);

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        TextView tvMintage = (TextView) view.findViewById(R.id.tvMintage);
        TextView tvMark = (TextView) view.findViewById(R.id.tvMark);
        ImageView ivCoin = (ImageView) view.findViewById(R.id.ivCoin);

        tvUnc = (TextView) view.findViewById(R.id.tvUnc);
        tvAUnc = (TextView) view.findViewById(R.id.tvAUnc);
        tvFine = (TextView) view.findViewById(R.id.tvFine);
        tvGood = (TextView) view.findViewById(R.id.tvGood);

        Button bAddUnc = (Button) view.findViewById(R.id.bAddUnc);
        Button bAddAUnc = (Button) view.findViewById(R.id.bAddAUnc);
        Button bAddFine = (Button) view.findViewById(R.id.bAddFine);
        Button bAddGood = (Button) view.findViewById(R.id.bAddGood);

        bDeleteUnc = (Button) view.findViewById(R.id.bDeleteUnc);
        bDeleteAUnc = (Button) view.findViewById(R.id.bDeleteAUnc);
        bDeleteFine = (Button) view.findViewById(R.id.bDeleteFine);
        bDeleteGood = (Button) view.findViewById(R.id.bDeleteGood);

        Button bUncClose = (Button) view.findViewById(R.id.bUncClose);
        bUncClose.setOnClickListener(this);

        tvName.setText(coin.getFullname());
        tvDescription.setText(coin.getDescription());

        if (coin.getRemark() != null && coin.getRemark().equalsIgnoreCase("2017")) {
            tvMintage.setText("COMING SOON: " + coin.getMintage() + " ");
        } else {
            tvMintage.setText("MINTAGE: " + coin.getMintage() + " ");
        }

        preferences = getActivity().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        if (preferences.getInt(VIEW_MODE, 1) == 0) {
            RelativeLayout rlItem = (RelativeLayout) view.findViewById(R.id.rlItem);
            rlItem.removeView(tvMark);
        } else {
            tvMark.setText(coin.getMark());
        }

        ivCoin.setImageResource(getResources().getIdentifier(coin.getImageId(),
                "drawable", getContext().getPackageName()));

        tvAUnc.setText(Integer.toString(coin.getAUnc()));
        bAddAUnc.setOnClickListener(this);
        bDeleteAUnc.setOnClickListener(this);
        if (coin.getAUnc() == 0) bDeleteAUnc.setEnabled(false);

        tvUnc.setText(Integer.toString(coin.getUnc()));
        bAddUnc.setOnClickListener(this);
        bDeleteUnc.setOnClickListener(this);
        if (coin.getUnc() == 0) bDeleteUnc.setEnabled(false);

        tvFine.setText(Integer.toString(coin.getFine()));
        bAddFine.setOnClickListener(this);
        bDeleteFine.setOnClickListener(this);
        if (coin.getFine() == 0) bDeleteFine.setEnabled(false);

        tvGood.setText(Integer.toString(coin.getGood()));
        bAddGood.setOnClickListener(this);
        bDeleteGood.setOnClickListener(this);
        if (coin.getGood() == 0) bDeleteGood.setEnabled(false);

        return view;
    }

    @Override
    public void onClick(View view) {

        CoinDao coinDao = new CoinDao(getActivity().getBaseContext());
        BasicFragment basicFragment = (BasicFragment) getTargetFragment();
        int currentUnc = coin.getUnc();
        int currentAUnc = coin.getAUnc();
        int currentFine = coin.getFine();
        int currentGood = coin.getGood();
//        Operation operation = null;

        switch (view.getId()) {
            case R.id.bUncClose:
                coin.setUnc(++currentUnc);
//                operation = Operation.ADD_UNC;
                this.dismiss();
                break;
            case R.id.bAddUnc:
                coin.setUnc(++currentUnc);
                bDeleteUnc.setEnabled(true);
                break;
            case R.id.bDeleteUnc:
                coin.setUnc(--currentUnc);
                if (coin.getUnc() == 0) bDeleteUnc.setEnabled(false);
                break;
            case R.id.bAddAUnc:
                coin.setAUnc(++currentAUnc);
                bDeleteAUnc.setEnabled(true);
                break;
            case R.id.bDeleteAUnc:
                coin.setAUnc(--currentAUnc);
                if (coin.getAUnc() == 0) bDeleteAUnc.setEnabled(false);
                break;
            case R.id.bAddFine:
                coin.setFine(++currentFine);
                bDeleteFine.setEnabled(true);
                break;
            case R.id.bDeleteFine:
                coin.setFine(--currentFine);
                if (coin.getFine() == 0) bDeleteFine.setEnabled(false);
                break;
            case R.id.bAddGood:
                coin.setGood(++currentGood);
                bDeleteGood.setEnabled(true);
                break;
            case R.id.bDeleteGood:
                coin.setGood(--currentGood);
                if (coin.getGood() == 0) bDeleteGood.setEnabled(false);
                break;
        }

        tvUnc.setText(Integer.toString(coin.getUnc()));
        tvAUnc.setText(Integer.toString(coin.getAUnc()));
        tvFine.setText(Integer.toString(coin.getFine()));
        tvGood.setText(Integer.toString(coin.getGood()));

        coinDao.updateAndPropagate(coin);
        basicFragment.doSomething(coin);
    }

}
