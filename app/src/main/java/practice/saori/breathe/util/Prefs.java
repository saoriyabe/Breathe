package practice.saori.breathe.util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity activity) {
        this.preferences = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public void setDate(long milliseconds) {
        preferences.edit().putLong("seconds", milliseconds).apply();
    }

    public String getDate() {
        long miliseconds = preferences.getLong("seconds",
                System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(miliseconds);
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd - hh:mm");
        return df.format(date);
    }

    public void setSessions(int sessions) {
        preferences.edit().putInt("sessions", sessions).apply();
    }

    public int getSessions() {
        return preferences.getInt("sessions", 0);
    }

    public void setBreaths(int breaths) {
        preferences.edit().putInt("breaths", breaths).apply();
    }

    public int getBreaths() {
        return preferences.getInt("breaths", 0);
    }


}
