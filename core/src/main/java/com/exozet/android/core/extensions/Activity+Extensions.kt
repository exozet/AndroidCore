@file:JvmName("ActivityExtensions")

package com.exozet.android.core.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.europapark.services.connectivity.ConnectivityAndInternetAccess
import com.github.florent37.application.provider.ActivityProvider
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import net.kibotu.logger.Logger.logv

val Activity.contentRootView: ViewGroup
    get() = window
        .decorView
        .findViewById(android.R.id.content)

val Activity.isImmersiveModeEnabled
    get() = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == window.decorView.systemUiVisibility

operator fun Window.plusAssign(flags: Int) {
    addFlags(flags)
}

operator fun Window.minusAssign(flags: Int) {
    clearFlags(flags)
}

fun Activity.chooseWifiNetwork() = startActivity(Intent(WifiManager.ACTION_PICK_WIFI_NETWORK))

fun Activity.addSecureFlag() {
    window += WindowManager.LayoutParams.FLAG_SECURE
}

fun Activity.clearSecureFlag() {
    window -= WindowManager.LayoutParams.FLAG_SECURE
}

fun Activity.setKeepOnScreenFlag() {
    // Keep the screen always on
    window += WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
    // Close dialogs and window shade
    sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
}

fun Activity.showSystemUI() {
    window -= WindowManager.LayoutParams.FLAG_FULLSCREEN
    window -= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    if (!isImmersiveModeEnabled)
        return

    toggleHideyBar()
}

fun Activity.hideSystemUI() {
    window += WindowManager.LayoutParams.FLAG_FULLSCREEN
    window += WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    if (isImmersiveModeEnabled)
        return

    toggleHideyBar()
}

/**
 * Detects and toggles immersive mode (also known as "hidey bar" mode).
 * @source https://github.com/googlesamples/android-ImmersiveMode/blob/master/Application/src/main/java/com/example/android/immersivemode/ImmersiveModeFragment.java
 */
fun Activity.toggleHideyBar() {

    // BEGIN_INCLUDE (get_current_ui_flags)
    // The UI options currently enabled are represented by a bitfield.
    // getSystemUiVisibility() gives us that bitfield.
    val uiOptions = window.decorView.systemUiVisibility
    var newUiOptions = uiOptions
    // END_INCLUDE (get_current_ui_flags)
    // BEGIN_INCLUDE (toggle_ui_flags)
    val isImmersiveModeEnabled = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
    if (isImmersiveModeEnabled) {
        logv { "Turning immersive mode mode off. " }
    } else {
        logv { "Turning immersive mode mode on." }
    }

    // Navigation bar hiding:  Backwards compatible to ICS.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    // Status bar hiding: Backwards compatible to Jellybean
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    // Immersive mode: Backward compatible to KitKat.
    // Note that this flag doesn't do anything by itself, it only augments the behavior
    // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
    // all three flags are being toggled together.
    // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
    // Sticky immersive mode differs in that it makes the navigation and status bars
    // semi-transparent, and the UI flag does not get cleared when the user interacts with
    // the screen.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

    window.decorView.systemUiVisibility = newUiOptions
    //END_INCLUDE (set_ui_flags)
}

var errorDialog: Dialog? = null

fun Activity.checkGooglePlayServices(): Boolean {

    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
    if (resultCode != ConnectionResult.SUCCESS) {
        if (googleApiAvailability.isUserResolvableError(resultCode)) {
            if (errorDialog == null) {
                errorDialog = googleApiAvailability.getErrorDialog(this, resultCode, 2404)
                errorDialog?.setCancelable(false)
            }
            if (errorDialog?.isShowing == false)
                errorDialog?.show()
        }
    }
    return resultCode == ConnectionResult.SUCCESS
}


fun Activity.setTransparentStatusBarFlags() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT
    }
}

fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val winParams = window.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    window.attributes = winParams
}

/**
 * true for dark tinted icons
 */
var Activity.isLightStatusBar
    get() = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR == window.decorView.systemUiVisibility
    set(enabled) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return

        if (enabled) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

/**
 * true for dark tinted icons
 */
var Activity.isLightNavigationBar
    get() = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR == window.decorView.systemUiVisibility
    set(enabled) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return

        if (enabled) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
    }


/**
 * Open another app.
 *
 * source: https://stackoverflow.com/a/7596063/1006741
 *
 * @param packageName the full package name of the app to open
 */
fun Activity.openMainLauncherApp(packageName: String?) =
    with(packageManager.getLaunchIntentForPackage(packageName!!)!!) {
        addCategory(Intent.CATEGORY_LAUNCHER)
        startActivity(this)
    }

fun String.openMainLauncherApp() = ActivityProvider.currentActivity?.openMainLauncherApp(this)

fun String.openMarket() = ActivityProvider.currentActivity?.openMarket(this)

fun Activity.openMarket(packageName: String) = try {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
} catch (e: Exception) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=${packageName}")))
}

fun String.openAppOrMarket() = try {
    openMainLauncherApp()
} catch (e: Exception) {
    openMarket()
}

val Context.isConnected
    get() = ConnectivityAndInternetAccess.isConnected(this)