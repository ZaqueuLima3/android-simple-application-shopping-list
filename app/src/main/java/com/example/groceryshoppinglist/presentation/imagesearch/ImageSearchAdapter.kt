package com.example.groceryshoppinglist.presentation.imagesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.groceryshoppinglist.databinding.ItemImageBinding
import com.example.groceryshoppinglist.domain.model.Image
import javax.inject.Inject

class ImageSearchAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageSearchAdapter.ViewHolder>() {
    private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
    private val differList = AsyncListDiffer(this, diffCallback)
    private var onImageClickListener: ((String) -> Unit)? = null
    var images: List<Image>
        get() = differList.currentList
        set(value) = differList.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, glide, onImageClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    fun setOnImageClickListener(listener: (String) -> Unit) {
        onImageClickListener = listener
    }

    class ViewHolder(
        private val binding: ItemImageBinding,
        private val glide: RequestManager,
        private val onImageClick: ((String) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            binding.itemImage.apply {
                glide.load(image.previewURL).into(this)
                setOnClickListener {
                    onImageClick?.let { click ->
                        click(image.previewURL)
                    }
                }
            }
        }
    }
}
