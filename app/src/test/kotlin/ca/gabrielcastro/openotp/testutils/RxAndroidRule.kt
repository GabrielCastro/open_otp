package ca.gabrielcastro.openotp.testutils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import rx.Scheduler
import rx.android.plugins.RxAndroidPlugins
import rx.android.plugins.RxAndroidSchedulersHook
import rx.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class RxAndroidRule : TestRule {

    private var executor: ExecutorService? = null

    fun waitForRunning() {
        val f = executor!!.submit {
            // nothing
        }
        f.get()
    }

    override fun apply(base: Statement, description: Description?): Statement? {
        return object : Statement() {
            override fun evaluate() {
                executor = Executors.newSingleThreadScheduledExecutor()
                val main = Schedulers.from(executor)

                RxAndroidPlugins.getInstance().registerSchedulersHook(object : RxAndroidSchedulersHook() {
                    override fun getMainThreadScheduler(): Scheduler {
                        return main
                    }
                })

                base.evaluate()

                RxAndroidPlugins.getInstance().reset()
                executor!!.shutdown()
                executor!!.awaitTermination(1, TimeUnit.SECONDS)
                executor = null
            }
        }
    }

}
