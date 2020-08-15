package kr.taehoon.paging.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import kr.taehoon.paging.base.BaseRecyclerAdapter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object BindingAdapterUtil {

    @JvmStatic
    @BindingAdapter("visible")
    fun visible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(Target.SIZE_ORIGINAL)
            .centerCrop()
            .into(imageView)
    }


    @JvmStatic
    @BindingAdapter("price")
    fun priceMarking(textView: TextView, price: Int) {
        val formatter: NumberFormat = DecimalFormat("#,###")
        textView.text = "${formatter.format(price)}원"
    }

    @JvmStatic
    @BindingAdapter(value = ["originPrice", "salePrice"], requireAll = true)
    fun saleRate(textView: TextView, originPrice: Int, salePrice: Int) {
//        val rate = (1 - (salePrice.toFloat() / originPrice.toFloat())) * 100
        val rate = (originPrice - salePrice).toFloat() * 100 / originPrice.toFloat()
        textView.text = "(-${rate.toInt()}%)"
    }

    @JvmStatic
    @BindingAdapter("setItems")
    fun setItems(recyclerView: RecyclerView, items: MutableLiveData<ArrayList<*>>?) {
        recyclerView.adapter?.let {
            val baseRecyclerAdapter = it as BaseRecyclerAdapter<*>

            items
                ?.value?.let {
                    baseRecyclerAdapter.addItems(it)
                }
        }
    }

    @JvmStatic
    @BindingAdapter("setItems")
    fun setItems(recyclerView: RecyclerView, items: List<*>?) {

        recyclerView.adapter?.let {
            val baseRecyclerAdapter = it as BaseRecyclerAdapter<*>

            items?.let {
                baseRecyclerAdapter.addItems(items as ArrayList<*>)
            }

        }
    }

    @JvmStatic
    @BindingAdapter(value = ["date", "dateFormat"], requireAll = true)
    fun convertDate(textView: TextView, date: Date?, dateFormat: String) {
        if (date == null) {
            textView.visibility = View.GONE
            return
        }
        date.let {
            val sdf = SimpleDateFormat(dateFormat)
            textView.text = sdf.format(it)
        }
    }

}