package rookie.android.testandroid.api.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rookie.android.testandroid.R
import rookie.android.testandroid.api.model.Meme

class MemeAdapter(private val onClick: (Meme) -> Unit) : ListAdapter<Meme, MemeAdapter.MemeViewHolder>(MemeCallBack){
    class MemeViewHolder(itemView: View, val onClick: (Meme) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val imgMeme: ImageView = itemView.findViewById(R.id.img_meme)

        private var currentMeme: Meme? = null

        init {
            itemView.setOnClickListener(){
                currentMeme?.let{
                    onClick(it)
                }
            }
        }

        fun bind(meme: Meme){
            currentMeme = meme

            Glide.with(itemView).load(meme.url).centerCrop().into(imgMeme)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_meme, parent, false)
        return  MemeViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        val meme = getItem(position)
        holder.bind(meme)
    }
}

object MemeCallBack: DiffUtil.ItemCallback<Meme>(){
    override fun areItemsTheSame(oldItem: Meme, newItem: Meme): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Meme, newItem: Meme): Boolean {
        return oldItem.id == newItem.id
    }

}