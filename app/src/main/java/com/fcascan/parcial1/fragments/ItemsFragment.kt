package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fcascan.parcial1.R
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.adapters.ItemsAdapter
import com.fcascan.parcial1.dao.ItemDao
import com.fcascan.parcial1.dao.ItemInUserCategoriesDao
import com.fcascan.parcial1.dao.UserDao
import com.fcascan.parcial1.database.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ItemsFragment : Fragment() {
    private var paramCategoryId: Int? = null   //Input Parameter

    lateinit var v: View
    lateinit var fabItems: FloatingActionButton
    private lateinit var recViewItems: RecyclerView
    lateinit var recViewAdapter: ItemsAdapter

    lateinit var userMail : String

    private var userDao : UserDao? = null
    private var itemsDao : ItemDao? = null
    private var itemInUserCategoriesDao : ItemInUserCategoriesDao? = null
    private var itemsList = mutableListOf<ItemsAdapter.ItemsObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paramCategoryId = arguments?.getString("paramCategoryId")?.toInt()

        Log.d("ItemsFragment", "Category ID: $paramCategoryId")

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
        itemsDao = appDB?.itemDao()
        itemInUserCategoriesDao = appDB?.itemInUserCategoriesDao()

        val mainActivity = activity as MainActivity
        userMail = mainActivity.paramUserMail.toString()

        populateItems()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_items, container, false)
        fabItems = v.findViewById(R.id.fabItems)
        recViewItems = v.findViewById(R.id.recViewItems)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recViewAdapter = ItemsAdapter(itemsList, {
                index -> onCardClicked(index)
                }, {
                index -> onCardLongClicked(index)
        })
        recViewItems.setHasFixedSize(true)
        recViewItems.layoutManager = LinearLayoutManager(context)
        recViewItems.adapter = recViewAdapter

        fabItems.setOnClickListener {
            onFabClicked()
        }

        itemsList.forEachIndexed { index, _ ->
            Log.d("ItemsFragment", "Item #${index}: ${itemsList[index].name}")
        }
    }

    private fun populateItems() {
        //TODO: crear la lista a mostrar acorde al usuario y a la categoria seleccionada anteriormente
//        val userId = userDao?.getUserIdByEmail(userMail)
//        if (userId != null) {
//            userCategoriesDao?.getUserCategoriesByUserId(userId)?.forEach {
//                if (it != null) {
//                    categoriesDao?.getCategoryById(it.category_id)?.let { it1 ->
//                        categoriesList.add(
//                            CategoriesAdapter.CategoriesObject(
//                                it1.id,
//                                it1.name,
//                                it1.description,
//                                0
//                            )
//                        )
//                    }
//                } else {
//                    Snackbar.make(v, "No se encontraron Categorias para mostrar", Snackbar.LENGTH_LONG).show()
//                }
//            }
//        } else {
//            Snackbar.make(v, "Error: No se pudo obtener el usuario", Snackbar.LENGTH_LONG).show()
//        }

        //Lista hardcodeada:
        itemsList = mutableListOf(
            ItemsAdapter.ItemsObject(1,"",  "Item1", "Item1 description"),
            ItemsAdapter.ItemsObject(2,"",  "Item2", "Item1 description"),
            ItemsAdapter.ItemsObject(3,"",  "Item3", "Item1 description"),
            ItemsAdapter.ItemsObject(4, "", "Item4", "Item1 description"),
        )
    }

    private fun onCardClicked(index: Int) {
        Log.d("ItemsFragment", "Clicked on card $index")
        Log.d("ItemsFragment", "Redirecting to ViewItemFragment")
        val bundle = Bundle()
        bundle.putString("paramItemId", itemsList[index].id.toString())
        Log.d("ItemsFragment", "paramItemId: ${itemsList[index].id}")
        findNavController().navigate(R.id.action_itemsFragment_to_viewItemFragment, bundle)
    }

    private fun onCardLongClicked(index: Int) {
        Log.d("ItemsFragment", "Long clicked on card $index")
        Log.d("ItemsFragment", "Redirecting to EditItemFragment")
        val bundle = Bundle()
        bundle.putString("paramItemId", itemsList[index].id.toString())
        Log.d("ItemsFragment", "paramItemId: ${itemsList[index].id}")
        findNavController().navigate(R.id.action_itemsFragment_to_editItemFragment, bundle)
    }

    private fun onFabClicked() {
        Log.d("ItemsFragment", "Redirecting to AddItemFragment")
        findNavController().navigate(R.id.action_itemsFragment_to_addItemFragment)
    }
}