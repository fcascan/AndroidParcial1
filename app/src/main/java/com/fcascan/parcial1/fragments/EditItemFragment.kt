package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.fcascan.parcial1.R
import com.fcascan.parcial1.dao.ItemDao
import com.fcascan.parcial1.database.AppDatabase
import com.google.android.material.snackbar.Snackbar

class EditItemFragment : Fragment() {
    private var itemId : Int? = null  //Input Parameter

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
    lateinit var btnCancel : Button
    lateinit var btnDelete : Button

    private var itemsDao : ItemDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = arguments?.getString("paramItemId")?.toInt()
        Log.d("EditItemFragment", "Received paramItemId: $itemId")

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        itemsDao = appDB?.itemDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_edit_item, container, false)
        txtName = v.findViewById(R.id.txtName)
        txtDescription = v.findViewById(R.id.txtDescription)
        spinnerCategories = v.findViewById(R.id.spinnerCategories)
        txtImgUrl = v.findViewById(R.id.txtImgUrl)
        field1 = v.findViewById(R.id.field1)
        field2 = v.findViewById(R.id.field2)
        field3 = v.findViewById(R.id.field3)
        btnSave = v.findViewById(R.id.btnSave)
        btnClear = v.findViewById(R.id.btnClear)
        btnCancel = v.findViewById(R.id.btnCancel)
        btnDelete = v.findViewById(R.id.btnDelete)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateFields()
//        populateCategoriesSpinner()   //TODO: Traer logica de AddItemFragment
//        categoriesSpinnerSetup()

        btnSave.setOnClickListener {
            val item = itemId?.let { it1 -> itemsDao?.getItemById(it1) }
            if (item != null) {
                item.name = txtName.text.toString()
                item.description = txtDescription.text.toString()
                item.photoUrl = txtImgUrl.text.toString()
                item.field1 = field1.text.toString()
                item.field2 = field2.text.toString()
                item.field3 = field3.text.toString()
                itemsDao?.updateItem(item)
                Snackbar.make(v, "Item Updated", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(v, "Item couldn't be saved", Snackbar.LENGTH_LONG).show()
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
            val item = itemId?.let { itemsDao?.getItemById(it) }
            if (item != null) {
                itemsDao?.deleteItem(item)
                //TODO: Borrar relacion entre item y UserCategories
//                itemInUserCategoriesDao?.deleteItemInUserCategoriesWithItemId(item.id)
                clearFields()
                Snackbar.make(v, "Item Deleted", Snackbar.LENGTH_LONG).show()
                findNavController().navigateUp()
            } else {
                Snackbar.make(v, "Item Not Found", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun populateFields() {
        if (itemId == null) {
            Snackbar.make(v, "No item received", Snackbar.LENGTH_LONG).show()
            return
        } else {
            Log.d("EditItemFragment", "Received paramItemId: $itemId")
            val item = itemsDao?.getItemById(itemId!!)
            if (item != null) {
                txtName.setText(item.name)
                txtDescription.setText(item.description)
//                spinnerCategories.setSelection(item.categoryId)   //TODO: Set spinner to item's category
                txtImgUrl.setText(item.photoUrl)
                field1.setText(item.field1)
                field2.setText(item.field2)
                field3.setText(item.field3)
            }
        }
    }

    private fun clearFields() {
        txtName.text.clear()
        txtDescription.text.clear()
        spinnerCategories.setSelection(0)   //TODO: Revisar si se puede dejar vacio
        txtImgUrl.text.clear()
        field1.text.clear()
        field2.text.clear()
        field3.text.clear()
    }
}