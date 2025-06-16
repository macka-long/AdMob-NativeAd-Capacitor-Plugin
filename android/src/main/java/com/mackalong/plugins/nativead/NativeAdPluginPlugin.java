package com.mackalong.plugins.nativead;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import android.util.Log;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;
import com.getcapacitor.JSObject;


@CapacitorPlugin(name = "NativeAdPlugin")
public class NativeAdPluginPlugin extends Plugin {

    private NativeAdPlugin implementation = new NativeAdPlugin();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @PluginMethod
    public void loadNativeAd(PluginCall call) {
        String adUnitId = "ca-app-pub-3940256099942544/2247696110"; // テスト用

        getActivity().runOnUiThread(() -> {
            MobileAds.initialize(getContext());

            AdLoader adLoader = new AdLoader.Builder(getContext(), adUnitId)
                .forNativeAd(nativeAd -> {
                    JSObject ad = new JSObject();
                    ad.put("headline", nativeAd.getHeadline());
                    ad.put("body", nativeAd.getBody());
                    ad.put("advertiser", nativeAd.getAdvertiser());
                    ad.put("callToAction", nativeAd.getCallToAction());

                    JSObject ret = new JSObject();
                    ret.put("ad", ad);
                    notifyListeners("nativeAdLoaded", ret);

                    call.resolve();
                })
                .build();

            adLoader.loadAd(new AdRequest.Builder().build());
        });
    }
}
