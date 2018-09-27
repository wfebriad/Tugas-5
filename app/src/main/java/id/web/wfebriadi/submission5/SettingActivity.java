package id.web.wfebriadi.submission5;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import id.web.wfebriadi.submission5.notification.DailyNotification;
import id.web.wfebriadi.submission5.notification.ReleaseNotification;

public class SettingActivity extends AppCompatActivity {

    SwitchCompat dailySwitch, releaseSwitch;
    TextView changeLanguage;
    private DailyNotification dailyNotification = new DailyNotification();
    private ReleaseNotification releaseNotification = new ReleaseNotification();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        dailySwitch = findViewById(R.id.daily_reminder);
        releaseSwitch = findViewById(R.id.release_reminder);
        changeLanguage = findViewById(R.id.change_language);
        DailyAlarm();
        ReleaseAlarm();
        ChangeLanguage();
    }
    void DailyAlarm(){
        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (dailySwitch.isChecked()){
                    dailyNotification.notificationDaily(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Daily Notification : on", Toast.LENGTH_SHORT).show();
                } else {
                    dailyNotification.cancelAlarm(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Daily Notification : off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void ReleaseAlarm(){
        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (releaseSwitch.isChecked()){
                    releaseNotification.notificationRelease(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Release Notification : on", Toast.LENGTH_SHORT).show();
                } else {
                    releaseNotification.notificationRelease(SettingActivity.this);
                    Toast.makeText(SettingActivity.this, "Release Notification : off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void ChangeLanguage(){
        changeLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeLanguage.isClickable()){
                    Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                    startActivity(intent);
                }
            }
        });
    }
}
