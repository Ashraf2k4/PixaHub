package com.progress.photos.pixahub.apipack

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)