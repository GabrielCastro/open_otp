package ca.gabrielcastro.openotp.ui.list

import ca.gabrielcastro.openotp.testutils.FakeDatabase
import ca.gabrielcastro.openotp.testutils.RxAndroidRule
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class ListPresenterImplTest {

    @Rule
    @JvmField
    val rxRule = RxAndroidRule()

    @Test
    fun showsList() {
        val db = FakeDatabase()
        val view = mock(ListContract.View::class.java)

        val presenter = ListPresenterImpl(db)

        presenter.init(view)
        presenter.resume()

        verify(view).showItems(anyListOf(ListContract.ListItem::class.java))

        presenter.pause()
    }
}
