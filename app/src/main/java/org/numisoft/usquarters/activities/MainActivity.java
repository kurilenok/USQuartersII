package org.numisoft.usquarters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.adapters.PageViewAdapter;
import org.numisoft.usquarters.models.Theme;
import org.numisoft.usquarters.utils.Constants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Constants {

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    PageViewAdapter pagerAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    SharedPreferences preferences;
    private Menu navigationViewMenu;

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        onCreate(savedInstanceState);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

// Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.the_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("America The Beautiful");
//        getSupportActionBar().setSubtitle("1999-2010");

// Tabs
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
//        tabLayout.addTab(tabLayout.newTab().setText("HAVE"));
        tabLayout.addTab(tabLayout.newTab().setText("NEED"));
        tabLayout.addTab(tabLayout.newTab().setText("SWAP"));
        tabLayout.addTab(tabLayout.newTab().setText("UNC-"));

// Pager
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_P);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

// Drawer
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

// Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.addHeaderView(View.inflate(this, R.layout.nav_header, null));
//        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

// Navigation menu transformation
        navigationViewMenu = navigationView.getMenu();
        int viewMode = preferences.getInt(VIEW_MODE, 1);
        switch (viewMode) {
            case 0:
                hideMenuItemsMode0(navigationViewMenu);
                break;
            case 1:
                hideMenuItemsMode1(navigationViewMenu);
                break;
            case 2:
                hideMenuItemsMode2(navigationViewMenu);
                break;
        }
    }

    private void hideMenuItemsMode0(Menu navigationViewMenu) {
        navigationViewMenu.findItem(R.id.nav_menu_states_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_states_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_states_p_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks_p_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_p_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_p_d).setVisible(false);

        navigationViewMenu.findItem(R.id.nav_menu_states).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_parks).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_presidents).setVisible(true);
    }

    private void hideMenuItemsMode1(Menu navigationViewMenu) {
        navigationViewMenu.findItem(R.id.nav_menu_states).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_states_p_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks_p_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_p_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_p_d).setVisible(false);

        navigationViewMenu.findItem(R.id.nav_menu_states_p).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_states_d).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_parks_p).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_parks_d).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_p).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_d).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_p).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_d).setVisible(true);

    }

    private void hideMenuItemsMode2(Menu navigationViewMenu) {
        navigationViewMenu.findItem(R.id.nav_menu_states).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_states_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_states_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_parks_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_d).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_p).setVisible(false);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_d).setVisible(false);

        navigationViewMenu.findItem(R.id.nav_menu_states_p_d).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_parks_p_d).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_sacagawea_p_d).setVisible(true);
        navigationViewMenu.findItem(R.id.nav_menu_presidents_p_d).setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.aux_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (item.getItemId() == R.id.menu_1) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        if (item.getItemId() == R.id.aux_menu_one_mint) {
            preferences.edit().putInt(VIEW_MODE, 1).apply();
            hideMenuItemsMode1(navigationViewMenu);
            item.setChecked(true);
            resetDisplay();
        }
        if (item.getItemId() == R.id.aux_menu_two_mints) {
            preferences.edit().putInt(VIEW_MODE, 2).apply();
            hideMenuItemsMode2(navigationViewMenu);
            item.setChecked(true);
            resetDisplay();
        }
        if (item.getItemId() == R.id.aux_menu_no_mints) {
            preferences.edit().putInt(VIEW_MODE, 0).apply();
            hideMenuItemsMode0(navigationViewMenu);
            item.setChecked(true);
            resetDisplay();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu_states:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.STATES, Theme.STATES);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.STATES.getValue());
                break;
            case R.id.nav_menu_states_p:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.STATES_P, Theme.STATES_P);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.STATES_P.getValue());
                break;
            case R.id.nav_menu_states_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.STATES_D, Theme.STATES_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.STATES_D.getValue());
                break;
            case R.id.nav_menu_states_p_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.STATES_P, Theme.STATES_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.STATES.getValue());
                break;

            case R.id.nav_menu_parks:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS, Theme.PARKS);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS.getValue());
                break;
            case R.id.nav_menu_parks_p:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_P);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS_P.getValue());
                break;
            case R.id.nav_menu_parks_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS_D, Theme.PARKS_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS_D.getValue());
                break;
            case R.id.nav_menu_parks_p_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS.getValue());
                break;
            case R.id.nav_menu_presidents:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PRESIDENTS, Theme.PRESIDENTS);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PRESIDENTS.getValue());
                break;
            case R.id.nav_menu_presidents_p:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PRESIDENTS_P, Theme.PRESIDENTS_P);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PRESIDENTS_P.getValue());
                break;
            case R.id.nav_menu_presidents_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PRESIDENTS_D, Theme.PRESIDENTS_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PRESIDENTS_D.getValue());
                break;
            case R.id.nav_menu_presidents_p_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PRESIDENTS_P, Theme.PRESIDENTS_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PRESIDENTS.getValue());
                break;

            case R.id.nav_menu_sacagawea:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.SACAGAWEA, Theme.SACAGAWEA);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.SACAGAWEA.getValue());
                break;
            case R.id.nav_menu_sacagawea_p:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.SACAGAWEA_P, Theme.SACAGAWEA_P);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.SACAGAWEA_P.getValue());
                break;
            case R.id.nav_menu_sacagawea_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.SACAGAWEA_D, Theme.SACAGAWEA_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.SACAGAWEA_D.getValue());
                break;
            case R.id.nav_menu_sacagawea_p_d:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.SACAGAWEA_P, Theme.SACAGAWEA_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.SACAGAWEA.getValue());
                break;
        }
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int viewMode = preferences.getInt(VIEW_MODE, 1);
        switch (viewMode) {
            case 0:
//                hideMenuItemsMode0(navigationViewMenu);
                menu.getItem(3).setChecked(true);
                break;
            case 1:
                hideMenuItemsMode1(navigationViewMenu);
                menu.getItem(1).setChecked(true);
                break;
            case 2:
                hideMenuItemsMode2(navigationViewMenu);
                menu.getItem(2).setChecked(true);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetDisplay();
    }

    private void resetDisplay() {
        int viewMode = preferences.getInt(VIEW_MODE, 1);
        switch (viewMode) {
            case 0:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS, Theme.PARKS);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS.getValue());
                break;
            case 1:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_P);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS_P.getValue());
                break;
            case 2:
                pagerAdapter = new PageViewAdapter(getSupportFragmentManager(),
                        tabLayout.getTabCount(), Theme.PARKS_P, Theme.PARKS_D);
                viewPager.setAdapter(pagerAdapter);
                getSupportActionBar().setTitle(Theme.PARKS.getValue());
                break;
        }
    }
}
