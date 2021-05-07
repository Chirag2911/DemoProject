package com.dynamo.chiragtest.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dynamo.chiragtest.model.RocketData
import com.dynamo.chiragtest.network.CallResponseStatus
import com.dynamo.chiragtest.network.NetworkManager
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RocketViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var networkManager: NetworkManager


    @Mock
    private lateinit var viewStateObserver: Observer<CallResponseStatus<List<RocketData>>>

    private lateinit var viewModel: RocketViewModel


    @Before
    fun setUp() {

        networkManager = NetworkManager()
        viewModel = RocketViewModel().apply {
            getRockets().observeForever(viewStateObserver)
        }
    }


    @Mock
    private lateinit var apiUsersObserver: Observer<CallResponseStatus<List<RocketData>>>

    @Test
    fun validateLiveData() {
        val rocketLiveData = MutableLiveData<List<RocketData>>()
        rocketLiveData.postValue(emptyList<RocketData>())
        TestCase.assertEquals(emptyList<RocketData>(), rocketLiveData.value)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() = runBlocking {
        val data = emptyList<RocketData>()
        Mockito.`when`(networkManager.getRepositories()).thenReturn(data)
        viewModel.getRocketList()
        Mockito.verify(viewStateObserver).onChanged(CallResponseStatus.success(emptyList()))
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() = runBlocking {
        val errorMessage = "Error Message For You"
        Mockito.`when`(networkManager.getRepositories()).thenReturn(emptyList<RocketData>())

        Mockito.doThrow(RuntimeException(errorMessage))
            .`when`(networkManager)
            .getRepositories()
        val viewModel = RocketViewModel()
        viewModel.getRockets().observeForever(apiUsersObserver)
        Mockito.verify(networkManager).getRepositories()
        Mockito.verify(apiUsersObserver).onChanged(
            CallResponseStatus.error(RuntimeException(errorMessage))
        )
        viewModel.getRockets().removeObserver(apiUsersObserver)
    }


}