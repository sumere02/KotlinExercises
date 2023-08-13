package com.sumere.retrofitkotlin.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    //Kotlin Feature do not need Serialized Name
    //data identifier already serializes in kotlin
    //@SerializedName("currency")
    val currency: String,
    //@SerializedName("price")
    val price: String) {
}