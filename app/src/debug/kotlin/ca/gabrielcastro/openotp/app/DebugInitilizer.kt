package ca.gabrielcastro.openotp.app

import com.squareup.leakcanary.LeakCanary
import dagger.Module
import dagger.Provides

class DebugInitializer : Initializer {
    override fun init(app: App) {
        LeakCanary.install(app)
    }
}

@Module
class InitializerModule() {
    val initializer: Initializer
        @Provides get() = DebugInitializer()
}
