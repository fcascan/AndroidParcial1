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
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.adapters.CategoriesAdapter
import com.fcascan.parcial1.dao.CategoryDao
import com.fcascan.parcial1.dao.UserCategoriesDao
import com.fcascan.parcial1.dao.UserDao
import com.fcascan.parcial1.database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class CategoriesFragment : Fragment() {
    lateinit var v: View
    lateinit var fabDashboard: FloatingActionButton
    lateinit var recViewCategories: RecyclerView
    lateinit var recViewAdapter: CategoriesAdapter

    lateinit var userMail : String

    private var userDao : UserDao? = null
    private var categoriesDao : CategoryDao? = null
    private var userCategoriesDao : UserCategoriesDao? = null
    private var categoriesList = mutableListOf<CategoriesAdapter.CategoriesObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
        categoriesDao = appDB?.categoryDao()
        userCategoriesDao = appDB?.userCategoriesDao()

        val mainActivity = activity as MainActivity
        userMail = mainActivity.paramUserMail.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_categories, container, false)
        fabDashboard = v.findViewById(R.id.fabDashboard)
        recViewCategories = v.findViewById(R.id.recViewCategories)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateCategories()

        Log.d("CategoriesFragment", "categoriesList: ${categoriesList.toString()}")
        recViewAdapter = CategoriesAdapter(categoriesList, {
                index -> onCardClicked(index)
                }, {
                index -> onCardLongClicked(index)
        })
        recViewCategories.setHasFixedSize(true)
        recViewCategories.layoutManager = LinearLayoutManager(context)
        recViewCategories.adapter = recViewAdapter

        fabDashboard.setOnClickListener {
            onFabClicked()
        }
    }


    fun populateCategories(){
        val userId = userDao?.getUserIdByEmail(userMail)
        if (userId != null) {
            userCategoriesDao?.getUserCategoriesByUserId(userId)?.forEach {
                if (it != null) {
                    categoriesDao?.getCategoryById(it.category_id)?.let { it1 ->
                        categoriesList.add(
                            CategoriesAdapter.CategoriesObject(
                                it1.id,
                                it1.name,
                                it1.description,
                                0
                            )
                        )
                    }
                } else {
                    Snackbar.make(v, "No se encontraron Categorias para mostrar", Snackbar.LENGTH_LONG).show()
                }
            }
        } else {
            Snackbar.make(v, "Error: No se pudo obtener el usuario", Snackbar.LENGTH_LONG).show()
        }

        //Lista hardcodeada:
//        categoriesList = mutableListOf(
//            CategoriesAdapter.CategoriesObject("Handheld Systems", "Consolas portatiles", 4),
//            CategoriesAdapter.CategoriesObject("Video Games Consoles", "Consolas de sobremesa", 5),
//            CategoriesAdapter.CategoriesObject("Retro Arcades", "Fichines o muebles arcade retro", 15),
//            CategoriesAdapter.CategoriesObject("Pinballs", "Flippers o pinballs", 8),
//        )
    }

    private fun onCardClicked(index: Int) {
        Log.d("CategoriesFragment", "Clicked on card $index")
        Log.d("CategoriesFragment", "Redirecting to ItemsFragment")
//        findNavController().navigate(R.id.action_categoriesFragment_to_itemsFragment)
        Snackbar.make(v, "No se pudo navegar", Snackbar.LENGTH_LONG).show()
    }

    private fun onCardLongClicked(index: Int) {
        Log.d("CategoriesFragment", "Long clicked on card $index")
        Log.d("CategoriesFragment", "Redirecting to EditCategoryFragment")
        val bundle = Bundle()
        bundle.putString("paramCategoryId", categoriesList[index].id.toString())
        Log.d("CategoriesFragment", "paramCategoryId: ${categoriesList[index].id.toString()}")
        findNavController().navigate(R.id.action_categoriesFragment_to_editCategoryFragment, bundle)
    }

    private fun onFabClicked() {
        Log.d("CategoriesFragment", "Redirecting to AddCategoryFragment")
        findNavController().navigate(R.id.action_categoriesFragment_to_addCategoryFragment)
    }
}