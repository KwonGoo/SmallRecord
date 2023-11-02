package com.example.smallrecord.entity

data class FoodEntity(
    val no: Int,
    val sampleId: String?,
    val productName: String,
    val maunfacturer: String,
    val Carbohydarte_g: Int,
    val protein_g: Int,
    val fat_g: Int,
    val sodium_mg: Int,
    val Cholesterol_mg: Int,
    val total_sugars_g: Int
)

