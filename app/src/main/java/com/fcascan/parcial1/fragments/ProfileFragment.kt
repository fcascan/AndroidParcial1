package com.fcascan.parcial1.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
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

class ProfileFragment : Fragment() {
    lateinit var v : View
    lateinit var imgAvatar : ImageView
    lateinit var txtName : EditText
    lateinit var txtLastName : EditText
    lateinit var txtEmail : EditText
    lateinit var txtPass : EditText
    lateinit var txtPassMatch : EditText
    lateinit var txtAvatarUrl : EditText
    lateinit var spinnerPermissions : Spinner
    lateinit var btnSave : Button
    lateinit var btnDelete : Button

    private var userDao : UserDao? = null
    lateinit var mainActivity : MainActivity
    lateinit var userMail : String
    private var arrivalUser : User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
        mainActivity = activity as MainActivity
        userMail = mainActivity.paramUserMail.toString()
        arrivalUser = userDao?.getUserByEmail(userMail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_profile, container, false)
        imgAvatar = v.findViewById(R.id.imgAvatar)
        txtName = v.findViewById(R.id.txtName)
        txtLastName = v.findViewById(R.id.txtLastName)
        txtEmail = v.findViewById(R.id.txtEmail)
        txtPass = v.findViewById(R.id.txtPass)
        txtPassMatch = v.findViewById(R.id.txtPassMatch)
        txtAvatarUrl = v.findViewById(R.id.txtAvatarUrl)
        spinnerPermissions = v.findViewById(R.id.spinnerPermissions)
        btnSave = v.findViewById(R.id.btnSave)
        btnDelete = v.findViewById(R.id.btnDelete)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        permissionsSpinnerSetup()
        populateFields()

        val userGuestType: User? = userDao?.getUserByEmail("guest")
        val userAdminType: User? = userDao?.getUserByEmail("admin")
        if (userGuestType != null && userMail == userGuestType.email
            || userAdminType != null && userMail == userAdminType.email) {
            disableAllFields()
        }

        btnSave.setOnClickListener {
            saveUser()
        }

        btnDelete.setOnClickListener {
            deleteUser()
        }
    }

    private fun permissionsSpinnerSetup() {
        val permissionsList = Permissions.values()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, permissionsList)
        spinnerPermissions.adapter = adapter
        spinnerPermissions.isEnabled = false
    }
    private fun populateFields() {
        userDao?.getUserByEmail(userMail)?.let {
            refreshAvatarImg(it.avatarUrl)
            txtName.setText(it.name)
            txtLastName.setText(it.lastName)
            txtEmail.setText(it.email)
            txtPass.setText(it.password)
//            txtPassMatch.setText(it.password)
            txtAvatarUrl.setText(it.avatarUrl)
            spinnerPermissions.setSelection(it.permissions.ordinal)
        }
    }

    private fun disableAllFields() {
        txtName.isEnabled = false
        txtLastName.isEnabled = false
        txtEmail.isEnabled = false
        txtPass.isEnabled = false
        txtPassMatch.isEnabled = false
        txtAvatarUrl.isEnabled = false
        spinnerPermissions.isEnabled = false
        btnSave.isEnabled = false
        btnDelete.isEnabled = false
    }

    private fun refreshAvatarImg(param : String) {
        val url = param.ifEmpty { DEFAULT_USER_IMG_URL }
        Glide.with(this)
            .load(url)
            .error(Glide.with(this)
                .load(R.drawable.not_found)
                .centerCrop()
                .sizeMultiplier(AVATAR_SIZE_MULTIPLIER)
            )
            .centerCrop()
            .sizeMultiplier(AVATAR_SIZE_MULTIPLIER)
            .into(imgAvatar)
    }

    private fun saveUser() {
        if (txtName.text.isEmpty() || txtLastName.text.isEmpty() || txtPass.text.isEmpty() || txtPassMatch.text.isEmpty() || txtEmail.text.isEmpty()) {
            Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (txtPass.text.toString() != txtPassMatch.text.toString()){
            clearPassFields()
            Snackbar.make(v, "Passwords don't match", Snackbar.LENGTH_LONG).show()
            return
        }
        val oldUser = arrivalUser?.let { userDao?.getUserById(it.id) }
        if (oldUser != null) {
            val newUser = User(
                oldUser.id,
                txtName.text.toString(),
                txtLastName.text.toString(),
                txtEmail.text.toString(),
                txtPass.text.toString(),
                oldUser.permissions,
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

    private fun deleteUser() {
        //TODO: Agregar un Dialog de confirmacion
        val user = arrivalUser?.let { userDao?.getUserById(it.id) }
        userDao?.deleteUser(user)
        Log.d("ProfileFragment", "User Deleted correctly")
        //TODO: Agregar un delay con ruedita de loading para que se llegue a ver el snackbar antes de salir de la actividad
        Snackbar.make(v, "User successfully deleted", Snackbar.LENGTH_LONG).show()
        mainActivity.exitMainActivity()
    }

    private fun clearPassFields(){
        txtPass.setText("")
        txtPassMatch.setText("")
    }
}