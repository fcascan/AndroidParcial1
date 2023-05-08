package com.fcascan.parcial1.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.fcascan.parcial1.R
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.consts.AVATAR_SIZE_MULTIPLIER
import com.fcascan.parcial1.consts.DEFAULT_USER_IMG_URL
import com.fcascan.parcial1.dao.UserDao
import com.fcascan.parcial1.database.AppDatabase
import com.fcascan.parcial1.entities.User
import com.fcascan.parcial1.enums.Permissions
import com.google.android.material.snackbar.Snackbar

class FragmentTools : Fragment() {
    //TODO: El 90% de este Fragment es lo mismo que ProfileFragment, se podria rehusar el codigo
    lateinit var v: View
    lateinit var imgAvatar: ImageView
    lateinit var txtId: EditText
    lateinit var txtName: EditText
    lateinit var txtLastName: EditText
    lateinit var txtEmail: EditText
    lateinit var txtPass: EditText
    lateinit var txtPassMatch: EditText
    lateinit var txtAvatarUrl: EditText
    lateinit var spinnerPermissions: Spinner
    lateinit var btnSave: Button
    lateinit var btnNew: Button
    lateinit var btnDelete: Button
    lateinit var usersSpinner: TextView

    private var userDao: UserDao? = null
    private lateinit var mainActivity: MainActivity
    private lateinit var userMail: String
    private var userMailSelected: User? = null
//    private var arrivalUser : User? = null

    private var userList: MutableList<User?>? = null
    lateinit var userStringList: List<String>

    lateinit var dialog: Dialog
    lateinit var arrayAdapter: ArrayAdapter<String>
    lateinit var searchField: TextView
    lateinit var listView: ListView
    lateinit var selectedUserMail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDB: AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
        mainActivity = activity as MainActivity
        userMail = mainActivity.paramUserMail.toString()

        populateUsersList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_tools, container, false)
        usersSpinner = v.findViewById(R.id.usersSpinner)
        imgAvatar = v.findViewById(R.id.imgAvatar)
        txtId = v.findViewById(R.id.txtId)
        txtName = v.findViewById(R.id.txtName)
        txtLastName = v.findViewById(R.id.txtLastName)
        txtEmail = v.findViewById(R.id.txtEmail)
        txtPass = v.findViewById(R.id.txtPass)
        txtPassMatch = v.findViewById(R.id.txtPassMatch)
        txtAvatarUrl = v.findViewById(R.id.txtAvatarUrl)
        spinnerPermissions = v.findViewById(R.id.spinnerPermissions)
        btnSave = v.findViewById(R.id.btnSave)
        btnNew = v.findViewById(R.id.btnNew)
        btnDelete = v.findViewById(R.id.btnDelete)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionsSpinnerSetup()
        avatarSetup()
        disableAllFields()
        usersSpinnerSetup()
        setUpDialog()

        usersSpinner.setOnClickListener {
            dialog.show()
        }

        searchField.doOnTextChanged { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->
            arrayAdapter.filter.filter(charSequence)
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent: AdapterView<*>, view2: View, position: Int, id: Long ->
                val selection = arrayAdapter.getItem(position).toString()
                searchField.text = selection
                enableAllFields()
                searchUserAndFillFields(selection)
                dialog.dismiss()
            }

        btnSave.setOnClickListener {
            saveUser()
        }

        btnDelete.setOnClickListener {
            deleteUser()
        }

        btnNew.setOnClickListener {
            newUser()
        }
    }

    private fun permissionsSpinnerSetup() {
        val newPermissionsList = Permissions.values().toMutableList()
        newPermissionsList.remove(Permissions.ERROR)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, newPermissionsList)
        spinnerPermissions.adapter = adapter
    }

    private fun avatarSetup() {
        Glide.with(this)
            .load(DEFAULT_USER_IMG_URL)
            .error(Glide.with(this)
                .load(R.drawable.not_found)
                .centerCrop()
                .sizeMultiplier(AVATAR_SIZE_MULTIPLIER)
            )
            .centerCrop()
            .sizeMultiplier(AVATAR_SIZE_MULTIPLIER)
            .into(imgAvatar)
    }

    private fun populateUsersList() {
        userList = userDao?.getAllUsers()
        val tempList = mutableListOf<String>()
        userList?.forEach { user ->
            if (user != null) {
                if (user.email != "admin") tempList.add(user.email)
//                if (user.permissions != Permissions.ADMIN) tempList.add(user.email)
            }
        }
        userStringList = tempList
    }

    private fun disableAllFields() {
        txtId.isEnabled = false
        txtName.isEnabled = false
        txtLastName.isEnabled = false
        txtEmail.isEnabled = false
        txtPass.isEnabled = false
        txtPassMatch.isEnabled = false
        txtAvatarUrl.isEnabled = false
        spinnerPermissions.isEnabled = false
        btnSave.isEnabled = false
//        btnNew.isEnabled = false
        btnDelete.isEnabled = false
    }

    private fun enableAllFields() {
        txtId.isEnabled = false
        txtName.isEnabled = true
        txtLastName.isEnabled = true
        txtEmail.isEnabled = true
        txtPass.isEnabled = true
        txtPassMatch.isEnabled = true
        txtAvatarUrl.isEnabled = true
        spinnerPermissions.isEnabled = true
        btnSave.isEnabled = true
        btnNew.isEnabled = true
        btnDelete.isEnabled = true
    }

    private fun usersSpinnerSetup() {
        var usersString = ""
        userList?.forEach {
            usersString += it?.email.toString() + "\n"
        }
    }

    private fun setUpDialog() {
        dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_seacharble_spinner)
        dialog.window?.setLayout(650, 800)
        dialog.setTitle("Users")
        dialog.setCancelable(true)

        searchField = dialog.findViewById(R.id.searchField)
        listView = dialog.findViewById(R.id.list_view)

        arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, userStringList)
        listView.adapter = arrayAdapter
    }

    private fun searchUserAndFillFields(email: String) {
        userMailSelected = userDao?.getUserByEmail(email)
        userMailSelected?.let {
            refreshAvatarImg(it.avatarUrl)
            txtId.setText(String.format("ID: " + it.id.toString()))
            txtName.setText(it.name)
            txtLastName.setText(it.lastName)
            txtEmail.setText(it.email)
            txtPass.setText(it.password)
            txtPassMatch.setText(it.password)
            txtAvatarUrl.setText(it.avatarUrl)
            spinnerPermissions.setSelection(it.permissions.ordinal)
        }
    }

    private fun refreshAvatarImg(param: String) {
        val url = param.ifEmpty { DEFAULT_USER_IMG_URL }
        Glide.with(this)
            .load(url)
            .error(Glide.with(this)
                .load(R.drawable.not_found)
                .centerCrop()
                .sizeMultiplier(AVATAR_SIZE_MULTIPLIER)
            )
            .centerCrop()
            .into(imgAvatar)
    }

    private fun saveUser() {
        if (txtName.text.isEmpty() || txtLastName.text.isEmpty() ||
            txtEmail.text.isEmpty() ||
            txtPass.text.isEmpty() || txtPassMatch.text.isEmpty() ||
            spinnerPermissions.selectedItem == null
        ) {
            Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (txtPass.text.toString() != txtPassMatch.text.toString()) {
            clearPassFields()
            Snackbar.make(v, "Passwords don't match", Snackbar.LENGTH_LONG).show()
            return
        }
        val oldUser = userMailSelected?.let { userDao?.getUserById(it.id) }
        if (oldUser != null) {
            val newUser = User(
                oldUser.id,
                txtName.text.toString(),
                txtLastName.text.toString(),
                txtEmail.text.toString(),
                txtPass.text.toString(),
                spinnerPermissions.selectedItem as Permissions,
                txtAvatarUrl.text.toString()
            )
            if (userDao?.updateUser(newUser) == 0) {
                clearPassFields()
                Snackbar.make(v, "User not updated. Try Again", Snackbar.LENGTH_LONG).show()
            } else {
                mainActivity.updateToolbarTitle(newUser.email)
                Snackbar.make(v, "User updated successfully", Snackbar.LENGTH_LONG).show()
            }
        } else {
            Snackbar.make(v, "User not found", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun newUser() {
        findNavController().navigate(R.id.action_toolsFragment_to_registerFragment2)
    }

    private fun deleteUser() {
        //TODO: Agregar un Dialog de confirmacion
        val user = userMailSelected?.let { userDao?.getUserById(it.id) }
        userDao?.deleteUser(user)
        Log.d("ProfileFragment", "User Deleted correctly")
        //TODO: Agregar un delay con ruedita de loading para que se llegue a ver el snackbar
        Snackbar.make(v, "User successfully deleted", Snackbar.LENGTH_LONG).show()
        findNavController().navigate(R.id.toolsFragment)
//        clearAllFields()
//        populateUsersList()
//        usersSpinnerSetup()
//        setUpDialog()
    }

    private fun clearPassFields() {
        txtPass.setText("")
        txtPassMatch.setText("")
    }

    private fun clearAllFields() {
        refreshAvatarImg(DEFAULT_USER_IMG_URL)
        txtId.setText("")
        txtName.setText("")
        txtLastName.setText("")
        txtEmail.setText("")
        txtPass.setText("")
        txtPassMatch.setText("")
        txtAvatarUrl.setText("")
        spinnerPermissions.setSelection(Permissions.ADMIN.ordinal)
        disableAllFields()
    }
}
