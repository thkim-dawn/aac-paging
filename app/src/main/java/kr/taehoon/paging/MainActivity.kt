package kr.taehoon.paging

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kr.taehoon.paging.base.BaseActivity
import kr.taehoon.paging.databinding.ActivityMainBinding
import kr.taehoon.paging.fragment.DetailFragment
import kr.taehoon.paging.fragment.MainFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private val searchViewModel: SearchViewModel by viewModel()

    override fun initAfterBinding() {
        searchViewModel.moveBookLiveData.observe(this, Observer { book ->
            replaceFragment(DetailFragment.newInstance(book), true)
        })

        replaceFragment(MainFragment.newInstance(), false)
    }

    private fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        // 생명주기를 고려하여, replace 대신 add 사용 (replace : remove->add)
        transaction.add(R.id.frame_main, fragment)
        if (isAddToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}