package com.example.filipturek.requestingpermission.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionsUtils {

    private Activity mActivity;
    private String [] permissions;

    public PermissionsUtils(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public void checkPermissions(int REQUEST_MULTIPLE_PERMISSIONS) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!arePermissionsEnabled()){
                requestMultiplePermissions(REQUEST_MULTIPLE_PERMISSIONS);
            }
        }
    }

    private boolean arePermissionsEnabled(){
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private  boolean requestMultiplePermissions(int REQUEST_MULTIPLE_PERMISSIONS) {
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : permissions){
            if(ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED){
                remainingPermissions.add(permission);
            }
        }

        if (!remainingPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    mActivity,
                    remainingPermissions.toArray(new String[remainingPermissions.size()]),
                    REQUEST_MULTIPLE_PERMISSIONS);

            return false;
        }
        return true;
    }

}
