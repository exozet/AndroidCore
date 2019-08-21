@file:JvmName("BottomSheetBehaviourExtensions")
package com.exozet.android.core.extensions

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

inline var <reified T : View> BottomSheetBehavior<T>.isExpanded: Boolean
    get() = state == BottomSheetBehavior.STATE_EXPANDED
    set(expand) {
        state = if (expand) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
    }

inline var <reified T : View> BottomSheetBehavior<T>.isCollapsed: Boolean
    get() = state == BottomSheetBehavior.STATE_COLLAPSED
    set(collapse) {
        state = if (collapse) BottomSheetBehavior.STATE_COLLAPSED else BottomSheetBehavior.STATE_EXPANDED
    }

inline var <reified T : View> BottomSheetBehavior<T>.isExpandedHalf: Boolean
    get() = state == BottomSheetBehavior.STATE_HALF_EXPANDED
    set(expandHalf) {
        state = if (expandHalf) BottomSheetBehavior.STATE_HALF_EXPANDED else BottomSheetBehavior.STATE_EXPANDED
    }

inline var <reified T : View> BottomSheetBehavior<T>.isHidden: Boolean
    get() = state == BottomSheetBehavior.STATE_HIDDEN
    set(hide) {
        isHideable = true
        state = if (hide) BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_COLLAPSED
    }