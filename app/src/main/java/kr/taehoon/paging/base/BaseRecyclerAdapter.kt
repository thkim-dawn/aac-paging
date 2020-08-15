package kr.taehoon.paging.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

//ViewType이 오직 하나인 RecyclerView를 위한 Base Adapter
abstract class BaseRecyclerAdapter<VD : ViewDataBinding> :  RecyclerView.Adapter<BaseViewHolder>() {
    abstract val layoutResourceId: Int
    abstract val context: Context
    abstract var mData: ArrayList<*>

    abstract fun onCreateCoreViewHolder(binding: VD, viewType: Int): BaseViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return LayoutInflater.from(parent.context).let {
            DataBindingUtil.inflate<VD>(it, layoutResourceId, parent, false)
        }.run {
            lifecycleOwner = (parent.context as? LifecycleOwner)
            onCreateCoreViewHolder(this, viewType)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(mData[position], position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun addItems(items: ArrayList<*>) {
        mData = items
        notifyDataSetChanged()
    }
}
abstract class BaseViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    abstract val binding: ViewDataBinding
    abstract fun bind(itemData: Any?, position: Int)
}