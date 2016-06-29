package ca.gabrielcastro.openotp.ui.scan

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class ScanModule {

    @Provides
    internal fun providePresenter(impl: ScanPresenterImpl): ScanContract.Presenter {
        return impl
    }

}

@Subcomponent(
        modules = arrayOf(ScanModule::class)
)
interface ScanComponent {
    fun inject(activity: BarcodeScanActivity)
}
