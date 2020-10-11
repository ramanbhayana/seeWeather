package com.example.seeweather.mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.app.job.JobScheduler
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.seeweather.dagger.modules.ActivityModule
import com.example.seeweather.application.WeatherApplication
import com.example.seeweather.commonUtils.common.LoadingDialog
import com.example.seeweather.dagger.components.ActivityComponent
import com.example.seeweather.dagger.components.DaggerActivityComponent
import javax.inject.Inject


abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {


    @Inject
    lateinit var viewModel: VM

//    private var mIdlingResource: SimpleIdlingResource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildActivityComponent())
        super.onCreate(savedInstanceState)
        setDataBindingLayout()
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onCreate()
    }


    private fun buildActivityComponent() =
        DaggerActivityComponent
            .builder()
            .applicationComponent((application as WeatherApplication).applicationComponent)
            .activityModule(ActivityModule(this))
            .build()


    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.showDialog.observe(this, Observer {
            showLoadingDialog(it)
        })
    }

    private fun showLoadingDialog(it: Boolean) {
        if (it) {
            LoadingDialog.showDialog()
        } else {
            LoadingDialog.dismissDialog()
        }
    }

    private fun showMessage(message: String) = show(applicationContext, message)

    private fun show(context: Context, text: CharSequence) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStackImmediate()
        else super.onBackPressed()
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("NewApi")
    open fun isJobServiceOn(JOB_ID: Int): Boolean {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        var hasBeenScheduled = false
        for (jobInfo in scheduler.allPendingJobs) {
            if (jobInfo.id == JOB_ID) {
                hasBeenScheduled = true
                break
            }
        }
        return hasBeenScheduled
    }

    /**
     * Only called from test, creates and returns a new [SimpleIdlingResource].
     */
//    @VisibleForTesting
//    open fun getIdlingResource(): IdlingResource? {
//        if (mIdlingResource == null) {
//            mIdlingResource = SimpleIdlingResource()
//        }
//        return mIdlingResource
//    }

    protected abstract fun setDataBindingLayout()

    protected abstract fun injectDependencies(activityComponent: ActivityComponent)

    protected abstract fun setupView(savedInstanceState: Bundle?)

}