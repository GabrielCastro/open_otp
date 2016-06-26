package ca.gabrielcastro.openotp.ui.list

import android.app.Instrumentation
import ca.gabrielcastro.openotp.FakeDatabase
import org.junit.Test
import org.mockito.Mockito.mock


class ListPresenterImplTest {


    @Test
    fun showsList() {
        val db = FakeDatabase()
        val view = mock(ListContract.View::class.java)

        val presenter = ListPresenterImpl(db)

        presenter.init(view)
        presenter.resume()

    }
}
