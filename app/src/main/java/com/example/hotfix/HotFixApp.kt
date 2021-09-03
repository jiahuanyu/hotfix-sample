package com.example.hotfix

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import java.io.File

@SuppressLint("DiscouragedPrivateApi")
class HotFixApp : Application() {
    companion object {
        private const val TAG = "HotFixApp"
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        Log.d(TAG, "ClassLoader = $classLoader")
        Log.d(TAG, "externalCacheDir = ${base.externalCacheDir}")
        // 进行热修复
        val hotFixApk = File(base.externalCacheDir, "hotfix.apk")
        if (hotFixApk.exists()) {
            Log.d(TAG, "hotFixApk exists, start hot fix")
            try {
                val pathListField =
                    BaseDexClassLoader::class.java.getDeclaredField("pathList")
                pathListField.isAccessible = true
                val pathListObject = pathListField.get(classLoader)

                val dexElementsField = pathListObject.javaClass.getDeclaredField("dexElements")
                dexElementsField.isAccessible = true

                // 加载 hotfix apk
                val dexClassLoader = DexClassLoader(
                    hotFixApk.absolutePath,
                    base.cacheDir.absolutePath,
                    null,
                    classLoader
                )
                val newPathListObject = pathListField.get(dexClassLoader)
                val newDexElementsObject = dexElementsField.get(newPathListObject)
                dexElementsField.set(pathListObject, newDexElementsObject)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
//                hotFixApk.delete()
            }
        }
    }
}