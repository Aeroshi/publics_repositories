package com.aeroshi.repositories.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aeroshi.repositories.model.repository.GitRepository
import com.aeroshi.repositories.util.GitUtil
import com.aeroshi.repositories.util.StringUtil
import com.aeroshi.repositories.util.TrampolineSchedulerProvider
import com.aeroshi.repositories.util.enuns.ErrorType
import io.reactivex.Single
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class HomeViewModelTest {

    companion object {
        private var mJsonSuccess: String? = null
        private var mJsonError: String? = null


        @BeforeClass
        @JvmStatic
        fun executeOnce() {
            this::class.java.classLoader?.let { classLoader ->
                mJsonSuccess = classLoader.getResource("json/successReturn.json").readText()
                mJsonError = classLoader.getResource("json/errorReturn.json").readText()
            }
        }
    }

    @get:Rule
    var mRule: TestRule = InstantTaskExecutorRule()

    private val context = Mockito.mock(Context::class.java)


    @Mock
    lateinit var mGitRepository: GitRepository



    private lateinit var mViewModel: HomeViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        mViewModel =
            HomeViewModel(
                mGitRepository,
                TrampolineSchedulerProvider()
            )
    }

    @Test
    fun HomeViewModel_doPubicRepositories_deve_retorna_sucesso() {
        // Preparing
        val fakeJsonReturn = mJsonSuccess
            ?: StringUtil.EMPTY

        // Mock
        Mockito.`when`(mGitRepository.doPubicRepositories(0))
            .thenReturn(Single.just(fakeJsonReturn))

        // Call
        mViewModel.doPublicRepositories(context)

        // Assert
        verify(mGitRepository, times(1)).doPubicRepositories(0)

        Assert.assertNotNull(mViewModel.mRepositories.value)
    }

    @Test
    fun HomeViewModel_doPubicRepositories_deve_retorna_erro_parser() {
        // Preparing
        val fakeJsonReturn = mJsonError
            ?: StringUtil.EMPTY

        // Mock
        Mockito.`when`(mGitRepository.doPubicRepositories(0))
            .thenReturn(Single.just(fakeJsonReturn))


        // Call
        mViewModel.doPublicRepositories(context)

        // Assert
        verify(mGitRepository, times(1)).doPubicRepositories(0)

        Assert.assertEquals(ErrorType.PARSER, mViewModel.mError.value)
    }


    @Test
    fun HomeViewModel_since_deve_retornar_369() {
        val fakeJsonReturn = mJsonSuccess
            ?: StringUtil.EMPTY

        mViewModel.mRepositories.value = GitUtil.repositoriesJsonParser(fakeJsonReturn)

        Assert.assertEquals(369, mViewModel.getSince())
    }

}