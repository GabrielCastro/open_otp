package ca.gabrielcastro.openotp.app

import android.os.Build
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import dagger.Module
import dagger.Provides
import timber.log.Timber

private fun isRoboUnitTest() : Boolean {
    return "robolectric".equals(Build.FINGERPRINT);
}

class DebugInitializer : Initializer {
    override fun init(app: App) {
        Timber.plant(Timber.DebugTree())
        if (isRoboUnitTest()) {
            return
        }
        LeakCanary.install(app)
        Stetho.initializeWithDefaults(app)
    }
}

@Module
class InitializerModule() {
    val initializer: Initializer
        @Provides get() = DebugInitializer()
}
