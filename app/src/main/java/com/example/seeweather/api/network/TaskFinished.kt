package com.example.seeweather.api.network

interface TaskFinished<T> {
    fun onTaskFinished(data: T)
}