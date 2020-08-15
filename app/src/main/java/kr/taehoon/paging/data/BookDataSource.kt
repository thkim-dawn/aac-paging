package kr.taehoon.paging.data

import androidx.paging.PageKeyedDataSource
import kr.taehoon.paging.SearchViewModel

class BookDataSource(private val searchViewModel: SearchViewModel) : PageKeyedDataSource<Int, Book>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Book>
    ) {
        var prePage: Int? = null
        if (searchViewModel.searchTextLiveData.value.isNullOrEmpty()) {
            return
        }
        if (searchViewModel.lastPage != 1)
            prePage = searchViewModel.lastPage - 1

        val loadInitial = searchViewModel.getSearchBook(
            searchViewModel.searchTextLiveData.value!!,
            searchViewModel.getSearchPage()
        ).subscribe({
                var nextPage: Int? = null

                if (!searchViewModel.isEndPage)
                    nextPage = searchViewModel.lastPage + 1

                callback.onResult(it, prePage, nextPage)
            }, {

            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        val loadAfter = searchViewModel.getSearchBook(
            searchViewModel.searchTextLiveData.value!!,
            params.key
        )
            .subscribe({
                if (searchViewModel.lastPage == 1)
                    callback.onResult(it, null)
                else
                    callback.onResult(it, params.key +1)
            }, {

            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Book>) {
        val loadBefore = searchViewModel.getSearchBook(
            searchViewModel.searchTextLiveData.value!!,
            params.key
        ).subscribe({
                if (searchViewModel.lastPage != 1) {
                    callback.onResult(it, params.key -1)
                } else
                    callback.onResult(it, null)
            }, {

            })
    }
}