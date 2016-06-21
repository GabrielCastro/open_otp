package ca.gabrielcastro.openotp.app

import android.content.Context
import ca.gabrielcastro.openotp.db.Database
import ca.gabrielcastro.openotp.db.DatabaseModule
import ca.gabrielcastro.openotp.ui.list.OtpListComponent
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Retention(AnnotationRetention.SOURCE)
annotation class AppContext

@Module
class AppModule(
        @get:Provides val app: App
) {

    @Provides
    @AppContext
    fun provideContext(): Context {
        return app
    }
}

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        DatabaseModule::class,
        InitializerModule::class
))
interface AppComponent {

    val listComponent: OtpListComponent

    fun inject(app: App)

}
