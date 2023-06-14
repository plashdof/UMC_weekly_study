package com.a9week.util

import android.content.Context

object DisplayUtil {

    val TAG = "debugging"

    fun getStatusBarHeight(context: Context): Int {
        var statusbarHeight = 0
        val resourceId: Int = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusbarHeight = context.resources.getDimensionPixelSize(resourceId)
        }

        var statusbarHeightDP = pxTodp(statusbarHeight, context)
        return statusbarHeightDP.toInt()
    }


    fun pxTodp(px: Int, context: Context): Float {
        return px / context.resources.displayMetrics.density
    }
}