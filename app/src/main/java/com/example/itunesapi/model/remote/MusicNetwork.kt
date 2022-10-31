package com.example.itunesapi.model.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * In kotlin this is a Singleton
 * An object that exist in memory
 * It doesn't need an instance/constructor
 */
object MusicNetwork {
    /* Retrofit
    *  1.- Create an API Interface
    *  2.- Define the HTTP Verbs in that reference
    *  3.- Define Base URL, Endpoints
    *  4.- Create the Retrofit Object
    *  5.- Create the API Interface from the Retrofit Object
    * */
    const val BASE_URL = "https://itunes.apple.com/"
    const val ENDPOINT = "search"

    val itunesApi: ItunesAPI by lazy{ // Create a temporary class
        initRetrofit().create(ItunesAPI::class.java)
    }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}