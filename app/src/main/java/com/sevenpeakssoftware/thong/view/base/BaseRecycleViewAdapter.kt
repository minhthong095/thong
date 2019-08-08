package com.sevenpeakssoftware.thong.view.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.sevenpeakssoftware.thong.BR
import java.lang.ref.WeakReference

abstract class BaseRecycleViewAdapter<CB : ViewDataBinding, CVM : ViewModel>: RecyclerView.Adapter<RecycleViewCell> {

    private val mItemSource: ObservableArrayList<CVM>
    private lateinit var mBinding: CB

    constructor(source: ObservableArrayList<CVM>) {
        mItemSource = source
        mItemSource.addOnListChangedCallback(WeakOnListChangedCallback(mObservableListCallback))
    }

    abstract fun getLayoutId(viewType: Int): Int

    open fun getItemType(position: Int) = 0

    open fun getCell(binder: ViewDataBinding) = RecycleViewCell(binder)

    override fun getItemCount() = mItemSource.count()

    override fun getItemViewType(position: Int) = getItemType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewCell {

        mBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )

        return getCell(mBinding)
    }


    override fun onBindViewHolder(holder: RecycleViewCell, position: Int) {
        val cvm = mItemSource[position]

        holder.binding.setVariable(BR.itemViewModel, cvm)
        holder.binding.executePendingBindings()
    }

    private val mObservableListCallback = object : ObservableList.OnListChangedCallback<ObservableArrayList<CVM>>() {
        override fun onChanged(sender: ObservableArrayList<CVM>?) {
            notifyDataSetChanged()
        }

        override fun onItemRangeRemoved(sender: ObservableArrayList<CVM>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeRemoved(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            sender: ObservableArrayList<CVM>?,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeInserted(sender: ObservableArrayList<CVM>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeChanged(sender: ObservableArrayList<CVM>?, positionStart: Int, itemCount: Int) {
            notifyItemRangeChanged(positionStart, itemCount)
        }
    }
}

class RecycleViewCell(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root)

private class WeakOnListChangedCallback<T : ObservableArrayList<*>>(callback: ObservableList.OnListChangedCallback<T>) :
    ObservableList.OnListChangedCallback<T>() {

    private val weakCallback: WeakReference<ObservableList.OnListChangedCallback<T>> = WeakReference(callback)

    override fun onChanged(sender: T) {
        weakCallback.get()?.onChanged(sender)
    }

    override fun onItemRangeChanged(sender: T, positionStart: Int, itemCount: Int) {
        weakCallback.get()?.onItemRangeChanged(sender, positionStart, itemCount)
    }

    override fun onItemRangeInserted(sender: T, positionStart: Int, itemCount: Int) {
        weakCallback.get()?.onItemRangeInserted(sender, positionStart, itemCount)
    }

    override fun onItemRangeMoved(sender: T, fromPosition: Int, toPosition: Int, itemCount: Int) {
        weakCallback.get()?.onItemRangeMoved(sender, fromPosition, toPosition, itemCount)
    }

    override fun onItemRangeRemoved(sender: T, positionStart: Int, itemCount: Int) {
        weakCallback.get()?.onItemRangeRemoved(sender, positionStart, itemCount)
    }
}