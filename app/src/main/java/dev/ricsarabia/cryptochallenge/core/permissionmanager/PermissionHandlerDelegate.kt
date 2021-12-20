package com.santander.globile.coreapp.permissionmanager

import android.app.Activity

interface PermissionHandlerDelegate{
    fun onPermissionsGranted()
    fun onPermissionsDenied()
    fun onErrorGettingPermissions()
    fun getActivityForPermissionRequest(): Activity
}