package com.example.seeweather.commonUtils.permissions

interface ResponsePermissionCallback {
    fun onResult(permissionResult: List<String>)
}