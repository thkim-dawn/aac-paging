package kr.taehoon.paging.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kr.taehoon.paging.BR

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(){
    private lateinit var viewDataBinding: T
    abstract val layoutResourceId: Int
    abstract fun initAfterBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        viewDataBinding.setVariable(BR.activity, this)
        viewDataBinding.lifecycleOwner = this

        initAfterBinding()
    }

}