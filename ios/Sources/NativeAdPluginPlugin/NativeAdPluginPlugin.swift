import Foundation
import Capacitor
import GoogleMobileAds

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(NativeAdPluginPlugin)
public class NativeAdPluginPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "NativeAdPluginPlugin"
    public let jsName = "NativeAdPlugin"
    private let implementation = NativeAdPlugin()

    var adLoader: GADAdLoader?
    var nativeAd: GADNativeAd?

    @objc func loadNativeAd(_ call: CAPPluginCall) {
        let adUnitId = call.getString("adUnitId") ?? ""
        let rootViewController = self.bridge?.viewController

        let options = GADMultipleAdsAdLoaderOptions()
        options.numberOfAds = 1

        adLoader = GADAdLoader(
            adUnitID: adUnitId,
            rootViewController: rootViewController,
            adTypes: [.native],
            options: [options]
        )
        adLoader?.delegate = self
        let request = GADRequest()
        adLoader?.load(request)

        call.resolve() 
    }
}

// MARK: - Delegate 実装
extension NativeAdPluginPlugin: GADNativeAdLoaderDelegate {
    func adLoader(_ adLoader: GADAdLoader, didReceive nativeAd: GADNativeAd) {
        self.nativeAd = nativeAd

        // 広告情報を必要に応じて抽出してJSに送信
        let adData: [String: Any] = [
            "headline": nativeAd.headline ?? "",
            "body": nativeAd.body ?? "",
            "advertiser": nativeAd.advertiser ?? ""
        ]

        notifyListeners("nativeAdLoaded", data: adData)
    }

    func adLoader(_ adLoader: GADAdLoader, didFailToReceiveAdWithError error: Error) {
        notifyListeners("nativeAdFailed", data: ["error": error.localizedDescription])
    }
}

