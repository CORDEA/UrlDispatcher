package jp.cordea.urldispatcher.home

import android.os.Looper
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import jp.cordea.urldispatcher.UrlRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @MockK
    private lateinit var repository: UrlRepository
    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun refresh() {
        every { repository.getUrls() } answers { Single.just(listOf(mockk(relaxed = true))) }

        val observer = mockk<Observer<List<HomeListItemModel>>>(relaxed = true)
        viewModel.adapterItems.observeForever(observer)

        viewModel.refresh()
        shadowOf(Looper.getMainLooper()).idle();

        val slot = slot<List<HomeListItemModel>>()
        verify { observer.onChanged(capture(slot)) }
        assertThat(slot.captured.size).isEqualTo(1)
        viewModel.adapterItems.removeObserver(observer)
    }
}
