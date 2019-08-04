package jp.cordea.urldispatcher.edit

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Completable
import io.reactivex.Maybe
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditViewModelTest {
    @MockK
    private lateinit var repository: UrlRepository
    @InjectMockKs
    private lateinit var viewModel: EditViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun init() {
        every { repository.findUrl(1L) } answers { Maybe.just(mockk()) }

        val observer = mockk<Observer<Url>>(relaxed = true)
        viewModel.url.observeForever(observer)
        viewModel.init(1L)

        verify { observer.onChanged(any()) }
        viewModel.url.removeObserver(observer)
    }

    @Test
    fun init_add() {
        viewModel.init(0L)

        verify(exactly = 0) { repository.findUrl(any()) }
    }

    @Test
    fun trySaveUrl() {
        val slot = slot<Url>()
        every { repository.insertUrl(capture(slot)) } answers { Completable.complete() }

        viewModel.trySaveUrl("http://example.com", "description")

        val url = slot.captured
        assertThat(url.id).isEqualTo(0L)
        assertThat(url.url).isEqualTo("http://example.com")
        assertThat(url.description).isEqualTo("description")
    }

    @Test
    fun trySaveUrl_description_null() {
        val slot = slot<Url>()
        every { repository.insertUrl(capture(slot)) } answers { Completable.complete() }

        viewModel.trySaveUrl("http://example.com", null)

        val url = slot.captured
        assertThat(url.description).isEmpty()
    }

    @Test
    fun trySaveUrl_blank() {
        viewModel.trySaveUrl("  ", null)

        verify(exactly = 0) { repository.insertUrl(any()) }
    }
}
