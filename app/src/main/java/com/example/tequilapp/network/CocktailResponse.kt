package com.example.tequilapp.network

data class CocktailResponse(
    val ingredients: List<Ingredient>
)

data class Ingredient(
    val strDescription: String
)