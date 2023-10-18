package com.example.contacts.utils

import kotlin.random.Random

object EntityID {
    fun generateID() : Int{
        return Random.nextInt(20, 101) // Genera un número entre 20 (inclusive) y 101 (exclusivo)
    }
}