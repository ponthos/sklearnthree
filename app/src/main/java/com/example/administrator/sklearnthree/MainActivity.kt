package com.example.administrator.sklearnthree

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.RuntimePermissions
import permissions.dispatcher.NeedsPermission
import android.Manifest
import android.content.Context
import android.net.*
import android.net.ConnectivityManager.NetworkCallback
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import android.widget.Button
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnNeverAskAgain

@RuntimePermissions
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sample_text.text = stringFromJNI()
        val buttonContacts: Button = findViewById(R.id.button_contacts)
        buttonContacts.setOnClickListener {
            readWithPermissionCheck()
        }
        //检查网络情况
        val network:ConnectivityManager.NetworkCallback=NetworkCallbackImpl()
        val build:NetworkRequest.Builder=NetworkRequest.Builder()
        val request:NetworkRequest=build.build()
        val connectivityManager: ConnectivityManager ? = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.registerNetworkCallback(request,network)
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
    @NeedsPermission(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun read() {
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }
    @OnShowRationale(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun showread(request: PermissionRequest) {
        showRationaleDialog(R.string.permission_contacts_rationale,request)
    }
    @OnPermissionDenied(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun readdenied() {
    }
    @OnNeverAskAgain(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
    fun readask() {
    }
    private fun showRationaleDialog(@StringRes messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow) { _, _ -> request.proceed() }
                .setNegativeButton(R.string.button_deny) { _, _ -> request.cancel() }
                .setCancelable(false)
                .setMessage(messageResId)
                .show()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    //网络变化
    private class NetworkCallbackImpl : NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
            super.onCapabilitiesChanged(network, networkCapabilities)
        }

        override fun onLost(network: Network?) {
            super.onLost(network)
        }

        override fun onLinkPropertiesChanged(network: Network?, linkProperties: LinkProperties?) {
            super.onLinkPropertiesChanged(network, linkProperties)
        }

        override fun onUnavailable() {
            super.onUnavailable()
        }

        override fun onLosing(network: Network?, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
        }

        override fun onAvailable(network: Network?) {
            super.onAvailable(network)
        }
    }
}
