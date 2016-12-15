package org.numisoft.usquarters.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.numisoft.usquarters.R;
import org.numisoft.usquarters.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by kukolka on 11/13/2016.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvCurrentDBDate;
    TextView tvCurrentDBUrl;
    TextView tvBackupDBDate;
    TextView tvBackupDBUrl;
    Button bSave;
    Button bRestore;

    String CURRENT_URL = Environment.getDataDirectory() + "/data/org.numisoft.usquarters/databases/";
    String CURRENT_DB = CURRENT_URL + "coins";
    String CURRENT_DBJ = CURRENT_URL + "coins-journal";
    String BACKUP_URL = Environment.getExternalStorageDirectory() + "/Numisoft/MyUSACoins/";
    String BACKUP_DB = BACKUP_URL + "coins";
    String BACKUP_DBJ = BACKUP_URL + "coins-journal";

    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //reload my activity with permission granted or use the features what required the permission
                } else {
                    Toast.makeText(this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setActionBar();

        tvCurrentDBDate = (TextView) findViewById(R.id.tvCurrentDBDate);
        tvCurrentDBUrl = (TextView) findViewById(R.id.tvCurrentDBUrl);
        tvBackupDBDate = (TextView) findViewById(R.id.tvBackupDBDate);
        tvBackupDBUrl = (TextView) findViewById(R.id.tvBackupDBUrl);
        bSave = (Button) findViewById(R.id.bSave);
        bRestore = (Button) findViewById(R.id.bRestore);
        bSave.setOnClickListener(this);
        bRestore.setOnClickListener(this);

        tvCurrentDBUrl.setText(CURRENT_URL);
        tvBackupDBUrl.setText(BACKUP_URL);
        updateBackupInfo();

        askPermissions();

    }

    private void askPermissions() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {

            boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_STORAGE);
            }
        }
    }

    private void updateBackupInfo() {
        File backupDB = new File(BACKUP_DB);
        if (backupDB.exists()) {
            bRestore.setEnabled(true);
            Date backupDBDate = new Date(backupDB.lastModified());
            tvBackupDBDate.setText(backupDBDate.toString());
        } else {
            tvBackupDBDate.setText("Not detected");
            bRestore.setEnabled(false);
        }

        File currentDB = new File(CURRENT_DB);
        if (currentDB.exists()) {
            Date currentDBDate = new Date(currentDB.lastModified());
            tvCurrentDBDate.setText(currentDBDate.toString());
        }
    }

    private void copy(String src, String dest) {
        File srcFile = new File(src);
        File destFile = new File(dest);
        Toast.makeText(this, "copy to " + dest, Toast.LENGTH_SHORT).show();

        if (!destFile.exists() && srcFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (srcFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(srcFile);
                FileOutputStream fos = new FileOutputStream(destFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Source file not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.the_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSave:
                File dir = new File(BACKUP_URL);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                copy(CURRENT_DB, BACKUP_DB);
                copy(CURRENT_DBJ, BACKUP_DBJ);
                updateBackupInfo();
                break;
            case R.id.bRestore:
                File restoreFile = new File(BACKUP_DB);
                if (restoreFile.exists()) {
                    copy(BACKUP_DB, CURRENT_DB);
                    copy(BACKUP_DBJ, CURRENT_DBJ);
                    updateBackupInfo();
                } else {
                    Toast.makeText(this, "No back-up DB to restore", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
