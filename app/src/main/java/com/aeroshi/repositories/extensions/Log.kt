package com.aeroshi.repositories.extensions


import android.util.Log
import com.aeroshi.repositories.util.LogUtil
import com.aeroshi.repositories.util.StringUtil

private const val TAG = ">>>>> [Repositories]"
private const val PIPE = "|"


fun logTrace(className: String, message: String, scope: String = StringUtil.EMPTY) {
    Log.v(
        TAG,
        "${LogUtil.getDate()} $PIPE ${LogUtil.getTime()} $PIPE ${Thread.currentThread().name} $PIPE Trace $PIPE $className ${
            LogUtil.getScope(
                scope
            )
        } $PIPE $message"
    )
}

fun logInfo(className: String, message: String, scope: String = StringUtil.EMPTY) {
    Log.i(
        TAG,
        "${LogUtil.getDate()} $PIPE ${LogUtil.getTime()} $PIPE ${Thread.currentThread().name} $PIPE Information $PIPE $className ${
            LogUtil.getScope(
                scope
            )
        } $PIPE $message"
    )
}

fun logDebug(className: String, message: String, scope: String = StringUtil.EMPTY) {
    Log.d(
        TAG,
        "${LogUtil.getDate()} $PIPE ${LogUtil.getTime()} $PIPE ${Thread.currentThread().name} $PIPE Debug $PIPE $className ${
            LogUtil.getScope(
                scope
            )
        } $PIPE $message"
    )
}

fun logWarning(className: String, message: String, scope: String = StringUtil.EMPTY) {
    Log.w(
        TAG,
        "${LogUtil.getDate()} $PIPE ${LogUtil.getTime()} $PIPE ${Thread.currentThread().name} $PIPE Warning $PIPE $className ${
            LogUtil.getScope(
                scope
            )
        } $PIPE $message"
    )
}

fun logError(
    className: String,
    message: String,
    exception: Exception,
    scope: String = StringUtil.EMPTY
) {
    Log.w(
        TAG,
        "${LogUtil.getDate()} $PIPE ${LogUtil.getTime()} $PIPE ${Thread.currentThread().name} $PIPE Error $PIPE $className ${
            LogUtil.getScope(
                scope
            )
        } $PIPE $message $PIPE ${exception.message}"
    )
    exception.printStackTrace()
}

fun logError(
    className: String,
    message: String,
    throwable: Throwable,
    scope: String = StringUtil.EMPTY
) {
    Log.w(
        TAG,
        "${LogUtil.getDate()} $PIPE ${LogUtil.getTime()} $PIPE ${Thread.currentThread().name} $PIPE Error $PIPE $className ${
            LogUtil.getScope(
                scope
            )
        } $PIPE $message $PIPE ${throwable.message}"
    )
    throwable.printStackTrace()
}

