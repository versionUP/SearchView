package com.example.prashanth.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prashanth.models.PorcupineItem
import com.example.prashanth.models.PorcupineResponse
import com.example.prashanth.models.ServiceResult
import com.example.prashanth.repository.PorcupineRepository
import kotlinx.coroutines.launch

internal class MainFragmentViewModel(
    private val porcupineRepository: PorcupineRepository
) : ViewModel() {

    private val _dialogError = MutableLiveData<String>()
    val dialogError: LiveData<String> = _dialogError

    private val _isShowSpinner = MutableLiveData(false)
    val isShowSpinner: LiveData<Boolean> = _isShowSpinner

    private val _porcupineData = MutableLiveData<List<PorcupineItem>>()
    val porcupineData: LiveData<List<PorcupineItem>> = _porcupineData

    private var response: PorcupineResponse? = null

    var recentSearches: MutableList<String> = mutableListOf()

    init {
        viewModelScope.launch {
            _isShowSpinner.value = true
            when (val res = porcupineRepository.getPorcupineData()) {
                is ServiceResult.Success -> {
                    response = res.data
                    _isShowSpinner.value = false
                    _porcupineData.value = res.data.items?.filterNotNull()
                }
                is ServiceResult.Error -> {
                    _dialogError.value = res.exception.string
                    _isShowSpinner.value = false
                }
            }

        }
    }

    fun handleUserInput(toString: String) {
        response?.let { res ->
            val list = res.items?.filter { it?.title?.contains(toString) == true }?.filterNotNull()
            _porcupineData.value = list ?: res.items?.filterNotNull()
            recentSearches.apply {
                if (size >= MAX_SIZE) {
                    removeAt(size - 1)
                } else {
                    add(toString)
                }
            }
        }
    }


    companion object {
        const val MAX_SIZE = 5
    }

}
