package com.example.itunesapi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesapi.databinding.MusicItemLayoutBinding
import com.example.itunesapi.model.remote.MusicInfo
import com.squareup.picasso3.Picasso

// TODO: dataset needs to do mutable
class MusicAdapter(private val dataset: List<MusicInfo>, private val openDetails: (MusicInfo) -> Unit) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(private val binding: MusicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(currentElement: MusicInfo,openDetails: (MusicInfo) -> Unit){
            binding.root.setOnClickListener {
                openDetails( currentElement)
            }
            binding.tvMusicTitle.text = currentElement.trackName
            binding.tvMusicAuthor.text = currentElement.artistName
            Picasso.Builder(binding.tvMusicAuthor.context).build().load(currentElement.artworkUrl60).into(binding.ivMusicCover)
            // get Picasso Dependency, do the Picasso.Builder().load().into(binding.ivCoverBook)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(MusicItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(dataset[position],openDetails)
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }
}