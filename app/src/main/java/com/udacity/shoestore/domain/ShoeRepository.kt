package com.udacity.shoestore.domain

import androidx.lifecycle.LiveData
import com.udacity.shoestore.domain.models.Shoe

interface ShoeRepository {

    fun getShoeList(): LiveData<List<Shoe>>

    fun createNewShoe(shoe: Shoe)
}