package com.sevenpeakssoftware.thong.view.main.item

import androidx.databinding.ObservableField
import com.sevenpeakssoftware.thong.view.base.BaseViewModel

class ArticalCellViewModel : BaseViewModel {

    val bindTitle = ObservableField<String>()
    val bindImage = ObservableField<String>()

    constructor()

    constructor(title: String, image: String) {
        bindTitle.set(title)
        bindImage.set(image)
    }
}