package ca.gabrielcastro.openotp.ui.base


interface BasePresenter {
    fun resume()
    fun pause()
}

abstract class BasePresenterImpl : BasePresenter {
    override fun resume() {}
    override fun pause() {}
}
