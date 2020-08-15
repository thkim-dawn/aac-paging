package kr.taehoon.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import kr.taehoon.paging.base.BaseViewHolder
import kr.taehoon.paging.data.Book
import kr.taehoon.paging.databinding.ItemBookBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookPageRecyclerAdapter(context: Context) :
    PagedListAdapter<Book, BookPageRecyclerAdapter.BookViewHolder>(DIFF_CALLBACK) {

    val searchViewModel :SearchViewModel by (context as ViewModelStoreOwner).viewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {

        return LayoutInflater.from(parent.context).let {
            DataBindingUtil.inflate<ItemBookBinding>(it, R.layout.item_book, parent, false)
        }.run {
            lifecycleOwner = (parent.context as? LifecycleOwner)
            BookViewHolder(this)
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    inner class BookViewHolder(override val binding: ItemBookBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(itemData: Any?, position: Int) {
            (itemData as? Book)?.let {
                binding.book = it
                binding.executePendingBindings()

            }
            binding.root.setOnClickListener {
                searchViewModel.moveBookLiveData.value = itemData as? Book
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }
}