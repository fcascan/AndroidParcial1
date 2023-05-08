package com.fcascan.parcial1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fcascan.parcial1.R
import com.fcascan.parcial1.dao.UserDao
import com.fcascan.parcial1.database.AppDatabase
import com.fcascan.parcial1.entities.User
import com.fcascan.parcial1.enums.Permissions
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {
    lateinit var v : View
    lateinit var txtName : EditText
    lateinit var txtLastName : EditText
    lateinit var txtEmail : EditText
    lateinit var txtPass : EditText
    lateinit var txtPassMatch : EditText
    lateinit var txtAvatarUrl : EditText
    lateinit var btnRegister : Button

    private var userDao : UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        userDao = appDB?.userDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_register, container, false)
        txtName = v.findViewById(R.id.txtName)
        txtLastName = v.findViewById(R.id.txtLastName)
        txtEmail = v.findViewById(R.id.txtEmail)
        txtPass = v.findViewById(R.id.txtPass)
        txtPassMatch = v.findViewById(R.id.txtPassMatch)
        txtAvatarUrl = v.findViewById(R.id.txtAvatarUrl)
        btnRegister = v.findViewById(R.id.btnRegister)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {
            saveUser()
        }
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
        val newUser = User(
            0,
            txtName.text.toString(),
            txtLastName.text.toString(),
            txtEmail.text.toString(),
            txtPass.text.toString(),
            Permissions.USER,
            txtAvatarUrl.text.toString()
        )
        if (userDao?.insertUser(newUser) == 0L) {
            clearPassFields()
            Snackbar.make(v, "User could not be created. Try Again", Snackbar.LENGTH_LONG).show()
        } else {
            findNavController().navigateUp()
                Snackbar.make(v, "User created successfully.", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun clearPassFields(){
        txtPass.setText("")
        txtPassMatch.setText("")
    }

}