package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable

interface IDatabaseHelper {
    fun getAllArtical() : Observable<List<Artical>>
}