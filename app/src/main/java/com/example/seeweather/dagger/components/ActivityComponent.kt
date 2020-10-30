package com.example.seeweather.dagger.components

import com.example.seeweather.dagger.ActivityScope
import com.example.seeweather.dagger.modules.ActivityModule
import com.example.seeweather.view.*
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent{
    fun inject(mainActivity: MainActivity)
    fun inject(formActivity: FormActivity)
    fun inject(formFillActivity: FormFillActivity)
    fun inject(splashActivity: SplashActivity)
    fun inject(loginActivity: LoginActivity)


}