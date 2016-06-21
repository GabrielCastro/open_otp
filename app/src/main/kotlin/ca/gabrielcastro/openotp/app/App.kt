package ca.gabrielcastro.openotp.app

import android.app.Application
import android.content.Context
import javax.inject.Inject


class App : Application() {

    companion object {
        fun component(context: Context): AppComponent {
            return (context.applicationContext as App).component
        }
    }

    lateinit var component: AppComponent
        private set

    @set:Inject
    lateinit var initilizer: Initializer

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        component.inject(this)
        initilizer.init(this)
    }
}
