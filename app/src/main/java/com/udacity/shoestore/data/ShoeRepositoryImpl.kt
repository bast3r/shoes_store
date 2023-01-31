package com.udacity.shoestore.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.shoestore.domain.ShoeRepository
import com.udacity.shoestore.domain.models.Shoe

object ShoeRepositoryImpl: ShoeRepository {
    private val database: LiveData<List<Shoe>> = MutableLiveData(ArrayList())

    override fun getShoeList(): LiveData<List<Shoe>> {
        return database
    }

    override fun createNewShoe(shoe: Shoe) {
        (database.value as? ArrayList)?.add(shoe)
    }
}