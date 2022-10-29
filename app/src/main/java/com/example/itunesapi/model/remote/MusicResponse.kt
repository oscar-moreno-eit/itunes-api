package com.example.itunesapi.model.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicResponse(
    val resultCount: Long = 0,
    val results: List<MusicInfo> = emptyList()
): Parcelable

@Parcelize
data class MusicInfo(
    val trackName: String = "",
    val artistName: String = "",
    val currency: String = "",
    val previewUrl: String = "",
    val artworkUrl100: String = "",
    val trackPrice: Double = 0.0
): Parcelable