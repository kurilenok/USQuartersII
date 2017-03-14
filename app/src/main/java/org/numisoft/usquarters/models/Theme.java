package org.numisoft.usquarters.models;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.utils.App;

/**
 * Created by kukolka on 10/18/2016.
 */

public enum Theme {

    STATES (R.string.theme_states),
    STATES_P (R.string.theme_states_p),
    STATES_D (R.string.theme_states_d),
    PARKS (R.string.theme_parks),
    PARKS_P (R.string.theme_parks_p),
    PARKS_D (R.string.theme_parks_d),
    PRESIDENTS (R.string.theme_presidents),
    PRESIDENTS_P (R.string.theme_presidents_p),
    PRESIDENTS_D (R.string.theme_presidents_d),
    SACAGAWEA (R.string.theme_sacagawea),
    SACAGAWEA_P (R.string.theme_sacagawea_p),
    SACAGAWEA_D (R.string.theme_sacagawea_d);

    private int value;

    Theme(int value) {
        this.value = value;
    }

    public String getValue() {
        return App.getContext().getString(value);
    }
}
