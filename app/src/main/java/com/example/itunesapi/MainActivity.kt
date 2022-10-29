package com.example.itunesapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.itunesapi.databinding.ActivityMainBinding
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


    /*
    private val movieHandler =
        object: Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                when(msg.what){
                    1-> {
                        val listOfSongs = msg.obj as List<MusicInfo>
                        Log.d(TAG, "handleMessage: $listOfSongs")
                        rvMusicList.adapter = MusicAdapter(listOfSongs){
                            return@MusicAdapter
                        }

                    }
                    else -> {
                        msg.data?.getString("KEY")?.let {
                            Toast.makeText(this@MainActivity,  it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            }
        }*/


    private lateinit var btnNavBar: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNavBar = findViewById(R.id.bnv_navigator)
        btnNavBar.setOnItemSelectedListener {
            this.doSearch("rock")
            true
        }
        /*initViews()*/

        //doSearch("rock")

    }
/*
    private fun initViews() {

        rvMusicList = findViewById<RecyclerView?>(R.id.fragment_music_list).findViewById(R.id.rv_music_result)
        //rvMovieList.adapter = MovieAdapter(getMovieList())

        rvMusicList.layoutManager = createLayoutManager()


    }

    private fun createLayoutManager(): RecyclerView.LayoutManager {
        val linearLayoutManager =  LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val gridLayoutManager = GridLayoutManager(this,3, GridLayoutManager.HORIZONTAL,true)
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL)
        return linearLayoutManager
    }

   private fun getMusicList() {
//        val network = MusicNetwork

        Thread{
            Log.d(TAG, "getMusicList: ${Thread.currentThread().name}")

            var data = MutableLiveData<List<MusicInfo>>()
            val message = Message()
            message.what = 1
            message.obj = MusicNetwork.itunesApi.getMusic("rock").enqueue(object :
                Callback<MusicResponse> {
                override fun onFailure(call: Call<MusicResponse>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                }

                override fun onResponse(call: Call<MusicResponse>?, response: Response<MusicResponse>?) {
                    data.value = response!!.body()!!.results
                }

            })
            movieHandler.sendMessage(message)
        }.start()
    }*/

    override fun doSearch(term: String) {
        MusicNetwork.itunesApi.getMusic(term).enqueue(
            object : Callback<MusicResponse> {

                override fun onResponse(call: Call<MusicResponse>, response: Response<MusicResponse>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        createDisplayFragment(body)
                    }
                }

                override fun onFailure(call: Call<MusicResponse>?, t: Throwable?) {
                Log.v("retrofit", "call failed")
            }



        })//execute - syncronous : enqueue - Asyncronous
    }

    override fun playPreview() {
        TODO("Not yet implemented")
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