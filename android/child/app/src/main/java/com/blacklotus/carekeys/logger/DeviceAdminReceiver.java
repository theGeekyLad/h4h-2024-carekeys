package com.blacklotus.carekeys.logger;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import com.blacklotus.carekeys.R;

public class DeviceAdminReceiver extends android.app.admin.DeviceAdminReceiver {

    void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        showToast(context, "onEnabled");
    }

    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return "Bro, disable requested!";
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        showToast(context, "onDisabled");
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        showToast(context, "onPasswordChanged");
    }
}