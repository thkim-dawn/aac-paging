package kr.taehoon.paging.fragment

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.rxkotlin.addTo
import kr.taehoon.paging.BookPageRecyclerAdapter
import kr.taehoon.paging.R
import kr.taehoon.paging.SearchViewModel
import kr.taehoon.paging.base.BaseFragment
import kr.taehoon.paging.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val layoutResId: Int
        get() = R.layout.fragment_main
    private lateinit var bookPageRecyclerAdapter : BookPageRecyclerAdapter
    private val searchViewModel: SearchViewModel by sharedViewModel()

    private lateinit var recyclerBookList: RecyclerView

    override fun onStart() {
        super.onStart()
        searchViewModel.builder.buildObservable()
            .subscribe {
                bookPageRecyclerAdapter.submitList(it)
            }.addTo(compositeDisposable)
    }

    override fun initOnCreatedView() {
        viewDataBinding.searchViewModel = searchViewModel
    }

    override fun initOnViewCreated() {
        viewDataBinding.etSearch.textChangeEvents().subscribe {
            viewDataBinding.btnSearch.isEnabled = it.text.isNotEmpty()
        }.addTo(compositeDisposable)

        bookPageRecyclerAdapter = BookPageRecyclerAdapter(requireContext())
        viewDataBinding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    onClickSearchBtn()
                    true
                }
                else -> false
            }
        }
        recyclerBookList = viewDataBinding.recyclerBookList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = bookPageRecyclerAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    fun onClickSearchBtn() {
        (recyclerBookList.adapter as BookPageRecyclerAdapter).currentList?.dataSource?.invalidate()
    }


    companion object {
        fun newInstance(): MainFragment {
            val bundle = Bundle()
            return MainFragment().apply { arguments = bundle }
        }
    }
}