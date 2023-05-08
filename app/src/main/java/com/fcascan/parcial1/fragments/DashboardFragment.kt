package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcascan.parcial1.R
import com.fcascan.parcial1.adapters.DashboardAdapter
import com.google.android.material.snackbar.Snackbar

class DashboardFragment : Fragment() {
    lateinit var v : View
    lateinit var recViewDashboard: RecyclerView
    lateinit var recViewAdapter: DashboardAdapter

    private lateinit var dashboardList : MutableList<DashboardAdapter.DashboardObject>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recViewDashboard = v.findViewById(R.id.recViewDashboard)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateDashboard()

        //TODO: Obtener cantidad de categorias y cantidad de items de este usuario
        // y mostrar el nro correcto en las cartas

        Log.d("DashboardFragment", dashboardList.toString())
        recViewAdapter = DashboardAdapter(dashboardList){
            index -> onCardClicked(index)
        }
        recViewDashboard.setHasFixedSize(true)
        recViewDashboard.layoutManager = LinearLayoutManager(context)
        recViewDashboard.adapter = recViewAdapter
    }

    private fun onCardClicked(index: Int) {
        if(dashboardList[index].name == "Categorias"){
            Log.d("DashboardFragment", "Redirecting to CategoriesFragment")
            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToCategoriesFragment())
        } else if(dashboardList[index].name == "Items"){
            Log.d("DashboardFragment", "Redirecting to ItemsFragment")
//            findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToItemListFragment())
        } else {
            Snackbar.make(v, "No se pudo navegar", Snackbar.LENGTH_LONG).show()
        }
    }

    fun populateDashboard() {
        dashboardList = mutableListOf(
            DashboardAdapter.DashboardObject("Categorias", "ver listado de categorias", 0),
            DashboardAdapter.DashboardObject("Items", "ver listado de items", 0)
        )
    }
}