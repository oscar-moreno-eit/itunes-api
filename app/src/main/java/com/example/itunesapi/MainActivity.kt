package com.example.itunesapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.itunesapi.model.remote.MusicNetwork
import com.example.itunesapi.model.remote.MusicResponse
import com.example.itunesapi.view.Communicator
import com.example.itunesapi.view.MusicListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var btnNavBar: BottomNavigationView
    private lateinit var str: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        this.loadMusic(getString(R.string.tab_rock))

    }

    private fun initViews() {


        btnNavBar = findViewById(R.id.bnv_navigator)
        str = findViewById(R.id.swipeContainer)

        btnNavBar.setOnItemSelectedListener {
            this.loadMusic(it.titleCondensed.toString().lowercase())
            true
        }

        str.setOnRefreshListener {
            when(btnNavBar.selectedItemId){
                R.id.it_rock -> this.loadMusic(getString(R.string.tab_rock))
                R.id.it_classic -> this.loadMusic(getString(R.string.tab_classick))
                R.id.it_pop -> this.loadMusic(getString(R.string.tab_pop))
            }

            str.isRefreshing = false
        }

    }


    override fun loadMusic(term: String) {
        MusicNetwork.itunesApi.getMusic(term).enqueue(
            object : Callback<MusicResponse> {

                override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        createDisplayFragment(body)
                    }
                }

                override fun onFailure(call: Call<MusicResponse>, t: Throwable) {
                    Log.v("retrofit", "call failed")
                }


            })//execute - synchronous : enqueue - Asynchronous
    }

    private fun createDisplayFragment(body: MusicResponse?) {
        body?.let {
            Log.d(TAG, "createDisplayFragment: $body it: $it")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_music_list,MusicListFragment.newInstance(it))
                .commit()

        }
    }

}