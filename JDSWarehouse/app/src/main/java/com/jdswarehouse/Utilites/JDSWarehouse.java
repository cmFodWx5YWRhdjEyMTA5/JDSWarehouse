package com.jdswarehouse.Utilites;

import android.support.multidex.MultiDexApplication;

import com.jdswarehouse.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dikhong on 02-07-2018.
 */

public class JDSWarehouse extends MultiDexApplication {

    private static JDSWarehouse instance;
    public String SERVER_ERROR = "Unable to connect to server, please try again after sometime";


    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static JDSWarehouse getInstance() {
        return instance;
    }
}
