package com.example.itunesapi.model.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// term=rock
// &amp;media=ebooks
// &amp;entity=1
// &amp;limit=books

interface ItunesAPI {
    @GET(MusicNetwork.ENDPOINT)
    fun getMusic(
        @Query("term") musicTerm: String,
        @Query("amp;media") musicMedia: String = "music",
        @Query("amp;entity") musicEntity: Int = 50,
        @Query("amp;limit") musicLimit: String = "song"

    ): Call<MusicResponse>
}