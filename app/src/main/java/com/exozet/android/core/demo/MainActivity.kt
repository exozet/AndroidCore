package com.exozet.android.core.demo

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Spanned
import android.view.animation.Animation
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.exozet.android.core.base.BaseActivity
import com.exozet.android.core.demo.features.realmLiveData.ViewModelRealmSampleFragment
import com.exozet.android.core.extensions.replaceFragment
import com.exozet.android.core.extensions.stringFromAssets
import com.exozet.android.core.services.notifications.GcmSender
import com.exozet.android.core.storage.secureStorage
import net.kibotu.resourceextension.*

class MainActivity : BaseActivity() {

    private var password by secureStorage("password", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Core)
        super.onCreate(savedInstanceState)


        val pw = password

        password = jwt

        if (savedInstanceState == null) {
//            replaceFragment(WidgetSampleFragment.newInstance())
            replaceFragment(ViewModelRealmSampleFragment())
        }

//        test()

        GcmSender.API_KEY = ""

        GcmSender.sendAsync("hello world")
    }

    fun test() {

        val my_integer: Int = R.integer.my_integer.resInt
        val my_long: Long = R.integer.my_long.resLong
        val my_string: String = R.string.my_string.resString
        val my_string_args: String = R.string.my_string_args.resString("ipsum")
        val my_string_args_float: String = R.string.my_string_args_float.resString(2f)
//        val my_localized_string: String = R.string.my_localized_string.localizedString(Locale.GERMAN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            val my_localized_resources: Resources = localizedResources(this, Locale.GERMAN)
        }
//        val my_quantityString: String = R.plurals.my_quantityString.quantityString(2)
//        val my_quantityString_args: String = R.plurals.my_quantityString_args.quantityString(2, "total")

        val my_boolean: Boolean = R.bool.my_boolean.resBoolean
//        val my_color_long: Long = R.color.my_color_long.resColorLong
        val my_color: Int = R.color.my_color.resColor
        val my_dimension: Float = R.dimen.my_dimension.resDimension
        val my_string_html: Spanned = """<a href="https://www.google.com/">Google</a>""".html
        val my_html: Spanned = R.string.my_html.html
//        val my_csv: List<String> = R.string.my_csv.csv
//        val my_xml: XmlResourceParser = R.xml.lorem_ipsum.resXml
        // 0.10f
//        val my_fraction: Float = R.fraction.my_fraction.resFraction(2, 2)
        val my_int_array: IntArray = R.array.my_int_array.resIntArray
        val my_string_array: Array<String> = R.array.my_string_array.resStringArray
        val my_character_array: Array<CharSequence> = R.array.my_string_array.resTextArray
        /*ColorRes*/
        val my_icons_array: List<Int> = R.array.my_colors.resColorIntArray
        /*ColorInt*/
//        val my_icons_array_color_int: List<Int> = R.array.my_colors.resColorIntArray
        /*@DrawableRes*/
        val my_colors_array: List<Int> = R.array.my_icons.resDrawableIdArray
        /*@Drawable*/
//        val my_colors_array_drawable: List<Drawable> = R.array.my_icons.resDrawableArray
        @IdRes val my_id: Int = "my_id".resId
        val my_res_name: String = R.integer.my_res_name.resName
//        val my_res_type_name: String = R.integer.my_res_type_name.resTypeName
//        val my_res_package_name: String = R.integer.my_res_package_name.resPackageName
//        @StringRes val my_res_string_id: Int = "my_res_string_id".resStringId { it.printStackTrace() }
        @DrawableRes val my_res_drawable_id: Int = "ic_share".resDrawableId
//        @DrawableRes val my_res_drawable_id_with_error_handling: Int = "ic_share".resDrawableId { it.printStackTrace() }
        val my_drawable: Drawable = R.drawable.ic_share.resDrawable
        val my_anim: Animation = R.anim.grow.resAnim
        val my_animator: Animator = R.animator.flip_animation.resAnimator
//        val my_font: Typeface = R.font.lato.resFont
//        val my_raw: InputStream = R.raw.my_raw.resRaw
//        val my_interpolator: Interpolator = android.R.interpolator.anticipate_overshoot.resInterpolator
//        val my_res_layout_animation_controller: LayoutAnimationController = R.anim.layout_animation.resLayoutAnimation
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val my_transition: Transition = android.R.transition.explode.resTransition
        }
//        val my_layout: XmlResourceParser = R.layout.activity_main.resLayout
//        val my_inflated_layout: View = R.layout.activity_main.inflate(main_background)
        val my_dp: Float = 16f.px
        val my_dp_int: Int = 16.px
        val my_px: Float = 200f.dp
        val my_px_int: Int = 200.dp
        val my_sp: Float = 14f.sp
        val my_screen_width_dp: Int = screenWidthDp
        val my_screen_height_dp: Int = screenHeightDp
        val my_screen_width_pixels: Int = screenWidthPixels
        val my_screen_height_pixels: Int = screenHeightPixels
        val my_bytes_from_assets: ByteArray? = "my_text.json".bytesFromAssets()
        val my_string_from_assets: String? = "my_text.json".stringFromAssets()
        val my_device_is_rtl: Boolean = isRightToLeft
        val my_string_is_a_telephone_link: Boolean =
            Uri.parse("""<a href="tel:491771789232">Google</a>""").isTelephoneLink
        val my_string_is_a_mailto_link: Boolean =
            Uri.parse("""<a href="mailto:cloudgazer3d@gmail.com">Google</a>""").isMailToLink
    }

    companion object {
        private const val jwt =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE1NzI2MTAwNTcsImV4cCI6MTU3MjY0NjA1Nywicm9sZXMiOlsiUk9MRV9NQU5EQU5UIiwiUk9MRV9BRE1JTl9BUkVBX0FMTCIsIlJPTEVfQURNSU5fQVJUSUNMRV9BTEwiLCJST0xFX0FETUlOX1NFQVNPTl9BTEwiLCJST0xFX0FETUlOX09QRU5fVElNRV9BTEwiLCJST0xFX0FETUlOX0NPTVBJTEFUSU9OX0FMTCIsIlJPTEVfQURNSU5fUEFDS0FHRV9BTEwiLCJST0xFX0FETUlOX1BST01PVElPTl9BTEwiLCJST0xFX0FETUlOX01FU1NBR0VfQUxMIiwiUk9MRV9BRE1JTl9QT0lfVFlQRV9BTEwiLCJST0xFX0FETUlOX1BPSV9GSUxURVJfQUxMIiwiUk9MRV9BRE1JTl9QT0lfQUxMIiwiUk9MRV9BRE1JTl9HUkFQSF9OT0RFX0FMTCIsIlJPTEVfQURNSU5fR1JBUEhfRURHRV9BTEwiLCJST0xFX0FETUlOX1dBSVRJTkdfVElNRV9BTEwiLCJST0xFX0FETUlOX0FERE9OX0FMTCIsIlJPTEVfQURNSU5fUFJPRFVDVF9BTEwiLCJST0xFX1NPTkFUQV9VU0VSX0FETUlOX1VTRVJfQUxMIiwiUk9MRV9VU0VSIl0sInVzZXJuYW1lIjoibWFuZGFudEBleG96ZXQuZXUifQ.UuAHM5vGGGXa-ZbO_wgr7yp2ewSdCW2dTp7AZBDOBR0ZL-XlclJ_Pxis2OL2_t5cC2NQlXxg0zP-3fAVYh_XPCyX8pPld_gshQ5ca1G-GudmChqVAS0WkcVjDahvho-M4ls1fowud-dDUvW5dJqvPAZND40rlvNjNBVOUTTtBga9OJDvNYRk7cwR1PL6Uf2_YyDbThn0-RNLfNpYB2XIzDrVwtxAuYdLdAKyYcKb3E7EogjGOZLb9DdWJqxoxcHJ5G211em38kEJC8lS1jtgBplP9ViGwV1DVHxJYDJErBoHWW2s-U25qFZsCa0AxTOwjdFSvYRpMQm3a_RXY4bO7WUYJC0CfKeH23OXmY7k9sTDq7XCL5lnkEcsR17meqI3Yx20nPIPevyjoNEcaUogmP8u_oraVzJPylDFyoYllh8hinYN1QAWzdbL-mKAbTch63vUlcIWrpdWFYXlTHts5ccHtfwNWFcziXBzsm4gwFht-mErguzzFLvyLqPi8R2Olq7Lgrb6uo1fG3QVv2L-Zv08A8wyoAnBX_cwWzES8iz-Tl48KGyENtGy8CgFzH7xJD59dxQMpLh-Hj0vpb1vP-S09e6Zk5dkM_0-GO1wQ7z3l5hzCvTdWzxB1WxklWAO9jboQWWwl6QIQxEltgig0f2LySU4HLQW90Dj31QW5RA"
    }
}