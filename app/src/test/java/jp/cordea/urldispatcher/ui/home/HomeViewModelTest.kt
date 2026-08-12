package jp.cordea.urldispatcher.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.MainDispatcherRule
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.filter
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

    private lateinit var viewModel: HomeViewModel
    private lateinit var urlsFlow: MutableStateFlow<List<Url>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        urlsFlow = MutableStateFlow(SEED)
        every { repository.getUrls() } returns urlsFlow
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun uiState_derivesSchemesAndTotalCount() = runTest {
        val state = viewModel.uiState.filter { !it.isLoading }.first()

        assertThat(state.totalCount).isEqualTo(3)
        assertThat(state.schemes).containsExactly("https", "intent", "myapp").inOrder()
        assertThat(state.items).hasSize(3)
        assertThat(state.selectedScheme).isNull()
    }

    @Test
    fun selectScheme_filtersItems() = runTest {
        viewModel.selectScheme("https")

        val state = viewModel.uiState
                .filter { !it.isLoading && it.selectedScheme == "https" }
                .first()

        assertThat(state.items.map { it.scheme }).containsExactly("https")
        assertThat(state.totalCount).isEqualTo(3)
    }

    @Test
    fun selectScheme_clearsWhenSchemeDisappears() = runTest {
        viewModel.selectScheme("myapp")

        viewModel.uiState
                .filter { !it.isLoading && it.selectedScheme == "myapp" }
                .first()

        urlsFlow.value = SEED.filterNot { it.url.startsWith("myapp") }

        val state = viewModel.uiState.filter { it.selectedScheme == null }.first()
        assertThat(state.items.map { it.scheme }).doesNotContain("myapp")
    }

    @Test
    fun onItemClick_emitsOpenLinkEvent() = runTest {
        val item = viewModel.uiState.filter { !it.isLoading }.first().items.first()

        viewModel.onItemClick(item)
        val event = viewModel.events.first()

        assertThat(event).isInstanceOf(HomeEvent.OpenLink::class.java)
        assertThat((event as HomeEvent.OpenLink).item.id).isEqualTo(item.id)
    }

    @Test
    fun deleteLink_forwardsToRepository() = runTest {
        coEvery { repository.deleteUrl(any()) } returns Unit

        viewModel.deleteLink(42L)

        coVerify { repository.deleteUrl(42L) }
    }

    companion object {
        private val SEED = listOf(
                Url(1L, "https://example.com/a", "a", 1_700_000_000_000L, DispatchType.DEFAULT),
                Url(2L, "myapp://x", "b", 1_700_000_001_000L, DispatchType.DEFAULT),
                Url(3L, "intent://y#Intent;end", "c", 1_700_000_002_000L, DispatchType.CHOOSER)
        )
    }
}
