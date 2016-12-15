package org.numisoft.usquarters.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.models.Coin;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kukolka on 10/16/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public DBHelper(Context context) {
        super(context, "coins", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        List<Coin> coins = parseJson();
        createCatalog(db, coins);
        createCollection(db, coins);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<Coin> coins = parseJson();
        createCatalog(db, coins);
        List<String> delta = getDelta(db);
        updateCollection(db, delta);
    }

    public List<String> getDelta(SQLiteDatabase db) {
        String query = "SELECT coinId FROM catalog EXCEPT SELECT coinId FROM collection";
        Cursor cursor = db.rawQuery(query, new String[]{});

        List<String> delta = new ArrayList<>();
        if (!cursor.moveToFirst()) return delta;

        do {
            delta.add(cursor.getString(cursor.getColumnIndex("coinId")));
        } while (cursor.moveToNext());

        cursor.close();
        return delta;
    }

    private void updateCollection(SQLiteDatabase db, List<String> delta) {
        for (String d : delta) {
            ContentValues cv3 = new ContentValues();
            cv3.put("coinId", d);
            cv3.put("unc", 0);
            cv3.put("aunc", 0);
            cv3.put("fine", 0);
            cv3.put("good", 0);
            db.insert("collection", null, cv3);
        }
    }


    private void createCatalog(SQLiteDatabase db, List<Coin> coins) {
        db.execSQL("DROP TABLE IF EXISTS catalog");
        db.execSQL("CREATE TABLE catalog (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, fullname TEXT, mintage TEXT, year INTEGER, " +
                "imageId TEXT, coinId TEXT, parentId TEXT, mark TEXT, " +
                "theme TEXT, description TEXT, remark TEXT)");

        for (Coin c : coins) {
            ContentValues cv = new ContentValues();
            cv.put("name", c.getName());
            cv.put("fullname", c.getFullname());
            cv.put("description", c.getDescription());
            cv.put("mintage", c.getMintage());
            cv.put("mark", c.getMark());
            cv.put("remark", c.getRemark());
            cv.put("year", c.getYear());
            cv.put("imageId", c.getImageId());
            cv.put("coinId", c.getCoinId());
            cv.put("parentId", c.getParentId());
            cv.put("theme", c.getTheme());
            db.insert("catalog", null, cv);
        }
    }

    private void createCollection(SQLiteDatabase db, List<Coin> coins) {
        db.execSQL("DROP TABLE IF EXISTS collection");
        db.execSQL("CREATE TABLE collection (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "coinId TEXT, unc INTEGER, aunc INTEGER, fine INTEGER, good INTEGER)");

        for (Coin c : coins) {
            ContentValues cv2 = new ContentValues();
            cv2.put("coinId", c.getCoinId());
            cv2.put("unc", 0);
            cv2.put("aunc", 0);
            cv2.put("fine", 0);
            cv2.put("good", 0);
            db.insert("collection", null, cv2);
        }
    }

    private List<Coin> parseJson() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Coin>>() {
        }.getType();
        return gson.fromJson(JSONConverter.convert(context,
                context.getResources().getString(R.string.json)), listType);
    }
}
