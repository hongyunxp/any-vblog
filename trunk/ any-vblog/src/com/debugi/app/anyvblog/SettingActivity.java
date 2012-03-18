package com.debugi.app.anyvblog;

import android.app.Activity;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.debugi.app.anyvblog.utils.Config;

public class SettingActivity extends PreferenceActivity implements OnPreferenceChangeListener, OnPreferenceClickListener {
	private static final String TAG = "SettingActivity";
	private final Activity activity = this;
	private CheckBoxPreference show_pic;
	private CheckBoxPreference auto_update;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 加载设置xml
		addPreferencesFromResource(R.xml.setting);
		
		show_pic = (CheckBoxPreference) findPreference(getString(R.string.show_pic));
		auto_update = (CheckBoxPreference) findPreference(getString(R.string.auto_update));
		
		show_pic.setOnPreferenceChangeListener(this);
		show_pic.setOnPreferenceClickListener(this);
		
		auto_update.setOnPreferenceChangeListener(this);
		auto_update.setOnPreferenceClickListener(this);
		
	}
	@Override
	public boolean onPreferenceClick(Preference preference) {
		Config.debug(TAG, preference);
		return true;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		Config.debug(TAG, preference.toString() + "  " + newValue);
		Config.showPic = Config.isShowPic(activity);
		Config.showPic = Config.isAutoCheckUpdate(activity);
		Toast.makeText(activity, "更改设置后请重启应用程序以使设置生效", Toast.LENGTH_SHORT).show();
		return true;
	}

}
