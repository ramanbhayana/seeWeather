package com.example.seeweather.view

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.seeweather.R
import com.example.seeweather.commonUtils.common.Resource
import com.example.seeweather.dagger.components.ActivityComponent
import com.example.seeweather.databinding.ActivityMainBinding
import com.example.seeweather.mvvm.BaseActivity
import com.example.seeweather.scheduler.WeatherDataDeleteJobService
import com.example.seeweather.viewModel.MainActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<MainActivityViewModel>() {
    private var mMenu: Menu? = null
    var dataBinding: ActivityMainBinding? = null
    private val latency_Time = 86400000 //24 hours

    override fun setDataBindingLayout() {
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding?.mainActivityViewModel = viewModel
        dataBinding?.lifecycleOwner = this
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        dataBinding?.let {
            with(it, {

                etSearchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        callSearch(query)
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Do Nothing
                        return true
                    }

                    fun callSearch(query: String?) {
                        query?.let { it1 -> getWeatherByCityName(it1) }
                        etSearchCity.setQuery("", false);
                        etSearchCity.clearFocus();
                    }
                })
            })
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setupObservers() {
        super.setupObservers()
        viewModel.weatherLiveData.observe(this, Observer {
            if (it != null) {
                dataBinding?.bodyScrollLayout?.visibility = View.VISIBLE
                viewModel.setLiveData(it)
            } else {
                // Do Nothing
            }
        })

        viewModel.generateJobIdLiveData.observe(this, Observer {

            viewModel.jobId = (1..10000).random()
            while (isJobServiceOn(viewModel.jobId)) {
                viewModel.jobId = (1..10000).random()
            }
            viewModel.insertAndSetWeatherData(it)
            scheduleJobToDelete(viewModel.jobId)
        })
    }

    // Method to Schedule Recurring Service to Delete the Save cities after every 24 hours
    @SuppressLint("NewApi")
    private fun scheduleJobToDelete(jobId: Int) {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val serviceComponent = ComponentName(this, WeatherDataDeleteJobService::class.java)
        val builder = JobInfo.Builder(
            jobId,
            serviceComponent
        )
        builder.setMinimumLatency(latency_Time.toLong())
        scheduler.schedule(builder.build())
    }

    // Method to Get Weather By City Query
    private fun getWeatherByCityName(query: String) {
        hideKeyboard()
        if (query.isNotEmpty()) {
            // Background call to get Weather data
            CoroutineScope(IO).launch {
                viewModel.getWeatherData(dataBinding?.etSearchCity?.query.toString())
            }
        } else {
            viewModel.messageString.postValue(Resource.error(getString(R.string.msg_please_enter_city_name)))
        }
    }

}