package ca.gabrielcastro.openotp.ui.base


interface BasePresenter {
    fun resume()
    fun pause()
}

interface BaseView {
    fun finish(ok :Boolean)
}

abstract class BasePresenterImpl : BasePresenter {
    override fun resume() {}
    override fun pause() {}
}
