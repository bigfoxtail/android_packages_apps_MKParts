/*
 * Copyright (C) 2014-2019 The MoKee Open Source Project
 * Copyright (C) 2017-2019 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mokee.mokeeparts.statusbar;

import android.content.ContentResolver;
import android.os.Bundle;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;

import mokee.preference.MoKeeSecureSettingSwitchPreference;
import mokee.providers.MoKeeSettings;
import org.mokee.mokeeparts.R;
import org.mokee.mokeeparts.SettingsPreferenceFragment;


public class NetworkTrafficSettings extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener  {

    private static final String TAG = "NetworkTrafficSettings";

    private DropDownPreference mNetTrafficMode;
    private MoKeeSecureSettingSwitchPreference mNetTrafficAutohide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.network_traffic_settings);
        final ContentResolver resolver = getActivity().getContentResolver();

        mNetTrafficMode = findPreference(MoKeeSettings.Secure.NETWORK_TRAFFIC_MODE);
        mNetTrafficMode.setOnPreferenceChangeListener(this);
        int mode = MoKeeSettings.Secure.getInt(resolver,
                MoKeeSettings.Secure.NETWORK_TRAFFIC_MODE, 3);
        mNetTrafficMode.setValue(String.valueOf(mode));

        mNetTrafficAutohide = findPreference(MoKeeSettings.Secure.NETWORK_TRAFFIC_AUTOHIDE);
        mNetTrafficAutohide.setOnPreferenceChangeListener(this);

        updateEnabledStates(mode);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mNetTrafficMode) {
            int mode = Integer.valueOf((String) newValue);
            MoKeeSettings.Secure.putInt(getActivity().getContentResolver(),
                    MoKeeSettings.Secure.NETWORK_TRAFFIC_MODE, mode);
            updateEnabledStates(mode);
        }
        return true;
    }

    private void updateEnabledStates(int mode) {
        final boolean enabled = mode != 0;
        mNetTrafficAutohide.setEnabled(enabled);
    }
}
