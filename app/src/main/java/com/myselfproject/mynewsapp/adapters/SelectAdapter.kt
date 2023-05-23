package com.myselfproject.mynewsapp.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.collection.arraySetOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myselfproject.mynewsapp.clicklistener.OnDataArticleClicker
import com.myselfproject.mynewsapp.databinding.DataBaseItemBinding
import com.myselfproject.mynewsapp.models.DataArticle
import java.text.SimpleDateFormat
import java.util.*


class SelectAdapter(
    private val onDataArticleClicker: OnDataArticleClicker
) : RecyclerView.Adapter<SelectAdapter.SelectNewsHolder>() {

    private val allDataArticle = arraySetOf<DataArticle>()

    inner class SelectNewsHolder(val selectBinding: DataBaseItemBinding) :
        RecyclerView.ViewHolder(selectBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectNewsHolder {
        val binding =
            DataBaseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectNewsHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SelectNewsHolder, position: Int) {
        val item: DataArticle = allDataArticle.elementAt(position)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd / MM / yyyy", Locale.ENGLISH)

        Glide.with(holder.selectBinding.imageView)
            .load(item.urlToImage)
            .into(holder.selectBinding.imageView)

        holder.selectBinding.textTitle.text = item.title
        holder.selectBinding.textSource.text = item.source
        holder.selectBinding.publishedAt.text = item.publishedAt?.let { s ->
            simpleDateFormat.parse(s)?.let { formatter.format(it) }
        }
        holder.selectBinding.cardNews.setOnClickListener {
            onDataArticleClicker.onArticleClicker(item)
        }
        holder.selectBinding.buttonToDelete.setOnClickListener {
            onDataArticleClicker.deleteButtonClick(item)
        }
        holder.selectBinding.buttonToShare.setOnClickListener {
            onDataArticleClicker.shareButtonClick(item)
        }
    }

    fun updateList(newList: List<DataArticle>) {

        allDataArticle.clear()

        allDataArticle.addAll(newList)

        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return allDataArticle.size
    }
}

