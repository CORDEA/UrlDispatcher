package jp.cordea.urldispatcher.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import jp.cordea.urldispatcher.MainDispatcherRule
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var repository: UrlRepository
    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun refresh() = runTest {
        every { repository.getUrls() } returns flowOf(listOf(mockk(relaxed = true)))

        val observer = mockk<Observer<List<HomeListItemModel>>>(relaxed = true)
        viewModel.adapterItems.observeForever(observer)

        viewModel.refresh()

        val slot = slot<List<HomeListItemModel>>()
        verify { observer.onChanged(capture(slot)) }
        assertThat(slot.captured.size).isEqualTo(1)
        viewModel.adapterItems.removeObserver(observer)
    }
}
