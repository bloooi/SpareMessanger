package jay.messenger.spare.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by leejaebeom on 2017. 8. 12..
 */

public class SharedPreference {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public static void saveData(Context context, String prefKey, String key, int value){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static void saveData(Context context, String prefKey, String key, String value){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static void saveData(Context context, String prefKey, String key, boolean value){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static void saveData(Context context, String prefKey, String key, Set<String> value){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putStringSet(key,value);
        editor.commit();
    }

    public static int getData(Context context, String prefKey, String key, int defaultValue){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        return preferences.getInt(key,defaultValue);
    }

    public static String getData(Context context, String prefKey, String key, String defaultValue){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        return preferences.getString(key,defaultValue);
    }

    public static boolean getData(Context context, String prefKey, String key, boolean defaultValue){
        preferences = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);
        return preferences.getBoolean(key,defaultValue);
    }
}
