package com.example.itunesapi.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesapi.MainActivity
import com.example.itunesapi.databinding.ActivityMainBinding
import com.example.itunesapi.databinding.FragmentMusicListBinding
import com.example.itunesapi.model.remote.MusicInfo
import com.example.itunesapi.model.remote.MusicResponse
import com.example.itunesapi.view.adapter.MusicAdapter

private const val TAG = "MusicListFragment"
class MusicListFragment : Fragment() {

    companion object{ //Factory design pattern - a single method
        const val DISPLAY_MUSIC = "DISPLAY_MUSIC"
        fun newInstance(bookResponse: MusicResponse): MusicListFragment{
            val fragment = MusicListFragment()
            val bundle = Bundle()
            bundle.putParcelable(DISPLAY_MUSIC, bookResponse)
            fragment.arguments = bundle
            return  fragment
        }
    }


    private lateinit var bridge: Communicator

    /**
     * context here is the host activity, in this case: MainActivity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        when(context){
            is MainActivity -> {
                bridge = context

            }
            else -> IllegalAccessException("Incorrect  Host Activity!")
        }
    }

    private lateinit var binding: FragmentMusicListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_music_list, container, false)
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMusicListBinding.inflate(inflater,container,false )


        initViews()
        arguments?.getParcelable<MusicResponse>(DISPLAY_MUSIC)?.let {

            updateAdapter(it)
        }

        return  binding.root
    }

    private fun updateAdapter(dataSet: MusicResponse) {

        binding.rvMusicResult.adapter = MusicAdapter(parseListMusicInfo(dataSet)){
            // Trailing lambda
            // Toast.makeText(context,"$it",Toast.LENGTH_SHORT).show()

        }
    }

    private fun parseListMusicInfo(dataSet: MusicResponse): List<MusicInfo> {
        return dataSet.results.map { songItem ->
            Log.d(TAG, "parseListMusicInfo: $songItem")
            MusicInfo(
                songItem.trackName
                ,songItem.artistName
                ,songItem.currency
                ,songItem.previewUrl
                ,songItem.artworkUrl100
                ,songItem.trackPrice
            )
        }
    }

    private fun initViews() {


        //bridge.doSearch( "rock" )


        binding.rvMusicResult.setOnClickListener {

            //todo Play preview
            /*
            if (binding.sbMaxResults.progress == 0) {binding.sbMaxResults.progress = 10}

            bridge.doSearch(binding.tilBookSearch.editText?.text.toString()
                ,binding.spFilter.selectedItem.toString()
                ,binding.spBookType.selectedItem.toString()
                ,binding.sbMaxResults.progress)*/
        }

    }



}