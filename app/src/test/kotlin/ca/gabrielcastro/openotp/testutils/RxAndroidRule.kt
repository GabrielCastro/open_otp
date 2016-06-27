package ca.gabrielcastro.openotp.testutils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.plugins.RxJavaPlugins
import rx.plugins.RxJavaSchedulersHook
import rx.schedulers.Schedulers


class RxAndroidRule : TestRule {

    private fun setSchedulers() {
        val immediate = Schedulers.immediate()

        RxJavaPlugins.getInstance().registerSchedulersHook(object : RxJavaSchedulersHook() {
            override fun getIOScheduler(): Scheduler? {
                return immediate
            }

            override fun getComputationScheduler(): Scheduler? {
                return immediate
            }

            override fun getNewThreadScheduler(): Scheduler? {
                return immediate
            }
        })

        RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
            override fun getMainThreadScheduler(): Scheduler {
                return immediate
            }
        })
    }

    private fun resetSchedulers() {
        RxJavaPlugins.getInstance().reset()
        RxAndroidPlugins.getInstance().reset()
    }

    override fun apply(base: Statement, description: Description?): Statement? {
        return object : Statement() {
            override fun evaluate() {
                setSchedulers()
                try {
                    base.evaluate()
                } finally {
                    resetSchedulers()
                }
            }
        }
    }

}
