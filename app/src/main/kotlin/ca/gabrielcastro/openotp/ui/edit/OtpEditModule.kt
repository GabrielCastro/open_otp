package ca.gabrielcastro.openotp.ui.edit

import ca.gabrielcastro.openotp.ui.detail.OtpDetailActivity
import ca.gabrielcastro.openotp.ui.detail.OtpDetailContract
import ca.gabrielcastro.openotp.ui.detail.OtpDetailPresenterImpl
import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@Module
class OtpEditModule {

    @Provides
    internal fun providePresenter(impl: OtpEditPresenterImpl): OtpEditContract.Presenter {
        return impl
    }

}

@Subcomponent(
        modules = arrayOf(OtpEditModule::class)
)
interface OtpEditComponent {
    fun inject(activity: OtpEditActivity)
}
