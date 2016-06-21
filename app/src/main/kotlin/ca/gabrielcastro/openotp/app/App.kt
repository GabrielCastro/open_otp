package ca.gabrielcastro.openotp.app

import android.app.Application
import android.content.Context


class App : Application() {

    companion object {
        fun component(context: Context): AppComponent {
            return (context.applicationContext as App).component
        }
    }

    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
