package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fcascan.parcial1.R
import com.fcascan.parcial1.consts.ITEM_AVATAR_SIZE_MULTIPLIER
import com.fcascan.parcial1.consts.ITEM_IMG_SIZE_MULTIPLIER
import com.fcascan.parcial1.dao.ItemDao
import com.fcascan.parcial1.database.AppDatabase
import com.google.android.material.snackbar.Snackbar

class ViewItemFragment : Fragment() {
    private var itemId : Int? = null  //Input Parameter

    lateinit var v : View
    lateinit var imgItem : ImageView
    lateinit var txtName : EditText
    lateinit var txtDescription : EditText
    lateinit var spinnerCategories : Spinner
    lateinit var txtImgUrl : EditText
    lateinit var field1 : EditText
    lateinit var field2 : EditText
    lateinit var field3 : EditText

    private var itemsDao : ItemDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        itemId = arguments?.getString("paramItemId")?.toInt()
        Log.d("ViewItemFragment", "Received paramItemId: $itemId")

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        itemsDao = appDB?.itemDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_view_item, container, false)
        imgItem = v.findViewById(R.id.imgItem)
        txtName = v.findViewById(R.id.txtName)
        txtDescription = v.findViewById(R.id.txtDescription)
        spinnerCategories = v.findViewById(R.id.spinnerCategories)
        txtImgUrl = v.findViewById(R.id.txtImgUrl)
        field1 = v.findViewById(R.id.field1)
        field2 = v.findViewById(R.id.field2)
        field3 = v.findViewById(R.id.field3)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateFields()
//        populateCategoriesSpinner()   //TODO: Traer logica de AddItemFragment
//        categoriesSpinnerSetup()
        refreshImage()
        makeAllFieldsReadOnly()
    }

    private fun populateFields() {
        if (itemId == null) {
            Snackbar.make(v, "No item received", Snackbar.LENGTH_LONG).show()
            return
        } else {
            Log.d("ViewItemFragment", "Received paramItemId: $itemId")
            val item = itemsDao?.getItemById(itemId!!)
            if (item != null) {
                txtName.setText(item.name)
                txtDescription.setText(item.description)
//                spinnerCategories.setSelection(0)     //TODO seleccionar la categoria del Item
                txtImgUrl.setText(item.photoUrl)
                field1.setText(item.field1)
                field2.setText(item.field2)
                field3.setText(item.field3)
            }
        }
    }

    private fun refreshImage() {
        Glide.with(this)
            .load(txtImgUrl.text.toString())
            .error(
                Glide.with(this)
                    .load(R.drawable.not_found)
                    .centerCrop()
                    .circleCrop()
                    .sizeMultiplier(ITEM_IMG_SIZE_MULTIPLIER)
            )
            .centerCrop()
            .circleCrop()
            .sizeMultiplier(ITEM_IMG_SIZE_MULTIPLIER)
            .into(imgItem)
    }

    private fun makeAllFieldsReadOnly() {
//        txtName.isEnabled = false
//        txtDescription.isEnabled = false
//        spinnerCategories.isEnabled = false
//        txtImgUrl.isEnabled = false
//        field1.isEnabled = false
//        field2.isEnabled = false
//        field3.isEnabled = false

        txtName.focusable = View.NOT_FOCUSABLE
        txtDescription.focusable = View.NOT_FOCUSABLE
        spinnerCategories.focusable = View.NOT_FOCUSABLE
        txtImgUrl.focusable = View.NOT_FOCUSABLE
        field1.focusable = View.NOT_FOCUSABLE
        field2.focusable = View.NOT_FOCUSABLE
        field3.focusable = View.NOT_FOCUSABLE
    }
}