package ca.gabrielcastro.openotp.ui.detail

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class OtpDetailModule {

    @Provides
    internal fun providePresenter(impl: OtpDetailPresenterImpl): OtpDetailContract.Presenter {
        return impl
    }

}

@Subcomponent(
        modules = arrayOf(OtpDetailModule::class)
)
interface OtpDetailComponent {
    fun inject(activity: OtpDetailActivity)
}
