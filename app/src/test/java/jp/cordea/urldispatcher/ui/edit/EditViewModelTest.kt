package jp.cordea.urldispatcher.ui.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import jp.cordea.urldispatcher.DispatchType
import jp.cordea.urldispatcher.MainDispatcherRule
import jp.cordea.urldispatcher.Url
import jp.cordea.urldispatcher.UrlRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
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

    @Before
    fun setUp() = MockKAnnotations.init(this)

    private fun newViewModel(id: Long = 0L) = EditViewModel(repository, id)

    @Test
    fun create_mode_startsEmpty() = runTest {
        val vm = newViewModel()

        val state = vm.uiState.value
        assertThat(state.isEditMode).isFalse()
        assertThat(state.url).isEmpty()
        assertThat(state.description).isEmpty()
        assertThat(state.dispatchType).isEqualTo(DispatchType.DEFAULT)
    }

    @Test
    fun edit_mode_loadsExisting() = runTest {
        coEvery { repository.findUrl(7L) } returns Url(
                id = 7L,
                url = URL,
                description = DESCRIPTION,
                addedAt = 1_700_000_000_000L,
                dispatchType = DispatchType.CHOOSER
        )
        val vm = newViewModel(id = 7L)

        val state = vm.uiState.filter { !it.isLoading && it.url.isNotEmpty() }.first()
        assertThat(state.isEditMode).isTrue()
        assertThat(state.url).isEqualTo(URL)
        assertThat(state.description).isEqualTo(DESCRIPTION)
        assertThat(state.dispatchType).isEqualTo(DispatchType.CHOOSER)
    }

    @Test
    fun onFieldChanges_updateState() = runTest {
        val vm = newViewModel()
        vm.onUrlChange(URL)
        vm.onDescriptionChange(DESCRIPTION)
        vm.onDispatchTypeChange(DispatchType.BROWSER)

        val state = vm.uiState.value
        assertThat(state.url).isEqualTo(URL)
        assertThat(state.description).isEqualTo(DESCRIPTION)
        assertThat(state.dispatchType).isEqualTo(DispatchType.BROWSER)
    }

    @Test
    fun save_emitsSavedAndPersistsUrl() = runTest {
        val slot = slot<Url>()
        coEvery { repository.insertUrl(capture(slot)) } returns Unit
        val vm = newViewModel()
        vm.onUrlChange(URL)
        vm.onDescriptionChange(DESCRIPTION)
        vm.onDispatchTypeChange(DispatchType.CHOOSER)

        vm.save()
        val event = vm.events.first()

        assertThat(event).isEqualTo(EditEvent.Saved)
        coVerify { repository.insertUrl(any()) }
        val saved = slot.captured
        assertThat(saved.url).isEqualTo(URL)
        assertThat(saved.description).isEqualTo(DESCRIPTION)
        assertThat(saved.dispatchType).isEqualTo(DispatchType.CHOOSER)
    }

    @Test
    fun save_blankUrl_emitsEmptyUrlError() = runTest {
        val vm = newViewModel()
        vm.onUrlChange("   ")

        vm.save()
        val event = vm.events.first()

        assertThat(event).isEqualTo(EditEvent.Error(EditError.EMPTY_URL))
        coVerify(exactly = 0) { repository.insertUrl(any()) }
    }

    @Test
    fun save_repositoryFailure_emitsSaveFailedError() = runTest {
        coEvery { repository.insertUrl(any()) } throws IllegalStateException("nope")
        val vm = newViewModel()
        vm.onUrlChange(URL)

        vm.save()
        val event = vm.events.first()

        assertThat(event).isEqualTo(EditEvent.Error(EditError.SAVE_FAILED))
    }

    companion object {
        private const val URL = "http://example.com"
        private const val DESCRIPTION = "description"
    }
}
