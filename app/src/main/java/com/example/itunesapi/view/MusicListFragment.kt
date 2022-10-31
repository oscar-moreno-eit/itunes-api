package com.example.itunesapi.view

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.itunesapi.MainActivity
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
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMusicListBinding.inflate(inflater,container,false )


        initViews()
        arguments?.getParcelable<MusicResponse>(DISPLAY_MUSIC)?.let {

            updateAdapter(it)


        }

        return  binding.root
    }


    private fun initViews() {
        mediaPlayer = MediaPlayer()
    }


    private lateinit var mediaPlayer: MediaPlayer
    private fun updateAdapter(dataSet: MusicResponse) {



        binding.rvMusicResult.adapter = MusicAdapter(parseListMusicInfo(dataSet)){
            // Trailing lambda
            // on below line we are setting audio stream
            // type as stream music on below line.
            // MediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC) -> Deprecated

            // on below line we are running a try and catch block for our media player.
            try {
                //Stop any  other music in queue
                mediaPlayer.reset()
                // on below line we are setting audio
                // source as audio url on below line.
                mediaPlayer.setDataSource(it.previewUrl)

                // on below line we are
                // preparing our media player.
                mediaPlayer.prepare()

                // on below line we are
                // starting our media player.
                mediaPlayer.start()

            } catch (e: Exception) {

                // on below line we are handling our exception.
                e.printStackTrace()
            }

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

}