package jp.cordea.urldispatcher.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import jp.cordea.urldispatcher.MainDispatcherRule
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class EditViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var repository: UrlRepository

    @InjectMockKs
    private lateinit var viewModel: EditViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun init() = runTest {
        val url = mockk<Url> {
            every { url } returns URL
            every { description } returns DESCRIPTION
        }
        coEvery { repository.findUrl(1L) } returns url

        viewModel.init(1L)

        assertThat(viewModel.url.get()).isEqualTo(URL)
        assertThat(viewModel.description.get()).isEqualTo(DESCRIPTION)
    }

    @Test
    fun init_add() = runTest {
        viewModel.init(0L)

        coVerify(exactly = 0) { repository.findUrl(any()) }
    }

    @Test
    fun trySaveUrl() = runTest {
        val slot = slot<Url>()
        coEvery { repository.insertUrl(capture(slot)) } returns Unit

        viewModel.url.set(URL)
        viewModel.description.set(DESCRIPTION)
        viewModel.trySaveUrl()

        val url = slot.captured
        assertThat(url.id).isEqualTo(0L)
        assertThat(url.url).isEqualTo(URL)
        assertThat(url.description).isEqualTo(DESCRIPTION)
    }

    @Test
    fun trySaveUrl_description_null() = runTest {
        val slot = slot<Url>()
        coEvery { repository.insertUrl(capture(slot)) } returns Unit

        viewModel.url.set(URL)
        viewModel.description.set(null)
        viewModel.trySaveUrl()

        val url = slot.captured
        assertThat(url.description).isEmpty()
    }

    @Test
    fun trySaveUrl_blank() = runTest {
        viewModel.url.set("   ")
        viewModel.description.set(null)
        viewModel.trySaveUrl()

        coVerify(exactly = 0) { repository.insertUrl(any()) }
    }

    companion object {
        private const val URL = "http://example.com"
        private const val DESCRIPTION = "description"
    }
}
