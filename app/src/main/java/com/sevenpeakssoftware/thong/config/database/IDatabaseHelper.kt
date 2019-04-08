package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface IDatabaseHelper {
    fun getAllArtical() : Observable<List<Artical>>
    fun insertArtical(artical:Artical)
    fun deleteAllArtical()

}