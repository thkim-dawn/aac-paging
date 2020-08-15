package kr.taehoon.paging.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import kr.taehoon.paging.BR

abstract class BaseFragment<T: ViewDataBinding> : Fragment(){
    lateinit var viewDataBinding: T
    val compositeDisposable: CompositeDisposable = CompositeDisposable()
    abstract val layoutResId : Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId , container, false)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        viewDataBinding.setVariable(BR.fragment,this)
        initOnCreatedView()
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initOnViewCreated()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    abstract fun initOnCreatedView()
    abstract fun initOnViewCreated()
}