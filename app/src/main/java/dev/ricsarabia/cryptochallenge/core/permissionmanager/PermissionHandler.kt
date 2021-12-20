package com.santander.globile.coreapp.permissionmanager

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import dev.ricsarabia.cryptochallenge.R

class PermissionHandler(private val delegate: PermissionHandlerDelegate) {

    companion object {
        private const val TAG = "PermissionHandler"
        private const val requestCode = 948239283
    }

    private lateinit var permissionsRequired: Array<String>

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermissions(context: Context) {
        permissionsRequired = context.resources.getStringArray(R.array.app_permissions)
        val permissionsMissing = permissionsRequired.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        if (permissionsMissing.isEmpty()) {
            Log.i(TAG, "permissions granted")
            delegate.onPermissionsGranted()
        } else {
            Log.w(TAG, "permissions not accepted yet")
            delegate.getActivityForPermissionRequest()
                .requestPermissions(permissionsRequired, requestCode)
        }
    }


    fun validatePermissions(
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (grantResults.filter {
                it == 0
            }.size == permissionsRequired.size) {

            Log.i(TAG, "permissions granted")
            delegate.onPermissionsGranted() // hideSplashAnimation() 
        } else {
            Log.e(TAG, "not all permissions granted")
            delegate.onPermissionsDenied() // launchMessageForPermissionsDenied()
        }

    }

    fun launchMessageForPermissionsDenied(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.onDeniedPermissionsDialogMessage)
            .setPositiveButton(R.string.onDeniedPermissionsDialogAccept,
                DialogInterface.OnClickListener { dialog, id ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //checkPermissions(context)
                        openAppSettingsScreen(context)
                    } else {
                        Log.e(TAG, "permissions unhandled")
                    }
                })
            .setNegativeButton(
                R.string.onDeniedPermissionsDialogCancel,
                DialogInterface.OnClickListener { dialog, id ->
                    delegate.onErrorGettingPermissions()
                })
        // Create the AlertDialog object and return it
        builder.create().show()
    }

    private fun openAppSettingsScreen(activity: Context) {
        Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${activity.packageName}")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(this)
        }
    }
}
