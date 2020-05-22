
package com.example.news.main.observer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.news.main.repo.MainRepository
import com.example.news.main.repo.MainRepositoryTest
import com.example.news.model.ArticlesItem
import com.example.news.util.mock
import com.example.news.util.remoteUtils.Resource
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import java.util.*

@RunWith(JUnit4::class)
class DashboardViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mock(MainRepository::class.java)
    private var repoViewModel = DashboardViewModel(repository)

    @Test
    fun testNull() {
        assertThat(repoViewModel.repo, notNullValue())
        assertThat(repoViewModel.news, notNullValue())
        verify(repository, never()).fetchNewsItems()
    }

    @Test
    fun dontFetchWithoutObservers() {
        repoViewModel.init()
        verify(repository, never()).fetchNewsItems()
    }

    @Test
    fun fetchWhenObserved() {
        repoViewModel.init()
        repoViewModel.news.observeForever(mock())
        verify(repository).fetchNewsItems()
    }

    @Test
    fun changeWhileObserved() {
        repoViewModel.news.observeForever(mock())

        repoViewModel.init()
        repoViewModel.refreshData()

        verify(repository).fetchNewsItems()
        verify(repository).fetchNewsItems(true)
    }

    @Test
    fun contributors() {
        val observer = mock<Observer<Resource<List<ArticlesItem>>>>()
        repoViewModel.news.observeForever(observer)
        verifyNoMoreInteractions(observer)
        verifyNoMoreInteractions(repository)
        repoViewModel.init()
        verify(repository).fetchNewsItems()
    }

    @Test
    fun retry() {
        repoViewModel.refreshData()
        verifyNoMoreInteractions(repository)
        repoViewModel.init()
        verifyNoMoreInteractions(repository)
        val observer = mock<Observer<Resource<List<ArticlesItem>>>>()
        repoViewModel.news.observeForever(observer)
        verify(repository).fetchNewsItems()
        reset(repository)
        repoViewModel.refreshData()
        verify(repository).fetchNewsItems(true)
    }
}