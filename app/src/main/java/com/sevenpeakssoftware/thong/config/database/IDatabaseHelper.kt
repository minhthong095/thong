package com.sevenpeakssoftware.thong.config.database

import com.sevenpeakssoftware.thong.config.model.Artical
import io.reactivex.Observable

interface IDatabaseHelper {
    fun getAllArtical() : Observable<List<Artical>>
    fun insertAllArtical(articals: List<Artical>): Observable<Boolean>
    fun insertArtical(artical:Artical): Observable<Boolean>
}