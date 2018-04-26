package com.example.administrator.sklearnthree

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.annotation.RequiresApi

object PermissionLogUtils {
    private val logStringBuffer = StringBuffer()
    @RequiresApi(Build.VERSION_CODES.M)
// 查看权限是否已申请
    fun checkPermissions(context: Context, vararg permissions: String): String {
        logStringBuffer.delete(0, logStringBuffer.length)
        for (permission in permissions) {
            logStringBuffer.append(permission)
            logStringBuffer.append(" is applied? \n     ")
            logStringBuffer.append(isAppliedPermission(context, permission))
            logStringBuffer.append("\n\n")
        }
        return logStringBuffer.toString()
    }


    //    //使用EasyPermissions查看权限是否已申请
    //    public static String easyCheckPermissions(Context context,String ... permissions) {
    //        logStringBuffer.delete(0,logStringBuffer.length());
    //        for (String permission : permissions) {
    //            logStringBuffer.append(permission);
    //            logStringBuffer.append(" is applied? \n     ");
    //            logStringBuffer.append(EasyPermissions.hasPermissions(context,permission));
    //            logStringBuffer.append("\n\n");
    //        }
    //        return logStringBuffer.toString();
    //    }


    // 查看权限是否已申请
    @RequiresApi(api = Build.VERSION_CODES.M)
    public fun isAppliedPermission(context: Context, permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}
