package com.exozet.android.core.utils;

import android.os.Bundle;

import androidx.annotation.Nullable;

import net.kibotu.logger.Logger;


/**
 * Created by jan.rabe on 11/03/16.
 */
final public class BundleExtensions {

    private static final String TAG = BundleExtensions.class.getSimpleName();

    private BundleExtensions() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void printBundle(@Nullable final Bundle bundle) {
        if (bundle == null) {
            Logger.v(TAG, "Empty bundle.");
            return;
        }
        for (final String key : bundle.keySet()) {
            final Object value = bundle.get(key);
            Logger.d(TAG, String.format("%s %s (%s)", key, "" + value, value != null ? value.getClass().getName() : " null"));
        }
    }

    public static String toString(Bundle bundle) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (final String key : bundle.keySet()) {
            final Object value = bundle.get(key);
            builder.append(key).append(":").append(value);
        }
        builder.append("}");
        return builder.toString();
    }
}
