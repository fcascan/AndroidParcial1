package com.fcascan.parcial1.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

import com.fcascan.parcial1.R
import com.fcascan.parcial1.activities.MainActivity
import com.fcascan.parcial1.database.AppDatabase
import com.fcascan.parcial1.ui.login.LoginViewModel
import com.fcascan.parcial1.ui.login.LoginViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {
    lateinit var v: View
    lateinit var loginViewModel: LoginViewModel
    lateinit var loadingProgressBar: View

    lateinit var txtEmail: TextView
    lateinit var txtPass: TextView
    lateinit var btnLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_login, container, false)
        txtEmail = v.findViewById(R.id.txtEmail)
        txtPass = v.findViewById(R.id.txtPass)
        btnLogin = v.findViewById(R.id.btnLogin)
        //TODO: Agregar boton para Registro de nuevos usuarios
        //TODO: Agregar boton para ingreso como invitado (credenciales de gg@gmail.com)
        loadingProgressBar = v.findViewById(R.id.loading)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appDB : AppDatabase? = AppDatabase.getInstance(requireContext())
        Log.d("LoginFragment", "DB instance created = $appDB")
        val userDao = appDB?.userDao()
        Log.d("LoginFragment", "UserDao instance created = $userDao")

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        btnLogin.setOnClickListener {
            loadingON()
            loginViewModel.login(
                txtEmail.text.toString(),
                txtPass.text.toString()
            )
            val user = userDao?.getUserByEmailAndPassword(txtEmail.text.toString(), txtPass.text.toString())
            if (user != null) {
                displayMessage("Logged in Successfully. Redirecting...")
                Log.d("LoginFragment", "Credentials match with DB")
                val intent = Intent(this.context, MainActivity::class.java).apply {
                    putExtra("paramUserMail", user.email)
                }
                startActivity(intent)
            } else {
                txtPass.setText("")
                loadingOFF()
                displayMessage("Wrong Credentials, try again or Register")
                Log.d("LoginFragment", "Credentials do not match with DB")
            }
        }

        txtEmail.addTextChangedListener {
            areFieldsValid()
        }

        txtPass.addTextChangedListener {
            areFieldsValid()
        }

//
//        val usernameEditText = binding.username
//        val passwordEditText = binding.password
//        val loginButton = binding.login
//        val loadingProgressBar = binding.loading
//
//        loginViewModel.loginFormState.observe(viewLifecycleOwner,
//            Observer { loginFormState ->
//                if (loginFormState == null) {
//                    return@Observer
//                }
//                btnLogin.isEnabled = loginFormState.isDataValid
//                loginFormState.usernameError?.let {
//                    txtEmail.error = getString(it)
//                }
//                loginFormState.passwordError?.let {
//                    txtPass.error = getString(it)
//                }
//            })
//
//        loginViewModel.loginResult.observe(viewLifecycleOwner,
//            Observer { loginResult ->
//                loginResult ?: return@Observer
//                loadingProgressBar.visibility = View.GONE
//                loginResult.error?.let {
//                    showLoginFailed(it)
//                }
//                loginResult.success?.let {
//                    updateUiWithUser(it)
//                }
//            })
//
//        val afterTextChangedListener = object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                // ignore
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                // ignore
//            }
//
//            override fun afterTextChanged(s: Editable) {
//                loginViewModel.loginDataChanged(
//                    txtEmail.text.toString(),
//                    txtPass.text.toString()
//                )
//            }
//        }
//        txtEmail.addTextChangedListener(afterTextChangedListener)
//        txtPass.addTextChangedListener(afterTextChangedListener)
//        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                loginViewModel.login(
//                    usernameEditText.text.toString(),
//                    passwordEditText.text.toString()
//                )
//            }
//            false
//        }
//
//        loginButton.setOnClickListener {
//            loadingProgressBar.visibility = View.VISIBLE
//            loginViewModel.login(
//                usernameEditText.text.toString(),
//                passwordEditText.text.toString()
//            )
//        }
    }

    fun displayMessage(message: String) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).setAnchorView(R.id.btnLogin).show()
    }

    fun areFieldsValid(): Boolean {
        if (txtEmail.text.toString() == "") {
            //TODO: agregar un regex para validar el email
            btnLogin.isEnabled = false
            return false
        }
        if (txtPass.text.toString() == "") {
            //TODO: agregar un regex para validar passwords
            btnLogin.isEnabled = false
            return false
        }
        btnLogin.isEnabled = true
        return true
    }

    fun loadingON() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    fun loadingOFF() {
        loadingProgressBar.visibility = View.GONE
    }

//    private fun updateUiWithUser(model: LoggedInUserView) {
//        val welcome = getString(R.string.welcome) + model.displayName
//        // TODO : initiate successful logged in experience
//        val appContext = context?.applicationContext ?: return
//        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
//    }
//
//    private fun showLoginFailed(@StringRes errorString: Int) {
//        val appContext = context?.applicationContext ?: return
//        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}