package com.example.prashanth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prashanth.models.PorcupineItem
import com.google.gson.Gson

class DetailFragmentViewModel (porcupineString:String?): ViewModel() {


    private val _porcupine = MutableLiveData<PorcupineItem>()
    val porcupine:LiveData<PorcupineItem> = _porcupine

    init {
        _porcupine.value = Gson().fromJson(porcupineString,PorcupineItem::class.java)
    }

}
