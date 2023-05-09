package com.fcascan.parcial1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fcascan.parcial1.R
import com.fcascan.parcial1.consts.ITEM_AVATAR_SIZE_MULTIPLIER

class ItemsAdapter(
    private val itemsList: MutableList<ItemsObject>,
    private var onClick: (Int) -> Unit,
    private var onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<ItemsAdapter.ItemsHolder>() {
    private lateinit var context: Context

    class ItemsHolder (v: View): RecyclerView.ViewHolder(v) {
        private var view: View
        init {
            this.view = v
        }

        fun setImgItem(context: Context, imgItemUrl: String) {
            val imgItem: ImageView = view.findViewById(R.id.imgItem)
            Glide.with(context)
                .load(imgItemUrl)
                .error(
                    Glide.with(context)
                    .load(R.drawable.not_found)
                    .centerCrop()
                    .circleCrop()
                    .sizeMultiplier(ITEM_AVATAR_SIZE_MULTIPLIER)
                )
                .centerCrop()
                .circleCrop()
                .sizeMultiplier(ITEM_AVATAR_SIZE_MULTIPLIER)
                .into(imgItem)
        }

        fun setName(name: String) {
            val txtName: TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }

        fun setDescription(description: String) {
            val txtDescription: TextView = view.findViewById(R.id.txtDescription)
            txtDescription.text = description
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.card_with_image)
        }
    }

    class ItemsObject(id: Int, name: String, description: String, imgItem: String) {
        var id: Int = id
        var name: String = name
        var description: String = description
        var imgItem: String = imgItem
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_with_image, parent, false)
        context = parent.context
        return (ItemsHolder(view))
    }

    override fun onBindViewHolder(holder: ItemsHolder, index: Int) {
        holder.setImgItem(context, itemsList[index].imgItem)
        holder.setName(itemsList[index].name)
        holder.setDescription(itemsList[index].description)
        holder.getCard().setOnClickListener {
            onClick(index)
        }
        holder.getCard().setOnLongClickListener {
            onLongClick(index)
            true
        }
    }
}