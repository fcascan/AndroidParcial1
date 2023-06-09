package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fcascan.parcial1.R
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.dao.CategoryDao
import com.fcascan.parcial1.dao.UserCategoriesDao
import com.fcascan.parcial1.dao.UserDao
import com.fcascan.parcial1.database.AppDatabase
import com.fcascan.parcial1.entities.Category
import com.fcascan.parcial1.entities.UserCategories
import com.google.android.material.snackbar.Snackbar

class EditCategoryFragment : Fragment() {
    private var paramCategoryId: Int? = null   //Input Parameter

    lateinit var v : View
    lateinit var txtName : EditText
    lateinit var txtDescription : EditText
    lateinit var btnSave : Button
    lateinit var btnClear : Button
    lateinit var btnCancel : Button
    lateinit var btnDelete : Button

    lateinit var userMail : String
    private var userDao : UserDao? = null
    private var categoryDao : CategoryDao? = null
    private var userCategoriesDao : UserCategoriesDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        paramCategoryId = arguments?.getString("paramCategoryId")?.toInt()
        Log.d("EditCategoryFragment", "Category ID: $paramCategoryId")

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
        categoryDao = appDB?.categoryDao()
        userCategoriesDao = appDB?.userCategoriesDao()

        val mainActivity = activity as MainActivity
        userMail = mainActivity.paramUserMail.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_edit_category, container, false)
        txtName = v.findViewById(R.id.txtName)
        txtDescription = v.findViewById(R.id.txtDescription)
        btnSave = v.findViewById(R.id.btnSave)
        btnClear = v.findViewById(R.id.btnClear)
        btnCancel = v.findViewById(R.id.btnCancel)
        btnDelete = v.findViewById(R.id.btnDelete)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateFields()

        btnSave.setOnClickListener {
            //1) Guardo nueva categoria en la base de datos Category
            //2) Guardo la relacion entre el usuario y la nueva categoria en la base de datos UserCategories
//            val newCategory = Category(0, txtName.text.toString(), txtDescription.text.toString())
//            val newCategoryId = categoryDao?.insertCategory(newCategory)
//            if (newCategoryId == null) {return@setOnClickListener}
//            val userId = userDao?.getUserIdByEmail(userMail)
//            if (userId == null) {return@setOnClickListener}
//            val newRelation = UserCategories(0, userId, newCategoryId.toInt())
//            userCategoriesDao?.insertUserCategories(newRelation)
//            clearFields()

            //2)
//            categoryDao?.updateCategory(Category(paramCategoryId!!, txtName.text.toString(), txtDescription.text.toString()))
//            userCategoriesDao?.updateUserCategories(userCategoriesDao?.getUserCategoryByCategoryId(paramCategoryId!!)!!)
//            clearFields()
//            Snackbar.make(v, "Saved Successfully", Snackbar.LENGTH_LONG).show()
//            findNavController().navigateUp()

            //3)
            val category = paramCategoryId?.let { it1 -> categoryDao?.getCategoryById(it1) }
            if (category != null) {
                category.name = txtName.text.toString()
                category.description = txtDescription.text.toString()
                categoryDao?.updateCategory(category)
                Snackbar.make(v, "Category Updated", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(v, "Category couldn't be saved", Snackbar.LENGTH_LONG).show()
            }
        }

        btnClear.setOnClickListener {
            clearFields()
            Snackbar.make(v, "Fields Cleared", Snackbar.LENGTH_SHORT).show()
        }

        btnCancel.setOnClickListener {
            clearFields()
            Snackbar.make(v, "Edition Cancelled", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        btnDelete.setOnClickListener {
            val category = paramCategoryId?.let { categoryDao?.getCategoryById(it) }
            if (category != null) {
                categoryDao?.deleteCategory(category)
                //TODO: Borrar la relacion entre el usuario y la categoria
//                userCategoriesDao?.deleteUserCategoriesWithCategoryId(category.id)
                clearFields()
                Snackbar.make(v, "Category Deleted", Snackbar.LENGTH_LONG).show()
                findNavController().navigateUp()
            } else {
                Snackbar.make(v, "Category Not Found", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun clearFields(){
        txtName.text.clear()
        txtDescription.text.clear()
    }

    private fun populateFields() {
        val category = paramCategoryId?.let { categoryDao?.getCategoryById(it) }
        if (category != null) {
            txtName.setText(category.name)
            txtDescription.setText(category.description)
        }
    }
}