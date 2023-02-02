package com.udacity.shoestore.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.data.ShoeRepositoryImpl
import com.udacity.shoestore.domain.ShoeRepository
import com.udacity.shoestore.domain.models.Shoe

class ShoeStoreViewModel: ViewModel() {

    private val shoeRepository: ShoeRepository by lazy {
        ShoeRepositoryImpl
    }

    private var _shoeList = shoeRepository.getShoeList()
    val shoeList: LiveData<List<Shoe>> = _shoeList

    private var _shoeDetail = MutableLiveData<Shoe?>()
    val shoeDetail: LiveData<Shoe?> = _shoeDetail

    fun createNewShoe(shoe: Shoe) {
        shoeRepository.createNewShoe(shoe)
    }

    fun loadShoeDetail(shoe: Shoe) {
        _shoeDetail.value = shoe
    }

    override fun onCleared() {
        super.onCleared()
        _shoeDetail.value = null
    }

    fun clearData() {
        _shoeDetail.value = null
    }
}