package com.hadir.hadirapp.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hadir.hadirapp.*
import com.hadir.hadirapp.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    private val vm by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LoginViewModel::class.java)
    }

    companion object {
        val RC_SIGN_IN: Int = 1
        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private val clicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity<HomeActivity>(requireContext())
            (activity as LoginActivity).finish()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val username = vm.sharedPreferenceUtils.getUsername()
        val password = vm.sharedPreferenceUtils.getPassword()
        if (username != null && password != null) {
            email.setText(username)
            passwordField.setText(password)
            circularProgress.visibility = View.VISIBLE
            makeText(getString(R.string.auto_login))
            val isRight = vm.login(email.text.toString(), passwordField.text.toString(), viewLifecycleOwner)
            isRight.observe(viewLifecycleOwner, Observer {
                val intent = Intent(requireContext(), StatisticsActivity::class.java)
                if (it) {
                    startActivity(intent)
                } else if (!it) {
                    tv_header.text = getString(R.string.session_expired)
                    circularProgress.visibility = View.INVISIBLE
                }
            })
        }

        loginBtn.setOnClickListener {
            if (checkEmpty()) {
                circularProgress.visibility = View.VISIBLE
                makeText(getString(R.string.logging_in))
                val isRight = vm.login(email.text.toString(), passwordField.text.toString(), viewLifecycleOwner)
                isRight.observe(viewLifecycleOwner, Observer {
                    val intent = Intent(requireContext(), StatisticsActivity::class.java)
                    if (it) {
                        startActivity(intent)
                    } else if (!it) {
                        tv_header.text = getString(R.string.username_password_error)
                        circularProgress.visibility = View.INVISIBLE
                    }
                })
            }
        }

        register.setOnClickListener {
            val intent = Intent(requireContext(), GetApiActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkEmpty(): Boolean {
        var isNotEmpty = true
        if (email.text.isNullOrBlank()) {
            email.error = "Username cannot be empty"
            isNotEmpty = false
        }
        if (passwordField.text.isNullOrBlank()) {
            passwordField.error = "Password cannot be empty"
            isNotEmpty = false
        }
        return isNotEmpty
    }

    private fun signIn() {
        val signIntent: Intent = vm.getClient().signInIntent
        startActivityForResult(signIntent, RC_SIGN_IN)
    }

    //login with email
    private fun loginWithEmail() {
        val email = email.toString()
        val password = passwordField.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            vm.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful && !clicked) {
                    val user = vm.firebaseAuth.currentUser
                    startActivity(HomeActivity.getLaunchIntent(requireContext()))
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error Login, try again later ", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "Please fill up the Credentials", Toast.LENGTH_LONG).show()
        }
    }

    //authentication firebase with google
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)

        vm.firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                startActivity(HomeActivity.getLaunchIntent(requireContext()))
            } else {
                Toast.makeText(requireContext(), "Google Sign In Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Google Sign In Failed" + e, Toast.LENGTH_LONG).show()

            }

        }


    }
}
