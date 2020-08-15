package kr.taehoon.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kr.taehoon.paging.data.Book
import kr.taehoon.paging.data.BookDataSource
import kr.taehoon.paging.util.ApiService
import kr.taehoon.paging.util.default
import kr.taehoon.paging.util.parsingData
import org.koin.core.KoinComponent
import org.koin.core.inject


class SearchViewModel : ViewModel(), KoinComponent {
    private val TAG = "SearchViewModel"

    private val compositeDisposable = CompositeDisposable()
    private val apiService: ApiService by inject()

    private var isLoading = false
    private var lastQuery = ""

    //   LiveData
    val searchTextLiveData = MutableLiveData<String>().default("")
    val bookListLiveData = MutableLiveData<ArrayList<Book>>()
    val moveBookLiveData = MutableLiveData<Book>()

    var lastPage: Int = 1
    var isEndPage: Boolean = false

    private val config = PagedList.Config.Builder()
        .setInitialLoadSizeHint(20)
        .setPageSize(50)
        .setPrefetchDistance(5)
        .setEnablePlaceholders(true)
        .build()

    val builder = RxPagedListBuilder<Int, Book>(object : DataSource.Factory<Int, Book>() {
        override fun create(): DataSource<Int, Book> {
            return BookDataSource(this@SearchViewModel)
        }
    }, config)

    fun getSearchBook(query: String, page: Int): Single<ArrayList<Book>> {

        isLoading = true
        return apiService.getSearchBook(query, page)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                lastQuery = query
                val books: ArrayList<Book> = it.parsingData("documents")
                bookListLiveData.value = books
                lastPage = page
                it.asJsonObject.getAsJsonObject("meta").let { metaData ->
                    if (metaData.has("is_end")) {
                        isEndPage = metaData.get("is_end").asBoolean
                    }
                }
                return@flatMap Single.just(books)
            }
    }

    fun getSearchPage(): Int {
        if (lastQuery != searchTextLiveData.value)
            lastPage = 1
        return lastPage
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}