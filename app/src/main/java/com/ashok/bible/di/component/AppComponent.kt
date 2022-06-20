package com.ashok.bible.di.component

import android.app.Application
import com.ashok.bible.App
import com.ashok.bible.di.builder.ActivityBuilderModule
import com.ashok.bible.di.module.AppModule
import com.ashok.bible.di.module.RoomModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (AndroidInjectionModule::class), (ActivityBuilderModule::class)])
public interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        abstract fun application(application: Application): Builder
        abstract fun build(): AppComponent
    }

    abstract fun inject(app: App)
}