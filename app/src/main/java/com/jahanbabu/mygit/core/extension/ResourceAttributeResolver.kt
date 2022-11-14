package com.jahanbabu.mygit.core.extension

import android.content.Context
import android.util.TypedValue
import android.view.ContextThemeWrapper
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes

fun Context.resolveAttribute(@AttrRes attrResId: Int): Int {
    val typedValue = theme.obtainStyledAttributes(intArrayOf(attrResId))
    val resId = typedValue.getResourceId(0, Int.INVALID_VALUE)
    typedValue.recycle()
    return resId
}

fun Context.resolveAttribute(@StyleRes themeResId: Int, @AttrRes attrResId: Int): Int {
    return ContextThemeWrapper(this, themeResId).resolveAttribute(attrResId)
}

@ColorInt
fun Context.resolveColor(@AttrRes attrResId: Int): Int {
    val typedValue = TypedValue()
    return if (theme.resolveAttribute(attrResId, typedValue, true)) {
        typedValue.data
    } else {
        0
    }
}

@ColorInt
fun Context.resolveColor(@StyleRes themeResId: Int, @AttrRes attrResId: Int): Int {
    return ContextThemeWrapper(this, themeResId).resolveColor(attrResId)
}
