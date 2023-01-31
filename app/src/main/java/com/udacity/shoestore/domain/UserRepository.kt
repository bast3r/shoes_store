package com.udacity.shoestore.domain

import com.udacity.shoestore.domain.models.User

interface UserRepository {

    fun signUp(user: User): Boolean

    fun signIn(user: User): Boolean

    fun logOut()
}