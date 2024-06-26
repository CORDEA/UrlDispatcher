package jp.cordea.urldispatcher.edit

import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Maybe
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf

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
        val url = mockk<Url> {
            every { url } returns URL
            every { description } returns DESCRIPTION
        }
        every { repository.findUrl(1L) } answers { Maybe.just(url) }

        viewModel.init(1L)
        shadowOf(Looper.getMainLooper()).idle();

        assertThat(viewModel.url.get()).isEqualTo(URL)
        assertThat(viewModel.description.get()).isEqualTo(DESCRIPTION)
    }

    @Test
    fun init_add() {
        viewModel.init(0L)
        shadowOf(Looper.getMainLooper()).idle();

        verify(exactly = 0) { repository.findUrl(any()) }
    }

    @Test
    fun trySaveUrl() {
        val slot = slot<Url>()
        every { repository.insertUrl(capture(slot)) } answers { Completable.complete() }

        viewModel.url.set(URL)
        viewModel.description.set(DESCRIPTION)
        viewModel.trySaveUrl()
        shadowOf(Looper.getMainLooper()).idle();

        val url = slot.captured
        assertThat(url.id).isEqualTo(0L)
        assertThat(url.url).isEqualTo(URL)
        assertThat(url.description).isEqualTo(DESCRIPTION)
    }

    @Test
    fun trySaveUrl_description_null() {
        val slot = slot<Url>()
        every { repository.insertUrl(capture(slot)) } answers { Completable.complete() }

        viewModel.url.set(URL)
        viewModel.description.set(null)
        viewModel.trySaveUrl()
        shadowOf(Looper.getMainLooper()).idle();

        val url = slot.captured
        assertThat(url.description).isEmpty()
    }

    @Test
    fun trySaveUrl_blank() {
        viewModel.url.set("   ")
        viewModel.description.set(null)
        viewModel.trySaveUrl()
        shadowOf(Looper.getMainLooper()).idle();

        verify(exactly = 0) { repository.insertUrl(any()) }
    }

    companion object {
        private const val URL = "http://example.com"
        private const val DESCRIPTION = "description"
    }
}
