package com.ashok.bible.utils

import java.util.ArrayList
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.ashok.bible.data.remote.model.BaseModel


class MappedFireBaseData<T : BaseModel> {

    /*fun getFireBaseData(map: BaseFireBase<T>): ArrayList<T> {
        var t: T
        val data = ArrayList<T>()
        val map = map.data
        if (map != null) {
            for ((key, value) in map) {
                var sample: T = value
                sample.id = key
                data.add(sample)
            }
        }
        return data
    }*/

    fun getFireBaseMapData(map: Map<String, T>): ArrayList<T> {
        val data = ArrayList<T>()
        if (map != null) {
            for ((key, value) in map) {
                var sample: T = value
                sample.id = key
                data.add(sample)
            }
        }
        return data
    }

    fun getFireBaseMapDataInList(map: Map<String, List<T>>): ArrayList<T> {
        val data = ArrayList<T>()
        if (map != null) {
            for ((key, value) in map) {
                var list: List<T> = value
                for (obj in list){
                    obj.id = key
                    data.add(obj)
                }
            }
        }
        return data
    }

    fun getFireBaseMapInSideMapData(map: Map<String, Map<String, T>>): ArrayList<T> {
        val data = ArrayList<T>()
        if (map != null) {
            for ((key, value) in map) {
                for ((key, value) in value) {
                    var sample: T = value
                    sample.id = key
                    data.add(sample)
                }
            }
        }
        return data
    }
}