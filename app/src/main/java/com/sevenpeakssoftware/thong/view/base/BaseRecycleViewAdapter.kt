package com.sevenpeakssoftware.thong.view.base

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sevenpeakssoftware.thong.BR
import java.lang.ref.WeakReference


abstract class BaseRecycleViewAdapter<CB : ViewDataBinding, CVM : ViewModel> :
    RecyclerView.Adapter<RecycleViewCell<CVM>>() {

    val itemSource: ObservableArrayList<CVM> = ObservableArrayList()

    private lateinit var mContext: Context

    private lateinit var mBinding: CB

    private var mHeightCell = 0

    init {
        itemSource.addOnListChangedCallback(ListChangedCallBack<CB, CVM>(this as BaseRecycleViewAdapter<ViewDataBinding, ViewModel>))
    }

    private fun _calHeightCell(): Int {
        val windowManager = getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        return (getRatioHeight() * size.y).toInt()
    }

    fun getContext() = mContext

    fun getBinding() = mBinding

    abstract fun getLayoutId(viewType: Int): Int

    open fun getItemType(position: Int) = 0

    open fun getRatioHeight(): Float = 1f

    open fun getCell(binder: ViewDataBinding) = RecycleViewCell<CVM>(binder)

    override fun getItemCount() = itemSource.count()

    override fun getItemViewType(position: Int) = getItemType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewCell<CVM> {

        mContext = parent.context

        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )

        mBinding.apply { root.layoutParams.height = _calHeightCell() }

        return getCell(mBinding)
    }

    override fun onBindViewHolder(holder: RecycleViewCell<CVM>, position: Int) {
        val cvm = itemSource[position]

        holder.binding.setVariable(BR.itemViewModel, cvm)
        holder.binding.executePendingBindings()
    }

    class ListChangedCallBack<CB, CVM>(adapter: BaseRecycleViewAdapter<ViewDataBinding, ViewModel>) :
        ObservableList.OnListChangedCallback<ObservableList<CVM>>() {

        private val adapterReference = WeakReference(adapter)

        override fun onChanged(sender: ObservableList<CVM>?) {
            adapterReference.get()?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(sender: ObservableList<CVM>?, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(sender: ObservableList<CVM>?, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            sender: ObservableList<CVM>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            adapterReference.get()?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(sender: ObservableList<CVM>?, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeRemoved(positionStart, itemCount)
        }

    }
}

class RecycleViewCell<CVM>(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root)

//abstract class BaseArrayAdapter: ArrayAdapter<ViewModel>(context: Context, resource: Int) {
//
//}