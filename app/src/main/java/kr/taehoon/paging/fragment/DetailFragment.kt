package kr.taehoon.paging.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import kr.taehoon.paging.MainActivity
import kr.taehoon.paging.R
import kr.taehoon.paging.base.BaseFragment
import kr.taehoon.paging.data.Book
import kr.taehoon.paging.databinding.FragmentDetailBinding

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override val layoutResId: Int
        get() = R.layout.fragment_detail
    var book: Book? = null
    override fun initOnCreatedView() {

    }

    override fun initOnViewCreated() {
        book = arguments?.getParcelable<Book>(PUT_BOOK)?.also {
            viewDataBinding.book = it
        }
    }

    fun onClickViewWeb() {
        if (book == null) {
            Toast.makeText(requireContext(),"실행할 수 없습니다.",Toast.LENGTH_SHORT).show()
        }else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book!!.url))
            startActivity(intent)
        }
    }

    fun onBackPressed(){
        (requireActivity() as? MainActivity)?.onBackPressed()
    }
    companion object {
        const val PUT_BOOK = "putBook"
        fun newInstance(book: Book): DetailFragment {
            val bundle = Bundle().apply {
                putParcelable(PUT_BOOK, book)
            }
            return DetailFragment().apply { arguments = bundle }
        }
    }
}