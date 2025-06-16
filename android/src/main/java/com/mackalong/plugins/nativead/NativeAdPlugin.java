package com.mackalong.plugins.nativead;

import android.util.Log;

public class NativeAdPlugin {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
