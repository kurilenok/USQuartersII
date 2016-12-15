package org.numisoft.usquarters.models;

import org.numisoft.usquarters.utils.Constants;

/**
 * Created by kukolka on 10/18/2016.
 */

public enum Theme {

    STATES ("Statehood Quarters"),
    STATES_P ("Statehood Quarters (P)"),
    STATES_D ("Statehood Quarters (D)"),
    PARKS ("America the Beautiful"),
    PARKS_P ("America the Beautiful (P)"),
    PARKS_D ("America the Beautiful (D)"),
    PRESIDENTS ("Presidential Dollars"),
    PRESIDENTS_P ("Presidential Dollars (P)"),
    PRESIDENTS_D ("Presidential Dollars (D)"),
    SACAGAWEA ("Native American Dollars"),
    SACAGAWEA_P ("Native American Dollars (P)"),
    SACAGAWEA_D ("Native American Dollars (D)");

    final String value;

    Theme(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
