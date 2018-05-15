package handlerclasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "BuzzmovePref";

    public static final String KEY_ISDATA = "isdata";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    public static final String KEY_RESULT = "result";
    public static final String KEY_CURR_LOC = "loc";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create response session for offline
     * */
    public void saveName(String name){
        // Storing login value as TRUE
        editor.putBoolean(KEY_ISDATA, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);
        // commit changes
        editor.commit();
    }

    /**
     * Create response session for offline
     * */
    public void saveResults(String results){
        // Storing login value as TRUE
        editor.putBoolean(KEY_ISDATA, true);


        // Storing email in pref
        editor.putString(KEY_RESULT, results);

        // commit changes
        editor.commit();
    }

    /**
     * Create response session for offline
     * */
    public void saveCurrLoc(String currentLoc){
        // Storing login value as TRUE
        editor.putBoolean(KEY_ISDATA, true);


        // Storing email in pref
        editor.putString(KEY_CURR_LOC, currentLoc);

        // commit changes
        editor.commit();
    }


    /**
     * Get stored session data
     * */
    public String getOfflineName(){

        return pref.getString(KEY_NAME, null);
    }

    /**
     * Get stored session data
     * */
    public String getOfflineLocation(){

        return pref.getString(KEY_CURR_LOC, null);
    }

    /**
     * Get stored session data
     * */
    public String getOfflineResult(){

        return pref.getString(KEY_RESULT, null);
    }

    /**
     * Quick check for available data
     * **/
    // Get Login State
    public boolean isOfflineData(){
        return pref.getBoolean(KEY_ISDATA, false);
    }
}
