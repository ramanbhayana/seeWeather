package com.example.seeweather.commonUtils.common

import android.graphics.Bitmap
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils() {

    companion object {
        fun getDateOrTimeFromSpecificFormat(
            format: String,
            date: Long
        ): String? {
            return try {

                val sdf1 = SimpleDateFormat(format)
                sdf1.timeZone = TimeZone.getDefault()
                sdf1.format(Date(date).time)
            } catch (e: ParseException) {
                Timber.e(e)
                ""
            }
        }


         var bitmap: Bitmap? = null

    }

}