package com.fcascan.parcial1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fcascan.parcial1.R
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DashboardAdapter(
    private val dashboardList: MutableList<DashboardObject>,
    private var onClick: (Int) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.DashboardHolder>() {
    class DashboardHolder (v: View): RecyclerView.ViewHolder(v) {
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

    class DashboardObject(name: String, description: String, totalItemsContained: Int) {
        var name: String = name
        var description: String = description
        var totalItemsContained: Int = totalItemsContained
    }

    override fun getItemCount(): Int {
        return dashboardList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return (DashboardHolder(view))
    }

    override fun onBindViewHolder(holder: DashboardHolder, index: Int) {
        holder.setName(dashboardList[index].name)
        holder.setDescription(dashboardList[index].description)
        holder.setTotalItemsContained(dashboardList[index].totalItemsContained)
        holder.getCard().setOnClickListener {
            onClick(index)
        }
    }
}