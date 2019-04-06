package com.sevenpeakssoftware.thong.view.main.item

import androidx.databinding.ObservableField
import com.sevenpeakssoftware.thong.view.base.BaseViewModel

class ArticalCellViewModel : BaseViewModel {

    val bindTitle = ObservableField<String>()

    constructor()

    constructor(title: String) {
        bindTitle.set(title)
    }
}