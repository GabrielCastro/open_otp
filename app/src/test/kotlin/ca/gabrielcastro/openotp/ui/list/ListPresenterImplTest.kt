package ca.gabrielcastro.openotp.ui.list

import ca.gabrielcastro.openotp.testutils.FakeDatabase
import ca.gabrielcastro.openotp.testutils.RxAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


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
        presenter.init(view)
        presenter.resume()

        verify(view).showItems(anyListOf(ListContract.ListItem::class.java))

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
