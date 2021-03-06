package ca.gabrielcastro.openotp.ui.list

import ca.gabrielcastro.openotp.testutils.FakeDatabase
import ca.gabrielcastro.openotp.testutils.RxAndroidRule
import ca.gabrielcastro.openotp.ui.detail.OtpDetailContract
import ca.gabrielcastro.openotp.ui.detail.OtpDetailPresenterImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import rx.schedulers.TestScheduler
import java.util.concurrent.TimeUnit


class DetailPresenterImplTest {

    @Rule
    @JvmField
    val rxRule = RxAndroidRule()

    private var _db: FakeDatabase? = null
    private var _view: OtpDetailContract.View? = null
    private var _presenter: OtpDetailContract.Presenter? = null
    val db: FakeDatabase get() = _db!!
    val view: OtpDetailContract.View get() = _view!!
    val presenter: OtpDetailContract.Presenter get() = _presenter!!

    @Before
    fun setUp() {
        _db = FakeDatabase()
        _view = Mockito.mock(OtpDetailContract.View::class.java)
        _presenter = OtpDetailPresenterImpl(_db!!)
    }

    @Test
    fun name() {
        val io = TestScheduler()
        rxRule.io = io
        val firstId = db.items[0].uuid
        presenter.init(view, firstId)

        presenter.resume()
        io.advanceTimeBy(30, TimeUnit.SECONDS)
        verify(view).showAccountName("john@gmail.com")
        verify(view).showIssuer("Google Inc.")

        presenter.pause()
    }
}
