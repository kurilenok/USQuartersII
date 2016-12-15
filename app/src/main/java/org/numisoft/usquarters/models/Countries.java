package org.numisoft.usquarters.models;

import android.content.Context;

import org.numisoft.usquarters.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kukolka on 14.08.16.
 */
public class Countries {

    Context context;

    public Countries(Context context) {
        this.context = context;
    }

    public List<Country> getCountries() {
        List<Country> countries = new ArrayList<>();

        countries.add(new Country(R.drawable.de, "Germany"));
        countries.add(new Country(R.drawable.fr, "France"));
        countries.add(new Country(R.drawable.it, "Italy"));
        countries.add(new Country(R.drawable.es, "Spain"));
        countries.add(new Country(R.drawable.nl, "Netherlands"));
        countries.add(new Country(R.drawable.be, "Belgium"));
        countries.add(new Country(R.drawable.at, "Austria"));
        countries.add(new Country(R.drawable.fi, "Finland"));
        countries.add(new Country(R.drawable.pt, "Portugal"));
        countries.add(new Country(R.drawable.gr, "Greece"));
        countries.add(new Country(R.drawable.sk, "Slovakia"));
        countries.add(new Country(R.drawable.si, "Slovenia"));
        countries.add(new Country(R.drawable.lt, "Lithuania"));
        countries.add(new Country(R.drawable.lv, "Latvia"));
        countries.add(new Country(R.drawable.ee, "Estonia"));
        countries.add(new Country(R.drawable.cy, "Cyprus"));
        countries.add(new Country(R.drawable.mt, "Malta"));
        countries.add(new Country(R.drawable.mc, "Monaco"));


        return countries;
    }

    class Country {

        int flag;
        String title;

        public Country(int flag, String title) {
            this.flag = flag;
            this.title = title;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
