package com.example.seeweather.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.seeweather.R
import com.example.seeweather.dagger.components.ActivityComponent
import com.example.seeweather.databinding.ActivityMainBinding
import com.example.seeweather.mvvm.BaseActivity
import com.example.seeweather.viewModel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<MainActivityViewModel>() {
    var dataBinding: ActivityMainBinding? = null

    override fun setDataBindingLayout() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        dataBinding?.mainActivityViewModel = viewModel
        dataBinding?.lifecycleOwner = this
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }


    override fun setupView(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Login"
        btn.setOnClickListener {
            val intent = Intent(this, FormFillActivity::class.java)
            startActivity(intent)
        }
    }

}