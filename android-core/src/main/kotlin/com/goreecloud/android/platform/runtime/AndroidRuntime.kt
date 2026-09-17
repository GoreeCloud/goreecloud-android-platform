package com.goreecloud.android.platform.runtime

import android.os.Build

data class AndroidRuntimeSnapshot(
    val sdkInt: Int,
    val previewSdkInt: Int,
) {
    fun isAtLeast(apiLevel: Int): Boolean = sdkInt >= apiLevel
}

object AndroidRuntime {
    fun snapshot(): AndroidRuntimeSnapshot = AndroidRuntimeSnapshot(
        sdkInt = Build.VERSION.SDK_INT,
        previewSdkInt = Build.VERSION.PREVIEW_SDK_INT,
    )
}
