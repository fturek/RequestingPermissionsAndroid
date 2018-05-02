package com.example.filipturek.requestingpermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.filipturek.requestingpermission.permissions.PermissionsUtils;

import de.mateware.snacky.Snacky;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_PERMISSIONS = 100;

    PermissionsUtils permissionsUtils;

    String[] permissions = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissionsUtils = new PermissionsUtils(this);
        permissionsUtils.setPermissions(permissions);
        permissionsUtils.checkPermissions(REQUEST_PERMISSIONS);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    // some required permissions are not granted. Show SnackBar and inform the user
                    buildSnackBarForNotGrantedPermissions();
                }
            }
        }

    }

    public void buildSnackBarForNotGrantedPermissions() {
        Snackbar snackbar = Snacky.builder()
                .setActivity(this)
                .setText("Not granted required permissions ")
                .setDuration(Snacky.LENGTH_INDEFINITE)
                .setActionText("GRANT")
                .setActionClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                permissions,
                                REQUEST_PERMISSIONS);
                    }
                })
                .build();

        snackbar.show();
    }
}
