package ca.gabrielcastro.openotp.ui.list

import dagger.Module
import dagger.Provides
import dagger.Subcomponent


@Module
class OtpListModule {

    @Provides
    internal fun providePresenter(impl: ListPresenterImpl): ListContract.Presenter {
        return impl
    }

}

@Subcomponent(
        modules = arrayOf(OtpListModule::class)
)
interface OtpListComponent {
    fun inject(activity: OtpListActivity)
}
