package com.example.prashanth.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prashanth.R
import com.example.prashanth.models.ImageDownloaderOptions
import com.example.prashanth.models.PorcupineItem
import com.example.prashanth.provideImageDownloadRepository


internal class PorcupineAdapter(
    private val porcupineItems: MutableList<PorcupineItem?>,
    private val onClick:(PorcupineItem) ->Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun refreshList(newList: List<PorcupineItem?>) {
        porcupineItems.clear()
        porcupineItems.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return PorcupineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        porcupineItems[position]?.let { (holder as PorcupineViewHolder).bind(it,onClick) }
    }

    override fun getItemCount(): Int = porcupineItems.size

    class PorcupineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.card_view_image)
        private val title = itemView.findViewById<TextView>(R.id.card_view_image_title)
        private val imageDownloaderRepo = provideImageDownloadRepository()

        fun bind(porcupine: PorcupineItem, onClick: (PorcupineItem) -> Unit) {
            title.text = porcupine.title
            imageDownloaderRepo.displayImage(
                ImageDownloaderOptions(porcupine.media?.m.orEmpty()),
                image,
                itemView
            )

            itemView.setOnClickListener {
                onClick.invoke(porcupine)
            }
        }
    }
}
