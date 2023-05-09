package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.fcascan.parcial1.R
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.dao.*
import com.fcascan.parcial1.database.AppDatabase
import com.fcascan.parcial1.entities.Item
import com.fcascan.parcial1.entities.ItemInUserCategories
import com.fcascan.parcial1.entities.UserCategories
import com.google.android.material.snackbar.Snackbar

class AddItemFragment : Fragment() {
    private var categoryIdPreviouslySelected : Int? = null  //Input Parameter

    lateinit var v : View
    lateinit var txtName : EditText
    lateinit var txtDescription : EditText
    lateinit var spinnerCategories : Spinner
    lateinit var txtImgUrl : EditText
    lateinit var field1 : EditText
    lateinit var field2 : EditText
    lateinit var field3 : EditText
    lateinit var btnSave : Button
    lateinit var btnClear : Button

    lateinit var userMail : String

    private var userDao : UserDao? = null
    private var categoryDao : CategoryDao? = null
    private var userCategoriesDao : UserCategoriesDao? = null
    private var itemDao : ItemDao? = null
    private var itemInUserCategoriesDao : ItemInUserCategoriesDao? = null

    private var categoriesSpinnerList : MutableList<String> = mutableListOf()
    private var userCategoriesList : List<UserCategories?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryIdPreviouslySelected = arguments?.getString("paramCategoryId")?.toInt()
        Log.d("AddItemFragment", "From Category ID: $categoryIdPreviouslySelected")

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
        categoryDao = appDB?.categoryDao()
        userCategoriesDao = appDB?.userCategoriesDao()
        itemDao = appDB?.itemDao()
        itemInUserCategoriesDao = appDB?.itemInUserCategoriesDao()

        val mainActivity = activity as MainActivity
        userMail = mainActivity.paramUserMail.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_add_item, container, false)
        txtName = v.findViewById(R.id.txtName)
        txtDescription = v.findViewById(R.id.txtDescription)
        spinnerCategories = v.findViewById(R.id.spinnerCategories)
        txtImgUrl = v.findViewById(R.id.txtImgUrl)
        field1 = v.findViewById(R.id.field1)
        field2 = v.findViewById(R.id.field2)
        field3 = v.findViewById(R.id.field3)
        btnSave = v.findViewById(R.id.btnSave)
        btnClear = v.findViewById(R.id.btnClear)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateCategoriesSpinner()
        categoriesSpinnerSetup()
        setDefaultCategorySelected()

        btnSave.setOnClickListener {
            save()
        }

        btnClear.setOnClickListener {
            clearFields()
            Snackbar.make(v, "Fields Cleared", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun populateCategoriesSpinner() {
        val userId = userDao?.getUserIdByEmail(userMail)
        if (userId == null) {
            Snackbar.make(v, "User not found", Snackbar.LENGTH_LONG).show()
        } else {
            userCategoriesList = userCategoriesDao?.getUserCategoriesByUserId(userId)
            Log.d("AddItemFragment", "populateCategoriesSpinner(): ${userCategoriesList.toString()}")

            userCategoriesList?.forEach {
                val category = it?.category_id?.let { it1 -> categoryDao?.getCategoryById(it1) }
                categoriesSpinnerList.add(category?.name.toString())
            }
            Log.d("AddItemFragment", "populateCategoriesSpinner(): $categoriesSpinnerList")
        }
    }

    private fun categoriesSpinnerSetup() {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categoriesSpinnerList)
        spinnerCategories.adapter = adapter
    }

    private fun setDefaultCategorySelected() {
        if (categoryIdPreviouslySelected != null) {
            spinnerCategories.setSelection(categoryIdPreviouslySelected!!)
        } else {
            spinnerCategories.setSelection(0)
        }
    }

    private fun save() {
        val userId = userDao?.getUserIdByEmail(userMail)
        if (userId == null) {
            Snackbar.make(v, "User not found", Snackbar.LENGTH_LONG).show()
            return
        }
        val categorySelected = categoryDao?.getCategoryByName(spinnerCategories.selectedItem.toString())
        if (categorySelected == null) {
            Snackbar.make(v, "Category not found", Snackbar.LENGTH_LONG).show()
            return
        }
        val userCategorySelected = userCategoriesDao?.getUserCategoryByCategoryId(categorySelected.id)
        if (userCategorySelected == null) {
            Snackbar.make(v, "Category not found in user", Snackbar.LENGTH_LONG).show()
            return
        }
        val newItem = Item(
            0,
            txtName.text.toString(),
            txtImgUrl.text.toString(),
            txtDescription.text.toString(),
            field1.text.toString(),
            field2.text.toString(),
            field3.text.toString()
        )
        val newItemId = itemDao?.insertItemReturnId(newItem)
        if (newItemId == null) {
            Snackbar.make(v, "Error saving item", Snackbar.LENGTH_LONG).show()
            return
        }
        val newRelationIIUC = ItemInUserCategories(
            0,
            newItemId,
            userCategorySelected.id
        )
        val newRelation = itemInUserCategoriesDao?.insertItemInUserCategories(newRelationIIUC)
        if (newRelation == null) {
            Snackbar.make(v, "Error saving item", Snackbar.LENGTH_LONG).show()
            return
        }
        clearFields()
        Snackbar.make(v, "Saved successfully", Snackbar.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun clearFields(){
        txtName.text.clear()
        txtDescription.text.clear()
//        spinnerCategories.setSelection(0)   //TODO: Revisar si se puede dejar vacio
        txtImgUrl.text.clear()
        field1.text.clear()
        field2.text.clear()
        field3.text.clear()
    }
}