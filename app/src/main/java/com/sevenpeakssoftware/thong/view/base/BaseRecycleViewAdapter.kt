package com.sevenpeakssoftware.thong.view.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sevenpeakssoftware.thong.BR
import java.lang.ref.WeakReference
import android.content.Context.LAYOUT_INFLATER_SERVICE
import androidx.core.content.ContextCompat.getSystemService



abstract class BaseRecycleViewAdapter<CVM> : RecyclerView.Adapter<RecycleViewCell<CVM>>() {

    val itemSource: ObservableArrayList<CVM> = ObservableArrayList()

    init {
        itemSource.addOnListChangedCallback(ListChangedCallBack(this))
    }

    abstract fun getLayoutId(viewType: Int): Int

    open fun getItemType(position: Int) = 0

    open fun getCell(binder: ViewDataBinding) = RecycleViewCell<CVM>(binder)

    override fun getItemCount() = itemSource.count()

    override fun getItemViewType(position: Int) = getItemType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewCell<CVM> {

        val binder = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )

        return getCell(binder)
    }

    override fun onBindViewHolder(holder: RecycleViewCell<CVM>, position: Int) {
        val cvm = itemSource[position]
        holder.binder.setVariable(BR.itemViewModel, cvm)
        holder.binder.executePendingBindings()
    }

    class ListChangedCallBack<T>(adapter: BaseRecycleViewAdapter<T>): ObservableList.OnListChangedCallback<ObservableList<T>>() {

        private val adapterReference = WeakReference(adapter)

        override fun onChanged(sender: ObservableList<T>) {
            adapterReference.get()?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
            adapterReference.get()?.notifyItemRangeRemoved(positionStart, itemCount)
        }

    }
}

class RecycleViewCell<CVM>(val binder: ViewDataBinding):
    RecyclerView.ViewHolder(binder.root)