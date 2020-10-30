package com.example.seeweather.view

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.seeweather.R
import com.example.seeweather.commonUtils.common.CommonUtils
import com.example.seeweather.dagger.components.ActivityComponent
import com.example.seeweather.databinding.ActivityMainBinding
import com.example.seeweather.mvvm.BaseActivity
import com.example.seeweather.viewModel.MainActivityViewModel
import com.github.gcacace.signaturepad.views.SignaturePad
import kotlinx.android.synthetic.main.activity_form.*


class FormActivity : BaseActivity<MainActivityViewModel>() {
    var dataBinding: ActivityMainBinding? = null
    override fun setDataBindingLayout() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_form)
        dataBinding?.mainActivityViewModel = viewModel
        dataBinding?.lifecycleOwner = this
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
        //disable both buttons at start


    }

    override fun setupView(savedInstanceState: Bundle?) {
        CommonUtils.bitmap = null
        //disable both buttons at start
        saveButton.isEnabled = false
        clearButton.isEnabled = false

        //change screen orientation to landscape mode

        //change screen orientation to landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        signaturePad.setOnSignedListener(object : SignaturePad.OnSignedListener {
            override fun onStartSigning() {}
            override fun onSigned() {
                saveButton.isEnabled = true
                clearButton.isEnabled = true
            }

            override fun onClear() {
                saveButton.isEnabled = false
                clearButton.isEnabled = false
            }
        })

        saveButton.setOnClickListener {
            CommonUtils.bitmap = signaturePad.transparentSignatureBitmap;
            Toast.makeText(this@FormActivity, "Signature Saved", Toast.LENGTH_SHORT).show()
            finish()

        }
        clearButton.setOnClickListener {
            CommonUtils.bitmap = null
            signaturePad.clear()
        }
    }


}

