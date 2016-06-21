package ca.gabrielcastro.openotp.app

import dagger.Module
import dagger.Provides

class ReleaseInitializer : Initializer {
    override fun init(app: App) {
    }
}

@Module
class InitializerModule() {
    val initializer: Initializer
        @Provides get() = ReleaseInitializer()
}
