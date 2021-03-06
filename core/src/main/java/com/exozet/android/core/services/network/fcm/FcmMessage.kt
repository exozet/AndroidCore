package com.exozet.android.core.services.network.fcm

import org.parceler.Parcel

/**
 * FCM message
 * <code>
 * {
 *     "to": "/topics/global",
 *     "notification": {
 *         "title": "hello",
 *         "text": "world"
 *     },
 *     "data": {
 *         "key1": "value1",
 *         "key2": "value2"
 *     }
 * }
 * </code>
 */
@Parcel(Parcel.Serialization.BEAN)
data class FcmMessage(
    val to: String? = null,
    val notification: NotificationContent? = null,
    val data: Map<String, String>? = null
) {
    constructor(recipient: String, title: String, text: String, data: Map<String, String>? = null)
            : this(recipient, NotificationContent(title, text), data)
}