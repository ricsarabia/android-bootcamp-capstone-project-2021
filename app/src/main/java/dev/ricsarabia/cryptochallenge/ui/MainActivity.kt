package dev.ricsarabia.cryptochallenge.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.santander.globile.coreapp.permissionmanager.PermissionHandler
import com.santander.globile.coreapp.permissionmanager.PermissionHandlerDelegate
import dagger.hilt.android.AndroidEntryPoint
import dev.ricsarabia.cryptochallenge.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var requestPermissions: PermissionHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportActionBar?.hide()
        requestAppPermissions()
    }

    private fun requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions = PermissionHandler(object : PermissionHandlerDelegate {
                override fun onPermissionsGranted() {
                    Toast.makeText(this@MainActivity, "onPermissionsGranted", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPermissionsDenied() {
                    requestPermissions?.launchMessageForPermissionsDenied(this@MainActivity)
                }

                override fun onErrorGettingPermissions() {
                    Toast.makeText(
                        this@MainActivity,
                        "onErrorGettingPermissions",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun getActivityForPermissionRequest(): Activity {
                    return this@MainActivity
                }
            })
            requestPermissions?.checkPermissions(this)
        } else {
            Toast.makeText(this, "no aprovation needed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        requestPermissions?.validatePermissions(permissions, grantResults)
    }
}
