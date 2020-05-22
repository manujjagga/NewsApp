
package com.example.news.main.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.news.database.LocalDatabase
import com.example.news.database.MainDao
import com.example.news.model.ArticlesItem
import com.example.news.remote.WebService
import com.example.news.util.ApiUtil.successCall
import com.example.news.util.InstantAppExecutors
import com.example.news.util.TestUtil
import com.example.news.util.mock
import com.example.news.util.remoteUtils.Resource
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyList
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import retrofit2.Response

@RunWith(JUnit4::class)
class MainRepositoryTest {
    private lateinit var repository: MainRepository
    private val dao = mock(MainDao::class.java)
    private val service = mock(WebService::class.java)
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        val db = mock(LocalDatabase::class.java)
        `when`(db.mainDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = MainRepository(dao,InstantAppExecutors(),service)
    }

    @Test
    fun loadArticlesFromNetwork() {
        val dbData = MutableLiveData<List<ArticlesItem>>()
        `when`(dao.fetchNewsItems()).thenReturn(dbData)

        val repo = TestUtil.createArticleResponse(5,"foo", "bar", "desc","source")
        val call = successCall(repo)
        `when`(service.fetchNewsItem()).thenReturn(call)

        val data = repository.fetchNewsItems()
        verify(dao).fetchNewsItems()
        verifyNoMoreInteractions(service)

        val observer = mock<Observer<Resource<List<ArticlesItem>>>>()
        data.observeForever(observer)
        verifyNoMoreInteractions(service)
        val updatedDbData = MutableLiveData<List<ArticlesItem>>()
        `when`(dao.fetchNewsItems()).thenReturn(updatedDbData)

        dbData.postValue(null)
        verify(service).fetchNewsItem()
        verify(dao).insertNewsItems(repo.articles)

        updatedDbData.postValue(repo.articles)
        verify(observer).onChanged(Resource.success(repo.articles))
    }
}