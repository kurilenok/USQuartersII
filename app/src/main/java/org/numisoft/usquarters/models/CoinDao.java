package org.numisoft.usquarters.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.numisoft.usquarters.utils.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kukolka on 14.08.16.
 */
public class CoinDao {

    private Context context;

    private final String SELECT = "SELECT name, fullname, description, year, imageId, parentId, " +
            "catalog.coinId, unc, aunc, fine, good, mintage, mark, remark ";

    public CoinDao(Context context) {
        this.context = context;
    }

    public List<Coin> getAllCoins(Theme theme1, Theme theme2) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT);
        query.append("FROM catalog ");
        query.append("LEFT OUTER JOIN collection ON catalog.coinId = collection.coinId ");
        query.append("WHERE (theme = '");
        query.append(theme1.value);
        query.append("' OR theme ='");
        query.append(theme2.value);
        query.append("') ORDER BY catalog.coinId DESC");

        return getSomeCoins(query.toString());
    }

    public List<Coin> getNeedCoins(Theme theme1, Theme theme2) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT);
        query.append("FROM catalog ");
        query.append("LEFT OUTER JOIN collection ON catalog.coinId = collection.coinId ");
        query.append("WHERE (theme = '");
        query.append(theme1.value);
        query.append("' OR theme ='");
        query.append(theme2.value);
        query.append("') AND (unc + aunc + fine + good) = 0");
        query.append(" ORDER BY catalog.coinId DESC");

        return getSomeCoins(query.toString());
    }


    public List<Coin> getSwapCoins(Theme theme1, Theme theme2) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT);
        query.append("FROM catalog ");
        query.append("LEFT OUTER JOIN collection ON catalog.coinId = collection.coinId ");
        query.append("WHERE (theme = '");
        query.append(theme1.value);
        query.append("' OR theme ='");
        query.append(theme2.value);
        query.append("') AND (unc + aunc + fine + good) > 1");
        query.append(" ORDER BY catalog.coinId DESC");

        return getSomeCoins(query.toString());
    }

    public List<Coin> getNotUncCoins(Theme theme1, Theme theme2) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT);
        query.append("FROM catalog ");
        query.append("LEFT OUTER JOIN collection ON catalog.coinId = collection.coinId ");
        query.append("WHERE (theme = '");
        query.append(theme1.value);
        query.append("' OR theme ='");
        query.append(theme2.value);
        query.append("') AND (aunc + fine + good) > 0");
        query.append(" AND unc = 0");
        query.append(" ORDER BY catalog.coinId DESC");

        return getSomeCoins(query.toString());
    }

    public List<Coin> getSomeCoins(String query) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{});

        List<Coin> coins = new ArrayList<>();
        if (!cursor.moveToFirst()) return coins;

        do {
            Coin coin = new Coin();
            coin.setName(cursor.getString(cursor.getColumnIndex("name")));
            coin.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
            coin.setYear(cursor.getInt(cursor.getColumnIndex("year")));
            coin.setUnc(cursor.getInt(cursor.getColumnIndex("unc")));
            coin.setAUnc(cursor.getInt(cursor.getColumnIndex("aunc")));
            coin.setFine(cursor.getInt(cursor.getColumnIndex("fine")));
            coin.setGood(cursor.getInt(cursor.getColumnIndex("good")));
            coin.setImageId(cursor.getString(cursor.getColumnIndex("imageId")));
            coin.setCoinId(cursor.getString(cursor.getColumnIndex("coinId")));
            coin.setParentId(cursor.getString(cursor.getColumnIndex("parentId")));
            coin.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            coin.setMintage(cursor.getString(cursor.getColumnIndex("mintage")));
            coin.setMark(cursor.getString(cursor.getColumnIndex("mark")));
            coin.setRemark(cursor.getString(cursor.getColumnIndex("remark")));
            coins.add(coin);
        } while (cursor.moveToNext());

        cursor.close();
        db.close();
        dbHelper.close();

        return coins;
    }

    public void updateAndPropagate(Coin coin) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (coin.getCoinId().equalsIgnoreCase(coin.getImageId())) {
            propagateToChildren(db, coin);
        } else {
            propagateToParent(db, coin);
        }

        updateCoin(db, coin);

        db.close();
        dbHelper.close();
    }

    private void propagateToChildren(SQLiteDatabase db, Coin coin) {
        int[] delta = getDelta(db, coin);
        Map<String, Coin> children = getChildren(db, coin);

        Coin childP = children.get("P");
        Coin childD = children.get("D");

        if (childP.getUnc() + delta[0] >= 0) {
            childP.setUnc(childP.getUnc() + delta[0]);
        } else {
            childD.setUnc(childD.getUnc() + delta[0]);
        }

        if (childP.getAUnc() + delta[1] >= 0) {
            childP.setAUnc(childP.getAUnc() + delta[1]);
        } else {
            childD.setAUnc(childD.getAUnc() + delta[1]);
        }

        if (childP.getFine() + delta[2] >= 0) {
            childP.setFine(childP.getFine() + delta[2]);
        } else {
            childD.setFine(childD.getFine() + delta[2]);
        }

        if (childP.getGood() + delta[3] >= 0) {
            childP.setGood(childP.getGood() + delta[3]);
        } else {
            childD.setGood(childD.getGood() + delta[3]);
        }

        updateCoin(db, children.get("P"));
        updateCoin(db, children.get("D"));
    }

    private void propagateToParent(SQLiteDatabase db, Coin coin) {
        int[] delta = getDelta(db, coin);

        Coin parent = getParent(db, coin);
        parent.setUnc(parent.getUnc() + delta[0]);
        parent.setAUnc(parent.getAUnc() + delta[1]);
        parent.setFine(parent.getFine() + delta[2]);
        parent.setGood(parent.getGood() + delta[3]);

        updateCoin(db, parent);
    }

    private int[] getDelta(SQLiteDatabase db, Coin coinNew) {
        Coin coinOld = getCoinDataById(db, coinNew.getCoinId());
        int[] delta = new int[4];
        delta[0] = coinNew.getUnc() - coinOld.getUnc();
        delta[1] = coinNew.getAUnc() - coinOld.getAUnc();
        delta[2] = coinNew.getFine() - coinOld.getFine();
        delta[3] = coinNew.getGood() - coinOld.getGood();
        return delta;
    }

    private Coin getParent(SQLiteDatabase db, Coin coin) {
        String coinId = coin.getParentId();
        Coin parent = getCoinDataById(db, coinId);
        return parent;
    }

    private Coin getCoinDataById(SQLiteDatabase db, String coinId) {
        String query = "SELECT * FROM collection WHERE coinId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{coinId});
        cursor.moveToFirst();

        Coin coin = new Coin();
        coin.setCoinId(coinId);
        coin.setUnc(cursor.getInt(cursor.getColumnIndex("unc")));
        coin.setAUnc(cursor.getInt(cursor.getColumnIndex("aunc")));
        coin.setFine(cursor.getInt(cursor.getColumnIndex("fine")));
        coin.setGood(cursor.getInt(cursor.getColumnIndex("good")));

        cursor.close();
        return coin;
    }

    private Map<String, Coin> getChildren(SQLiteDatabase db, Coin parent) {
        Map<String, Coin> children = new HashMap<>();

        String parentId = parent.getCoinId();

        String childPId = parentId.substring(0, parentId.length() - 1) + "7";
        String childDId = parentId.substring(0, parentId.length() - 1) + "5";

        Coin childP = getCoinDataById(db, childPId);
        Coin childD = getCoinDataById(db, childDId);

        children.put("P", childP);
        children.put("D", childD);
        return children;
    }

    private void updateCoin(SQLiteDatabase db, Coin coin) {
        db.execSQL(
                "UPDATE collection SET unc = ?, aunc = ?, fine = ?, good = ? WHERE coinId = ?",
                new Object[]{coin.getUnc(), coin.getAUnc(), coin.getFine(), coin.getGood(),
                        coin.getCoinId()});
    }


}
