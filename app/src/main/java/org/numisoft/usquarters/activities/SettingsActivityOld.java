package org.numisoft.usquarters.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.utils.Constants;

/**
 * Created by kukolka on 11/13/2016.
 */

public class SettingsActivityOld extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener, Constants {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.the_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

// RadioGroup
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgViewMode);
        radioGroup.setOnCheckedChangeListener(this);
        preferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        preselectRadioButton();

    }

    private void preselectRadioButton() {
        int viewMode = preferences.getInt(VIEW_MODE, 1);
        switch (viewMode) {
            case 0:
                RadioButton rbMode0 = (RadioButton) findViewById(R.id.rbMode0);
                rbMode0.setChecked(true);
                break;
            case 1:
                RadioButton rbMode1 = (RadioButton) findViewById(R.id.rbMode1);
                rbMode1.setChecked(true);
                break;
            case 2:
                RadioButton rbMode2 = (RadioButton) findViewById(R.id.rbMode2);
                rbMode2.setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                // Create a simple intent that starts the hierarchical parent activity and
                // use NavUtils in the Support Package to ensure proper handling of Up.
                Intent upIntent = new Intent(this, MainActivity.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is not part of the application's task, so create a new task
                    // with a synthesized back stack.
                    TaskStackBuilder.from(this)
                            // If there are ancestor activities, they should be added here.
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    // This activity is part of the application's task, so simply
                    // navigate up to the hierarchical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbMode0:
                preferences.edit().putInt(VIEW_MODE, 0).apply();
                break;
            case R.id.rbMode1:
                preferences.edit().putInt(VIEW_MODE, 1).apply();
                break;
            case R.id.rbMode2:
                preferences.edit().putInt(VIEW_MODE, 2).apply();
                break;
        }
    }
}
