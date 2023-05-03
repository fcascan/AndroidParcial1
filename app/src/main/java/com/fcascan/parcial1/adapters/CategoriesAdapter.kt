package com.fcascan.parcial1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.fcascan.parcial1.R

class CategoriesAdapter (
    private val categoriesList: MutableList<CategoriesObject>,
    private var onClick: (Int) -> Unit,
    private var onLongClick: (Int) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {
    class CategoriesHolder (v: View): RecyclerView.ViewHolder(v) {
        private var view: View
        init {
            this.view = v
        }

        fun setName(name: String) {
            val txtName: TextView = view.findViewById(R.id.txtName)
            txtName.text = name
        }

        fun setDescription(description: String) {
            val txtDescription: TextView = view.findViewById(R.id.txtDescription)
            txtDescription.text = description
        }

        fun setTotalItemsContained(totalItemsContained: Int) {
            val txtTotalItemsContained: TextView = view.findViewById(R.id.txtTotalItemsContained)
            txtTotalItemsContained.text = totalItemsContained.toString()
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.card)
        }
    }

    class CategoriesObject(id: Int, name: String, description: String, totalItemsContained: Int) {
        var id: Int = id
        var name: String = name
        var description: String = description
        var totalItemsContained: Int = totalItemsContained
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return (CategoriesHolder(view))
    }

    override fun onBindViewHolder(holder: CategoriesHolder, index: Int) {
        holder.setName(categoriesList[index].name)
        holder.setDescription(categoriesList[index].description)
        holder.setTotalItemsContained(categoriesList[index].totalItemsContained)
        holder.getCard().setOnClickListener {
            onClick(index)
        }
        holder.getCard().setOnLongClickListener {
            onLongClick(index)
            true
        }
    }
}