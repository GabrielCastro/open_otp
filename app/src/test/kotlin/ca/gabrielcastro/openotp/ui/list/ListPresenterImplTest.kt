package ca.gabrielcastro.openotp.ui.list

import ca.gabrielcastro.openotp.testutils.FakeDatabase
import ca.gabrielcastro.openotp.testutils.RxAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import rx.schedulers.TestScheduler
import java.util.concurrent.TimeUnit


class ListPresenterImplTest {

    @Rule
    @JvmField
    val rxRule = RxAndroidRule()

    private var _db: FakeDatabase? = null
    private var _view: ListContract.View? = null
    private var _presenter: ListContract.Presenter? = null
    val db: FakeDatabase get() = _db!!
    val view: ListContract.View get() = _view!!
    val presenter: ListContract.Presenter get() = _presenter!!

    @Before
    fun setUp() {
        _db = FakeDatabase()
        _view = mock(ListContract.View::class.java)
        _presenter = ListPresenterImpl(db)
    }

    @Test
    fun showsList() {
        val sch = TestScheduler()
        rxRule.io = sch
        presenter.init(view)
        presenter.resume()
        sch.advanceTimeBy(1, TimeUnit.SECONDS)
        presenter.pause()
    }

    @Test
    fun showsDetail() {
        presenter.init(view)

        val item = ListContract.ListItem(
                "123456789",
                "issuer",
                "account"
        )
        presenter.itemSelected(item)

        verify(view).showDetailForId("123456789")
    }
}
