/*
 * Copyright © 2018-2019 Syberia Project
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

package com.syberia.settings.fragments;

import android.os.Bundle;
import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import com.android.settings.R;
import androidx.preference.Preference;
import androidx.preference.SwitchPreference;
import androidx.preference.PreferenceScreen;
import android.os.SystemProperties;

import android.provider.SearchIndexableResource;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import com.android.settings.SettingsPreferenceFragment;

import com.android.internal.logging.nano.MetricsProto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.widget.Toast;

@SearchIndexable
public class GeneralTweaks extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String KEY_PHOTOS_SPOOF = "use_photos_spoof";
    private static final String SYS_PHOTOS_SPOOF = "persist.sys.pixelprops.gphotos";

    private SwitchPreference mPhotosSpoof;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.general_tweaks);

        final ContentResolver resolver = getActivity().getContentResolver();
        final PreferenceScreen prefSet = getPreferenceScreen();

        mPhotosSpoof = (SwitchPreference) findPreference(KEY_PHOTOS_SPOOF);
        mPhotosSpoof.setChecked(SystemProperties.getBoolean(SYS_PHOTOS_SPOOF, true));
        mPhotosSpoof.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mPhotosSpoof) {
            boolean value = (Boolean) newValue;
            SystemProperties.set(SYS_PHOTOS_SPOOF, value ? "true" : "false");
            return true;
        }
        return false;
    }


    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.SYBERIA;
    }

    /**
     * For Search.
     */
    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {

                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(
                        Context context, boolean enabled) {
                    final SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.general_tweaks;
                    return Arrays.asList(sir);
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    final List<String> keys = super.getNonIndexableKeys(context);
                    return keys;
                }
    };
}
