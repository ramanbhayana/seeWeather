package com.example.seeweather.api.network

interface InternetConnectivityListener {

    fun onInternetConnectivityChanged(isConnected: Boolean)
}