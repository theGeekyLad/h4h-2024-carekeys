/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blacklotus.carekeys.latin.settings;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.blacklotus.carekeys.R;
import com.blacklotus.carekeys.latin.utils.ApplicationUtils;
import com.blacklotus.carekeys.logger.DeviceAdminReceiver;

@SuppressLint("NewApi")
public final class SettingsFragment extends InputMethodSettingsFragment {
    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.prefs);
        final PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.setTitle(
                ApplicationUtils.getActivityTitleResId(getActivity(), SettingsActivity.class));

        ((Preference) findPreference("screen_enable_admin"))
                .setOnPreferenceClickListener(preference -> {

                    // [tgl] device admin
                    DevicePolicyManager dpm =
                            (DevicePolicyManager) getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);

                    ComponentName componentName = new ComponentName(getContext(), DeviceAdminReceiver.class);

                    if (!dpm.isAdminActive(componentName)) {
                        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, getString(R.string.device_admin_description));
                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Already enabled", Toast.LENGTH_SHORT).show();
                    }
//                    else {
//                        if (dpm.isDeviceOwnerApp(getPackageName()))
//                            Toast.makeText(getApplicationContext(), "Device Owner", Toast.LENGTH_LONG).show();
//                        if (dpm.isProfileOwnerApp(getPackageName()))
//                            Toast.makeText(getApplicationContext(), "Profile Owner", Toast.LENGTH_LONG).show();
//                            dpm.setPermittedInputMethods(componentName, new ArrayList<>());
//                            dpm.lockNow();
//                    }

                    return true;
                });

        ((Preference) findPreference("screen_switch_input"))
                .setOnPreferenceClickListener(preference -> {
                    ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showInputMethodPicker();

                    return true;
                });

        ((Preference) findPreference("screen_login"))
                .setOnPreferenceClickListener(preference -> {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Sign out");
                    builder.setMessage("Please ask your parent to complete this action.");
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();

                    return true;
                });
    }
}
