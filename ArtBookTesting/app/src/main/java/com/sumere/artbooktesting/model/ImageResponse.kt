package com.sumere.artbooktesting.model

data class ImageResponse (
    val hits: List<ImageResults>,
    val total: Int,
    val totalHits: Int
)