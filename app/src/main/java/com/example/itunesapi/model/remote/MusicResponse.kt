package com.example.itunesapi.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicResponse(
    val resultCount: Long,
    val results: List<MusicInfo>,
): Parcelable

@Parcelize
data class MusicInfo(
    val trackName: String,
    val artistName: String,
    val currency: String,
    val previewUrl: String,
    val artworkUrl60: String,
    val trackPrice: Double
): Parcelable