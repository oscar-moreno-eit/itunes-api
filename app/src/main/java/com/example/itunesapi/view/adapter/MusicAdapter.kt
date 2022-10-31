package com.example.itunesapi.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesapi.databinding.MusicItemLayoutBinding
import com.example.itunesapi.model.remote.MusicInfo
import com.squareup.picasso3.Picasso

class MusicAdapter(private val dataset: List<MusicInfo>, private val openDetails: (MusicInfo) -> Unit) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    class MusicViewHolder(private val binding: MusicItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(currentElement: MusicInfo,openDetails: (MusicInfo) -> Unit){
            binding.root.setOnClickListener {
                openDetails( currentElement)
            }
            binding.tvMusicTitle.text = currentElement.trackName
            binding.tvMusicAuthor.text = currentElement.artistName
            binding.tvMusicPrice.text = currentElement.trackPrice.toString()
            binding.tvMusicCurrency.text = currentElement.currency
            binding.vPreview.contentDescription = currentElement.previewUrl
            //Replacing 100x100bb.jpg by 250x250bb.jpg gives you a better image resolution
            Picasso.Builder(binding.tvMusicAuthor.context).build().load(currentElement.artworkUrl100.replace("100x100bb.jpg","250x250bb.jpg")).into(binding.ivMusicCover)

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