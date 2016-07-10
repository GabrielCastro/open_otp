package ca.gabrielcastro.openotp.app

import android.os.Build
import com.facebook.stetho.Stetho
import com.facebook.stetho.timber.StethoTree
import com.squareup.leakcanary.LeakCanary
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import dagger.Module
import dagger.Provides
import timber.log.Timber

private fun isRoboUnitTest(): Boolean {
    return "robolectric".equals(Build.FINGERPRINT);
}

class DebugInitializer : Initializer {
    override fun init(app: App) {
        Timber.plant(Timber.DebugTree())
        Timber.d("init started")
        if (isRoboUnitTest()) {
            return
        }
        if (LeakCanary.isInAnalyzerProcess(app)) {
            return
        }
        LeakCanary.install(app)
        Timber.d("leak canary done")
        Stetho.initialize(
                Stetho.newInitializerBuilder(app)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(app))
                        .enableWebKitInspector(
                                RealmInspectorModulesProvider.builder(app)
                                        .withMetaTables()
                                        .baseProvider(Stetho.defaultInspectorModulesProvider(app))
                                        .build()
                        )
                        .build()
        )

        Timber.plant(StethoTree())
        Timber.d("init done")
    }
}

@Module
class InitializerModule() {
    val initializer: Initializer
        @Provides get() = DebugInitializer()
}
