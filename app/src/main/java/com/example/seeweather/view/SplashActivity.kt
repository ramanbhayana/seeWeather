package com.example.seeweather.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.example.seeweather.R
import com.example.seeweather.dagger.components.ActivityComponent
import com.example.seeweather.databinding.ActivityMainBinding
import com.example.seeweather.mvvm.BaseActivity
import com.example.seeweather.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_splash.*

@Suppress("DEPRECATION")
class SplashActivity : BaseActivity<MainActivityViewModel>() {
    var dataBinding: ActivityMainBinding? = null

    override fun setDataBindingLayout() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        dataBinding?.mainActivityViewModel = viewModel
        dataBinding?.lifecycleOwner = this
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)

    }

    override fun setupView(savedInstanceState: Bundle?) {
        Handler().postDelayed({
            iv_info.scaleX = -1f
            Handler().postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }, 500)
        }, 1000)

    }

}